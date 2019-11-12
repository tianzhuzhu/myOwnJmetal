package org.uma.jmetal.algorithm.multiobjective.gde3;

import org.uma.jmetal.algorithm.AlgorithmBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.crossover.impl.MyDifferentialEvolutionCrossover;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.List;

/**
 * This class implements the GDE3 algorithm
 */
public class MyGDE3Builder implements AlgorithmBuilder<MyGDE3> {
  private DoubleProblem problem;
  protected int populationSize;
  protected int maxEvaluations;

  protected MyDifferentialEvolutionCrossover crossoverOperator;
  protected DifferentialEvolutionSelection selectionOperator;

  protected SolutionListEvaluator<DoubleSolution> evaluator;

  /** Constructor */
  public MyGDE3Builder(DoubleProblem problem) {
    this.problem = problem;
    maxEvaluations = 25000 ;
    populationSize = 100 ;
    selectionOperator = new DifferentialEvolutionSelection();
    crossoverOperator = new MyDifferentialEvolutionCrossover() ;
    evaluator = new SequentialSolutionListEvaluator<DoubleSolution>() ;
  }

  /* Setters */
  public MyGDE3Builder setPopulationSize(int populationSize) {
    this.populationSize = populationSize;

    return this;
  }

  public MyGDE3Builder setMaxEvaluations(int maxEvaluations) {
    this.maxEvaluations = maxEvaluations;

    return this;
  }

  public MyGDE3Builder setCrossover(MyDifferentialEvolutionCrossover crossover) {
    crossoverOperator = crossover;

    return this;
  }

  public MyGDE3Builder setSelection(DifferentialEvolutionSelection selection) {
    selectionOperator = selection;

    return this;
  }

  public MyGDE3Builder setSolutionSetEvaluator(SolutionListEvaluator<DoubleSolution> evaluator) {
    this.evaluator = evaluator ;

    return this ;
  }

  public MyGDE3 build() {
    return new MyGDE3(problem, populationSize, maxEvaluations, selectionOperator, crossoverOperator, evaluator) ;
  }

  /* Getters */
  public CrossoverOperator<DoubleSolution> getCrossoverOperator() {
    return crossoverOperator;
  }

  public SelectionOperator<List<DoubleSolution>, List<DoubleSolution>> getSelectionOperator() {
    return selectionOperator;
  }

  public int getPopulationSize() {
    return populationSize;
  }

  public int getMaxEvaluations() {
    return maxEvaluations;
  }

}

