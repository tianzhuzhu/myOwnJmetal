package org.uma.jmetal.runner.multiobjective;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.multiobjective.pesa2.PESA2Builder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.runner.AlgorithmRunner;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Class for configuring and running the GDE3 algorithm (parallel version)
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */

public class ParallelGDE3Runner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments.
   * @throws SecurityException
   * Invoking command:
  java org.uma.jmetal.TestRunnerC3.multiobjective.ParallelGDE3Runner problemName [referenceFront]
   */
  public static void main(String[] args) throws FileNotFoundException {
    DoubleProblem problem;
    Algorithm<List<DoubleSolution>> algorithm;
    DifferentialEvolutionSelection selection;
    DifferentialEvolutionCrossover crossover;

    String referenceParetoFront = "" ;

    String problemName ;
    if (args.length == 1) {
      problemName = args[0];
    } else if (args.length == 2) {
      problemName = args[0] ;
      referenceParetoFront = args[1] ;
    } else {
      problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
      referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf" ;
    }

    problem = (DoubleProblem) ProblemUtils.<DoubleSolution> loadProblem(problemName);

    double cr = 0.5 ;
    double f = 0.5 ;
    crossover = new DifferentialEvolutionCrossover(cr, f, "rand/1/bin") ;
    selection = new DifferentialEvolutionSelection() ;

    SolutionListEvaluator<DoubleSolution> evaluator = new MultithreadedSolutionListEvaluator<DoubleSolution>(0, problem) ;

    algorithm = new GDE3Builder(problem)
        .setCrossover(crossover)
        .setSelection(selection)
        .setMaxEvaluations(25000)
        .setPopulationSize(100)
        .setSolutionSetEvaluator(evaluator)
        .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
        .execute() ;

    List<DoubleSolution> population = ((GDE3)algorithm).getResult() ;
    long computingTime = algorithmRunner.getComputingTime() ;

    evaluator.shutdown();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

    printFinalSolutionSet(population);
    if (!referenceParetoFront.equals("")) {
      printQualityIndicators(population, referenceParetoFront) ;
    }
  }

    /**
     * Class for configuring and running the PESA2 algorithm
     *
     * @author Antonio J. Nebro <antonio@lcc.uma.es>
     */

    public static class PESA2Runner extends AbstractAlgorithmRunner {
      /**
       * @param args Command line arguments.
       * @throws SecurityException
       * Invoking command:
      java org.uma.jmetal.TestRunnerC3.multiobjective.ParallelGDE3Runner.PESA2Runner problemName [referenceFront]
       */
      public static void main(String[] args) throws JMetalException, FileNotFoundException {
        Problem<DoubleSolution> problem;
        Algorithm<List<DoubleSolution>> algorithm;
        CrossoverOperator<DoubleSolution> crossover;
        MutationOperator<DoubleSolution> mutation;

        String referenceParetoFront = "" ;

        String problemName ;
        if (args.length == 1) {
          problemName = args[0];
        } else if (args.length == 2) {
          problemName = args[0] ;
          referenceParetoFront = args[1] ;
        } else {
          problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";
          referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf" ;
        }

        problem = ProblemUtils.loadProblem(problemName);

        double crossoverProbability = 0.9 ;
        double crossoverDistributionIndex = 20.0 ;
        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

        double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
        double mutationDistributionIndex = 20.0 ;
        mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

        algorithm = new PESA2Builder<DoubleSolution>(problem, crossover, mutation)
            .setMaxEvaluations(25000)
            .setPopulationSize(100)
            .setArchiveSize(100)
            .setBisections(5)
            .build() ;

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute() ;

        List<DoubleSolution> population = algorithm.getResult();

        long computingTime = algorithmRunner.getComputingTime() ;

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

        printFinalSolutionSet(population);
        if (!referenceParetoFront.equals("")) {
          printQualityIndicators(population, referenceParetoFront) ;
        }
      }
    }
}
