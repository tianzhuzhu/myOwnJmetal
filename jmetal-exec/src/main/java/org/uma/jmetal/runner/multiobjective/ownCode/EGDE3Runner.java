package org.uma.jmetal.runner.multiobjective.ownCode;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.gde3.MyGDE3Builder;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.crossover.impl.MyDifferentialEvolutionCrossover;
import org.uma.jmetal.operator.selection.impl.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.runner.AlgorithmRunner;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.OwnAlgorithmRunner;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Class for configuring and running the GDE3 algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class EGDE3Runner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments.
   * @throws SecurityException Invoking command: java org.uma.jmetal.TestRunnerC3.multiobjective.GDE3Runner
   *                           problemName [referenceFront]
   */
  public static void EDGE3(String[] args) throws IOException {
    int i = 30;
    while (i-- > 0) {
      DoubleProblem problem;
      Algorithm<List<DoubleSolution>> algorithm;
      DifferentialEvolutionSelection selection;
      MyDifferentialEvolutionCrossover crossover;
      OwnAlgorithmRunner.setNum(30);
      String problemName;
      String referenceParetoFront = "";
      if (args.length == 1) {
        problemName = args[0];
      } else if (args.length == 2) {
        problemName = args[0];
        referenceParetoFront = args[1];
      } else {
        problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
        referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf";
      }

      problem = (DoubleProblem) ProblemUtils.<DoubleSolution>loadProblem(problemName);

      double cr = .5;
      double f = 0.5;
      crossover = new MyDifferentialEvolutionCrossover(cr, f, "rand/1/bin");

      selection = new DifferentialEvolutionSelection();

      algorithm = new MyGDE3Builder(problem)
              .setCrossover(crossover)
              .setSelection(selection)
              .setMaxEvaluations(25000)
              .setPopulationSize(100)
              .setSolutionSetEvaluator(new SequentialSolutionListEvaluator<>())
              .build();

      AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
              .execute();

      List<DoubleSolution> population = algorithm.getResult();
      long computingTime = algorithmRunner.getComputingTime();

      JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
      OwnAlgorithmRunner.printFinalSolutionSet(population);

      if (!referenceParetoFront.equals("")) {

        OwnAlgorithmRunner.printQualityIndicators(population, referenceParetoFront,"EGDE3");
      }

    }

  }
}
