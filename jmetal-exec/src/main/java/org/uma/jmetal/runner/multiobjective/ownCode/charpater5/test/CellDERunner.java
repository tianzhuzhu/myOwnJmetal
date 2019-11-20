package org.uma.jmetal.runner.multiobjective.ownCode.charpater5.test;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.cellde.CellDE45;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.runner.AlgorithmRunner;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.*;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.neighborhood.impl.C9;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Class to configure and run the MOCell algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class CellDERunner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments.
   * @throws JMetalException
   * @throws FileNotFoundException
   * Invoking command:
    java org.uma.jmetal.TestRunnerC5.multiobjective.ResourceMOCellRunner problemName [referenceFront]
   */
  public static void CELLDE(String[] args) throws JMetalException, IOException {
    int i = 30;
    while (i-- > 0) {
      Problem<DoubleSolution> problem;
      Algorithm<List<DoubleSolution>> algorithm;
      SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
      DifferentialEvolutionCrossover crossover;
      String referenceParetoFront = "";

      String problemName;
      if (args.length == 1) {
        problemName = args[0];
      } else if (args.length == 2) {
        problemName = args[0];
        referenceParetoFront = args[1];
      } else {
        problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
        referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf";
      }

      problem = ProblemUtils.<DoubleSolution>loadProblem(problemName);

      double cr = 0.5;
      double f = 0.5;

      crossover = new DifferentialEvolutionCrossover(cr, f, "rand/1/bin");

      selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());

      algorithm = new CellDE45(
              problem,
              50000,
              100,
              new CrowdingDistanceArchive<DoubleSolution>(100),
              new C9<DoubleSolution>((int) Math.sqrt(100), (int) Math.sqrt(100)),
              selection,
              crossover,
              20,
              new SequentialSolutionListEvaluator<DoubleSolution>()
      );

      AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
              .execute();

      List<DoubleSolution> population = algorithm.getResult();
      long computingTime = algorithmRunner.getComputingTime();

      JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

      printFinalSolutionSet(population);
      if (!referenceParetoFront.equals("")) {
        OwnAlgorithmRunner.printQualityIndicators(population, referenceParetoFront,"CellDE");
      }
    }
  }
}
