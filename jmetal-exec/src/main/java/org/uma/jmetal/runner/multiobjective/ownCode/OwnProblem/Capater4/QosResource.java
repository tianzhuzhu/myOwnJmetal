package org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.io.*;
import java.util.*;

/** Class representing problem ZDT1 */
@SuppressWarnings("serial")
public class QosResource extends AbstractDoubleProblem {
  static List<List<Double>> list =new ArrayList<>();
  public static int getAmount() {
    return amount;
  }

  public static void setAmount(int amount) {
    QosResource.amount = amount;
  }

  static int amount=100;
  static {

    File file = null;
    FileReader fw = null;
    Date date = new Date();
    Random random = new Random();

    try {
//      Scanner scanner=new Scanner(new FileInputStream("C:\\Users\\lujin\\OneDrive\\论文\\基于药事服务资源协同认知的调度方法研究\\myOwnJmetal\\459.txt"));
      Scanner scanner=new Scanner(new FileInputStream("C:\\Users\\lujin\\OneDrive\\论文\\基于药事服务资源协同认知的调度方法研究\\myOwnJmetal\\257better.txt"));
      for (int i = 1; i <= 10000; i++) {
        //成本0.5-1 时间0.2-1 可靠性0.8-1 质量0-1

        String s = scanner.nextLine();
        String[] s1 = s.split(" ");
        List<Double> doubleList=new ArrayList<>();
        for (int i1 = 0; i1 < s1.length; i1++) {
          doubleList.add(Double.parseDouble(s1[i1]));
        }
        list.add(doubleList);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {

    }
  }
  /** Constructor. Creates default instance of problem ZDT1 (30 decision variables) */
  public QosResource() {
    this(3);
  }

  /**
   * Creates a new instance of problem ZDT1.
   *
   * @param numberOfVariables Number of variables.
   */
  public QosResource(Integer numberOfVariables) {
    setNumberOfVariables(numberOfVariables);
    setNumberOfObjectives(3);
    setName("QosResource");

    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

    for (int i = 0; i < getNumberOfVariables(); i++) {
      lowerLimit.add(0.0);
      upperLimit.add(1.0);
    }

    setVariableBounds(lowerLimit, upperLimit);
  }

  /** Evaluate() method */
  public void evaluate(DoubleSolution solution) {

  double cost;
  double time;
  double quality;
    Double v0 = solution.getVariable(0)*amount;
    Double v1 = solution.getVariable(1)*amount;
    Double v2 = solution.getVariable(2)*amount;
    cost=(list.get((int) Math.floor(v0)).get(0)+list.get((int) Math.floor(v1)+1000).get(0)+list.get((int) Math.floor(v2)+2000).get(0))/3;
    time=(list.get((int) Math.floor(v0)).get(1)+list.get((int) Math.floor(v1)+1000).get(1)+list.get((int) Math.floor(v2)+2000).get(1))/3;
    quality=(list.get((int) Math.floor(v0)).get(2)*list.get((int) Math.floor(v0)).get(3)+
            list.get((int) Math.floor(v1)+1000).get(2)*list.get((int) Math.floor(v1)+1000).get(3)+
            list.get((int) Math.floor(v2)+2000).get(2)*list.get((int) Math.floor(v2)+2000).get(3))/3;
    solution.setObjective(0,cost);
    solution.setObjective(1,time);
    solution.setObjective(2,-quality);
    //todo
  }

  /**
   * Returns the value of the ZDT1 function G.
   *
   * @param solution Solution
   */
  protected double evalG(DoubleSolution solution) {
    double g = 0.0;
    for (int i = 1; i < solution.getNumberOfVariables(); i++) {
      g += solution.getVariable(i);
    }
    double constant = 9.0 / (solution.getNumberOfVariables() - 1);

    return constant * g + 1.0;
  }

  /**
   * Returns the value of the ZDT1 function H.
   *
   * @param f First argument of the function H.
   * @param g Second argument of the function H.
   */
  protected double evalH(double f, double g) {
    double h ;
    h = 1.0 - Math.sqrt(f / g);
    return h;
  }
}