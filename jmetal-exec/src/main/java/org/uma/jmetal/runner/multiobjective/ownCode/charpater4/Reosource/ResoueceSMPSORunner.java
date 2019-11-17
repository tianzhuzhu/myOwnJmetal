package org.uma.jmetal.runner.multiobjective.ownCode.charpater4.Reosource;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.smpso.SMPSOBuilder;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.runner.AlgorithmRunner;
import org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QosResource;
import org.uma.jmetal.runner.multiobjective.ownCode.charpater4.DataSheetAlgorithmRunner;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.List;

/**
 * Class for configuring and running the SMPSO algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class ResoueceSMPSORunner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments. The first (optional) argument specifies
   *             the problem to solve.
   * @throws org.uma.jmetal.util.JMetalException
   * @throws java.io.IOException
   * @throws SecurityException
   * Invoking command:
  java org.uma.jmetal.TestRunnerC3.multiobjective.smpso.SMPSORunner problemName [referenceFront]
   */
  public static void SMPSO(String[] args) throws Exception {
    int i=30;
    while(i-->0) {
      DoubleProblem problem;
      Algorithm<List<DoubleSolution>> algorithm;
      MutationOperator<DoubleSolution> mutation;

      String referenceParetoFront = "";

      String problemName;
      if (args.length == 1) {
        problemName = args[0];
      } else if (args.length == 2) {
        problemName = args[0];
        referenceParetoFront = args[1];
      } else {
        problemName = "org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QosResource";
//      referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf" ;
      }

      problem = (DoubleProblem) ProblemUtils.<DoubleSolution>loadProblem(problemName);

      BoundedArchive<DoubleSolution> archive = new CrowdingDistanceArchive<DoubleSolution>(100);

      double mutationProbability = 1.0 / problem.getNumberOfVariables();
      double mutationDistributionIndex = 20.0;
      mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);

      algorithm = new SMPSOBuilder(problem, archive)
              .setMutation(mutation)
              .setMaxIterations(250)
              .setSwarmSize(100)
              .setSolutionListEvaluator(new SequentialSolutionListEvaluator<DoubleSolution>())
              .build();

      AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
              .execute();

      List<DoubleSolution> population = algorithm.getResult();
      long computingTime = algorithmRunner.getComputingTime();

      JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

      JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
      DataSheetAlgorithmRunner.printFinalSolutionSet(population, "SMPSO", "Reousece" + QosResource.getAmount());
    }
  }
}
