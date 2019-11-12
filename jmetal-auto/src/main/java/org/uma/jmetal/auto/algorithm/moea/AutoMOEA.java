package org.uma.jmetal.auto.algorithm.moea;

import org.uma.jmetal.auto.algorithm.EvolutionaryAlgorithm;
import org.uma.jmetal.auto.component.evaluation.Evaluation;
import org.uma.jmetal.auto.component.evaluation.impl.SequentialEvaluation;
import org.uma.jmetal.auto.component.initialsolutionscreation.InitialSolutionsCreation;
import org.uma.jmetal.auto.component.replacement.Replacement;
import org.uma.jmetal.auto.component.selection.MatingPoolSelection;
import org.uma.jmetal.auto.component.termination.Termination;
import org.uma.jmetal.auto.component.termination.impl.TerminationByEvaluations;
import org.uma.jmetal.auto.component.variation.Variation;
import org.uma.jmetal.auto.parameter.IntegerParameter;
import org.uma.jmetal.auto.parameter.Parameter;
import org.uma.jmetal.auto.parameter.RealParameter;
import org.uma.jmetal.auto.parameter.catalogue.*;
import org.uma.jmetal.auto.util.densityestimator.DensityEstimator;
import org.uma.jmetal.auto.util.densityestimator.impl.CrowdingDistanceDensityEstimator;
import org.uma.jmetal.auto.util.ranking.Ranking;
import org.uma.jmetal.auto.util.ranking.impl.DominanceRanking;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.comparator.MultiComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoMOEA {
  public List<Parameter<?>> autoConfigurableParameterList;
  public List<Parameter<?>> fixedParameterList;

  private ProblemNameParameter<DoubleSolution> problemNameParameter;
  private ReferenceFrontFilenameParameter referenceFrontFilename;
  private IntegerParameter maximumNumberOfEvaluationsParameter;
  private StringCategoricalParameter algorithmResultParameter;
  private PopulationSizeParameter populationSizeParameter;
  private PopulationSizeWithArchive populationSizeWithArchiveParameter;
  private OffspringPopulationSizeParameter offspringPopulationSizeParameter;
  private CreateInitialSolutionsParameter createInitialSolutionsParameter;
  private StringCategoricalParameter algorithmType;
  private SelectionParameter selectionParameter;
  private VariationParameter variationParameter;
  private ReplacementParameter replacementParameter;

  public void parseAndCheckParameters(String[] args) {
    fixedParameterList = new ArrayList<>();
    autoConfigurableParameterList = new ArrayList<>();

    problemNameParameter = new ProblemNameParameter<>(args);
    referenceFrontFilename = new ReferenceFrontFilenameParameter(args);
    maximumNumberOfEvaluationsParameter =
        new IntegerParameter("maximumNumberOfEvaluations", args, 1, 10000000);

    fixedParameterList.add(problemNameParameter);
    fixedParameterList.add(referenceFrontFilename);
    fixedParameterList.add(maximumNumberOfEvaluationsParameter);

    for (Parameter<?> parameter : fixedParameterList) {
      parameter.parse().check();
    }
    populationSizeParameter = new PopulationSizeParameter(args);

    parseAlgorithmResult(args);
    parseCreateInitialSolutions(args);
    parseAlgorithmType(args) ;
    parseSelection(args);
    parseVariation(args);
    parseReplacement(args);

    autoConfigurableParameterList.add(populationSizeParameter);
    autoConfigurableParameterList.add(algorithmResultParameter);
    autoConfigurableParameterList.add(createInitialSolutionsParameter);
    autoConfigurableParameterList.add(algorithmType);
    autoConfigurableParameterList.add(variationParameter);
    autoConfigurableParameterList.add(selectionParameter);
    autoConfigurableParameterList.add(replacementParameter);

    for (Parameter<?> parameter : autoConfigurableParameterList) {
      parameter.parse().check();
    }
  }

  private void parseReplacement(String[] args) {
    replacementParameter =
        new ReplacementParameter(args, Arrays.asList("rankingAndDensityEstimatorReplacement"));
    StringCategoricalParameter removalPolicy =
        new StringCategoricalParameter(
            "removalPolicy", args, Arrays.asList("sequential", "oneShot"));

    RankingParameter<?> rankingForReplacement =
        new RankingParameter<>(
            "rankingForReplacement", args, Arrays.asList("dominanceRanking", "strengthRanking"));
    DensityEstimatorParameter<?> densityEstimatorForReplacement =
        new DensityEstimatorParameter<>(
            "densityEstimatorForReplacement", args, Arrays.asList("crowdingDistance", "knn"));

    replacementParameter.addGlobalParameter(removalPolicy);
    replacementParameter.addSpecificParameter(
        "rankingAndDensityEstimatorReplacement", rankingForReplacement);
    replacementParameter.addSpecificParameter(
        "rankingAndDensityEstimatorReplacement", densityEstimatorForReplacement);
  }

  private void parseVariation(String[] args) {
    CrossoverParameter crossover = new CrossoverParameter(args, Arrays.asList("SBX", "BLX_ALPHA"));
    ProbabilityParameter crossoverProbability =
        new ProbabilityParameter("crossoverProbability", args);
    crossover.addGlobalParameter(crossoverProbability);
    RepairDoubleSolutionStrategyParameter crossoverRepairStrategy =
        new RepairDoubleSolutionStrategyParameter(
            "crossoverRepairStrategy", args, Arrays.asList("random", "round", "bounds"));
    crossover.addGlobalParameter(crossoverRepairStrategy);

    RealParameter distributionIndex = new RealParameter("sbxDistributionIndex", args, 5.0, 400.0);
    crossover.addSpecificParameter("SBX", distributionIndex);

    RealParameter alpha = new RealParameter("blxAlphaCrossoverAlphaValue", args, 0.0, 1.0);
    crossover.addSpecificParameter("BLX_ALPHA", alpha);

    MutationParameter mutation =
        new MutationParameter(args, Arrays.asList("uniform", "polynomial"));
    ProbabilityParameter mutationProbability =
        new ProbabilityParameter("mutationProbability", args);
    mutation.addGlobalParameter(mutationProbability);
    RepairDoubleSolutionStrategyParameter mutationRepairStrategy =
        new RepairDoubleSolutionStrategyParameter(
            "mutationRepairStrategy", args, Arrays.asList("random", "round", "bounds"));
    mutation.addGlobalParameter(mutationRepairStrategy);

    RealParameter distributionIndexForMutation =
        new RealParameter("polynomialMutationDistributionIndex", args, 5.0, 400.0);
    mutation.addSpecificParameter("polynomial", distributionIndexForMutation);

    RealParameter uniformMutationPerturbation =
        new RealParameter("uniformMutationPerturbation", args, 0.0, 1.0);
    mutation.addSpecificParameter("uniform", uniformMutationPerturbation);

    DifferentialEvolutionCrossoverParameter differentialEvolutionCrossover =
        new DifferentialEvolutionCrossoverParameter(args);

    RealParameter f = new RealParameter("f", args, 0.0, 1.0);
    RealParameter cr = new RealParameter("cr", args, 0.0, 1.0);
    differentialEvolutionCrossover.addGlobalParameter(f);
    differentialEvolutionCrossover.addGlobalParameter(cr);

    offspringPopulationSizeParameter =
        new OffspringPopulationSizeParameter(args, Arrays.asList(1, 10, 50, 100, 200, 400));

    variationParameter =
        new VariationParameter(
            args, Arrays.asList("crossoverAndMutationVariation", "differentialEvolutionVariation"));
    variationParameter.addGlobalParameter(offspringPopulationSizeParameter);
    variationParameter.addSpecificParameter("crossoverAndMutationVariation", crossover);
    variationParameter.addSpecificParameter("crossoverAndMutationVariation", mutation);
    variationParameter.addSpecificParameter(
        "differentialEvolutionVariation", differentialEvolutionCrossover);
  }

  private void parseSelection(String[] args) {
    RankingParameter<?> rankingForSelection =
        new RankingParameter<>(
            "rankingForSelection", args, Arrays.asList("dominanceRanking", "strengthRanking"));
    DensityEstimatorParameter<?> densityEstimatorForSelection =
        new DensityEstimatorParameter<>(
            "densityEstimatorForSelection", args, Arrays.asList("crowdingDistance", "knn"));

    selectionParameter =
        new SelectionParameter(
            args, Arrays.asList("tournament", "random", "differentialEvolutionSelection"));
    IntegerParameter selectionTournamentSize =
        new IntegerParameter("selectionTournamentSize", args, 2, 10);

    selectionParameter.addSpecificParameter("tournament", selectionTournamentSize);
    selectionParameter.addSpecificParameter("tournament", rankingForSelection);
    selectionParameter.addSpecificParameter("tournament", densityEstimatorForSelection);
  }

  private void parseCreateInitialSolutions(String[] args) {
    createInitialSolutionsParameter =
        new CreateInitialSolutionsParameter(
            args, Arrays.asList("random", "latinHypercubeSampling", "scatterSearch"));
  }

  private void parseAlgorithmResult(String[] args) {
    algorithmResultParameter =
        new StringCategoricalParameter(
            "algorithmResult", args, Arrays.asList("externalArchive", "population"));
    populationSizeWithArchiveParameter =
        new PopulationSizeWithArchive(args, Arrays.asList(10, 20, 50, 100, 200));
    algorithmResultParameter.addSpecificParameter("population", populationSizeParameter);
    algorithmResultParameter.addSpecificParameter(
        "externalArchive", populationSizeWithArchiveParameter);
  }

  private void parseAlgorithmType(String[] args) {
    algorithmType =
        new StringCategoricalParameter("algorithmType",
            args, Arrays.asList("EA", "DE"));
  }

  /**
   * Creates an instance of NSGA-II from the parsed parameters
   *
   * @return
   */
  public EvolutionaryAlgorithm<DoubleSolution> create() {
    DoubleProblem problem = (DoubleProblem) problemNameParameter.getProblem();

    // Algorithm result
    Archive<DoubleSolution> archive = null;
    if (algorithmResultParameter.getValue().equals("externalArchive")) {
      archive = new CrowdingDistanceArchive<>(populationSizeParameter.getValue());
      populationSizeParameter.setValue(populationSizeWithArchiveParameter.getValue());
    }

    // Create initial solutions
    InitialSolutionsCreation<DoubleSolution> initialSolutionsCreation =
        createInitialSolutionsParameter.getParameter(problem, populationSizeParameter.getValue());

    // Variation
    //if (selectionParameter.getValue().equals("RankingAndDensityEstimator")) {}

    Ranking<DoubleSolution> ranking = new DominanceRanking<>(new DominanceComparator<>());
    DensityEstimator<DoubleSolution> densityEstimator = new CrowdingDistanceDensityEstimator<>();
    MultiComparator<DoubleSolution> rankingAndCrowdingComparator =
        new MultiComparator<DoubleSolution>(
            Arrays.asList(
                ranking.getSolutionComparator(), densityEstimator.getSolutionComparator()));

    Variation<DoubleSolution> variation =
        (Variation<DoubleSolution>) variationParameter.getParameter();

    // Selection
    MatingPoolSelection<DoubleSolution> selection =
        (MatingPoolSelection<DoubleSolution>)
            selectionParameter.getParameter(
                variation.getMatingPoolSize(), rankingAndCrowdingComparator);

    // Evaluation
    Evaluation<DoubleSolution> evaluation = new SequentialEvaluation<>(problem);

    // Replacement
    Replacement<DoubleSolution> replacement =
        (Replacement<DoubleSolution>)
            replacementParameter.getParameter(rankingAndCrowdingComparator);

    // Variation
    Termination termination =
        new TerminationByEvaluations(maximumNumberOfEvaluationsParameter.getValue());

    EvolutionaryAlgorithm<DoubleSolution> moea =
        new EvolutionaryAlgorithm<>(
            "Generic MOEA",
            evaluation,
            initialSolutionsCreation,
            termination,
            selection,
            variation,
            replacement,
            archive);

    return moea;
  }

  public static void print(List<Parameter<?>> parameterList) {
    parameterList.forEach(item -> System.out.println(item));
  }

  public static void main(String[] args) {
    String[] parameters =
        ("--problemName org.uma.jmetal.problem.multiobjective.zdt.ZDT4 "
                + "--referenceFrontFileName ZDT4.pf "
                + "--maximumNumberOfEvaluations 25000 "
                + "--algorithmResult externalArchive "
                + "--populationSize 100 "
                + "--populationSizeWithArchive 100 "
                + "--offspringPopulationSize 100 "
                + "--createInitialSolutions random "
                + "--variation crossoverAndMutationVariation "
                + "--selection tournament "
                + "--selectionTournamentSize 2 "
                + "--rankingForSelection dominanceRanking "
                + "--densityEstimatorForSelection crowdingDistance "
                + "--crossover SBX "
                + "--crossoverProbability 0.9 "
                + "--crossoverRepairStrategy bounds "
                + "--sbxDistributionIndex 20.0 "
                + "--mutation polynomial "
                + "--mutationProbability 0.01 "
                + "--mutationRepairStrategy bounds "
                + "--polynomialMutationDistributionIndex 20.0 "
                + "--replacement rankingAndDensityEstimatorReplacement "
                + "--rankingForReplacement dominanceRanking "
                + "--densityEstimatorForReplacement crowdingDistance ")
            .split("\\s+");

    AutoMOEA autoMOEA = new AutoMOEA();
    autoMOEA.parseAndCheckParameters(parameters);

    autoMOEA.print(autoMOEA.fixedParameterList);
    autoMOEA.print(autoMOEA.autoConfigurableParameterList);

    EvolutionaryAlgorithm<DoubleSolution> moea = autoMOEA.create();
    moea.run();

    new SolutionListOutput(moea.getResult())
        .setSeparator("\t")
        .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
        .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
        .print();
  }
}
