package org.uma.jmetal.runner.multiobjective.ownCode.charpater5.Reosource;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.MyGDE3Builder;
import org.uma.jmetal.operator.crossover.impl.MyDifferentialEvolutionCrossover;
import org.uma.jmetal.operator.crossover.impl.MyReourceEvolutionCrossover;
import org.uma.jmetal.operator.selection.impl.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.runner.AlgorithmRunner;
import org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QosResource;
import org.uma.jmetal.runner.multiobjective.ownCode.charpater4.DataSheetAlgorithmRunner;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.OwnAlgorithmRunner;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.io.IOException;
import java.util.List;

/**
 * Class for configuring and running the GDE3 algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class ResourceEGDE3Runner extends AbstractAlgorithmRunner {
  /**
   * @param args Command line arguments.
   * @throws SecurityException Invoking command: java org.uma.jmetal.TestRunnerC5.multiobjective.GDE3Runner
   *                           problemName [referenceFront]
   */
  public static void EDGE3(String[] args) throws IOException {
    int i = 30;
    while (i-- > 0) {
      DoubleProblem problem;
      Algorithm<List<DoubleSolution>> algorithm;
      DifferentialEvolutionSelection selection;
      MyDifferentialEvolutionCrossover crossover;
      OwnAlgorithmRunner.setNum(3);
      String problemName;
      String referenceParetoFront = "";
      if (args.length == 1) {
        problemName = args[0];
      } else if (args.length == 2) {
        problemName = args[0];
        referenceParetoFront = args[1];
      } else {
        problemName = "org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QosResource";
//        referenceParetoFront = "jmetal-problem/src/test/resources/pareto_fronts/ZDT1.pf";
      }

      problem = (DoubleProblem) ProblemUtils.<DoubleSolution>loadProblem(problemName);

      double cr = .5;
      double f = 0.5;
      crossover = new MyReourceEvolutionCrossover(cr, f, "rand/1/bin");

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

      DataSheetAlgorithmRunner.printFinalSolutionSet(population,"EGDE3","Reousece", ""+QosResource.getAmount());
    }

  }

  public static void main(String[] args) throws IOException {
    String string[]=new String[0];
    EDGE3(string);
  }
}
