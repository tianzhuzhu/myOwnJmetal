package org.uma.jmetal.runner.singleobjective;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.evolutionstrategy.EvolutionStrategyBuilder;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.BitFlipMutation;
import org.uma.jmetal.problem.binaryproblem.BinaryProblem;
import org.uma.jmetal.problem.singleobjective.OneMax;
import org.uma.jmetal.runner.AlgorithmRunner;
import org.uma.jmetal.solution.binarysolution.BinarySolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to configure and run a non elitist (mu,lamba) evolution strategy. The target problem is
 * OneMax.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class NonElitistEvolutionStrategyRunner {
  /**
   * Usage: java org.uma.jmetal.TestRunnerC5.singleobjective.NonElitistEvolutionStrategyRunner
   */
  public static void main(String[] args) throws Exception {

    Algorithm<BinarySolution> algorithm;
    BinaryProblem problem = new OneMax(512) ;

    MutationOperator<BinarySolution> mutationOperator = new BitFlipMutation(1.0 / problem.getBitsFromVariable(0)) ;

    algorithm = new EvolutionStrategyBuilder<BinarySolution>(problem, mutationOperator,
        EvolutionStrategyBuilder.EvolutionStrategyVariant.NON_ELITIST)
        .setMaxEvaluations(25000)
        .setMu(1)
        .setLambda(10)
        .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute() ;

    BinarySolution solution = algorithm.getResult() ;
    List<BinarySolution> population = new ArrayList<>(1) ;
    population.add(solution) ;

    long computingTime = algorithmRunner.getComputingTime() ;

    new SolutionListOutput(population)
            .setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
            .print();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

  }
}
