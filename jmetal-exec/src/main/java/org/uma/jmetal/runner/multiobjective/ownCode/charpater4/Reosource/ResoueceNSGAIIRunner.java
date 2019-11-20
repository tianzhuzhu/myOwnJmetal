package org.uma.jmetal.runner.multiobjective.ownCode.charpater4.Reosource;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.runner.AlgorithmRunner;
import org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QosResource;
import org.uma.jmetal.runner.multiobjective.ownCode.charpater4.DataSheetAlgorithmRunner;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.*;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Class to configure and run the NSGA-II algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class ResoueceNSGAIIRunner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments.
   * @throws JMetalException
   * @throws FileNotFoundException Invoking command:
   *                               java org.uma.jmetal.TestRunnerC5.multiobjective.nsgaii.ResoueceNSGAIIRunner problemName [referenceFront]
   */
  public static void NSGAII(String[] args) throws Exception {
    int i = 30;
    while (i-- > 0) {
      Problem<DoubleSolution> problem;
      Algorithm<List<DoubleSolution>> algorithm;
      CrossoverOperator<DoubleSolution> crossover;
      MutationOperator<DoubleSolution> mutation;
      SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
      String referenceParetoFront = "";

      String problemName;
      if (args.length == 1) {
        problemName = args[0];
      } else if (args.length == 2) {
        problemName = args[0];
        referenceParetoFront = args[1];
      } else {
        problemName = "org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QosResource";
//        referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf";
      }

      problem = ProblemUtils.<DoubleSolution>loadProblem(problemName);

      double crossoverProbability = 0.9;
      double crossoverDistributionIndex = 20.0;
      crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

      double mutationProbability = 1.0 / problem.getNumberOfVariables();
      double mutationDistributionIndex = 20.0;
      mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);

      selection = new BinaryTournamentSelection<DoubleSolution>(
              new RankingAndCrowdingDistanceComparator<DoubleSolution>());

      int populationSize = 100;
      algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation, populationSize)
              .setSelectionOperator(selection)
              .setMaxEvaluations(25000)
              .build();

      AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
              .execute();

      List<DoubleSolution> population = algorithm.getResult();
      long computingTime = algorithmRunner.getComputingTime();

      JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

      DataSheetAlgorithmRunner.printFinalSolutionSet(population,"NSGAII","Reousece"+ QosResource.getAmount());
    }
  }
}
