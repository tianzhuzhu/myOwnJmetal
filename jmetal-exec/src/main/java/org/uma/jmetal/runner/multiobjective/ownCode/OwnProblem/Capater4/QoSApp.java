package org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/** Class representing problem ZDT1 */
@SuppressWarnings("serial")
public class QoSApp extends AbstractDoubleProblem {
  static List<List<Double>> list =new ArrayList<>();

  public static int getAmount() {
    return amount;
  }

  public static void setAmount(int amount) {
    QoSApp.amount = amount;
  }

  static int amount=200;
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
  public QoSApp() {
    this(35);
  }

  /**
   * Creates a new instance of problem ZDT1.
   *
   * @param numberOfVariables Number of variables.
   */
  public QoSApp(Integer numberOfVariables) {
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

    double cost=0,cost1,cost2,cost3;
    double time=0,time1,time2,time3;
    double quality=0,quality1,quality2,quality3;


    for (int j = 0; j <5 ; j++) {
      Double v0 = solution.getVariable(0+j*7)*amount;
      Double v1 = solution.getVariable(1+j*7)*amount;
      Double v2 = solution.getVariable(2+j*7)*amount;
      Double v3 = solution.getVariable(3+j*7)*amount;
      Double v4 = solution.getVariable(4+j*7)*amount;
      Double v5 = solution.getVariable(5+j*7)*amount;
      Double v6 = solution.getVariable(6+j*7)*amount;

      cost1=(list.get((int) Math.floor(v0)).get(0)+list.get((int) Math.floor(v1)+1000).get(0)+list.get((int) Math.floor(v2)+2000).get(0))/3;
      time1=(list.get((int) Math.floor(v0)).get(1)+list.get((int) Math.floor(v1)+1000).get(1)+list.get((int) Math.floor(v2)+2000).get(1))/3;
      quality1=(list.get((int) Math.floor(v0)).get(2)*list.get((int) Math.floor(v0)).get(3)+
              list.get((int) Math.floor(v1)+1000).get(2)*list.get((int) Math.floor(v1)+1000).get(3)+
              list.get((int) Math.floor(v2)+2000).get(2)*list.get((int) Math.floor(v2)+2000).get(3))/3;
      cost2=(list.get((int) Math.floor(v3)+3000).get(0)+list.get((int) Math.floor(v4)+4000).get(0))/2;
      time2=(list.get((int) Math.floor(v3)+3000).get(1)+list.get((int) Math.floor(v4)+4000).get(1))/2;
      quality2=(
              list.get((int) Math.floor(v3)+3000).get(2)*list.get((int) Math.floor(v3)+3000).get(3)+
                      list.get((int) Math.floor(v4)+4000).get(2)*list.get((int) Math.floor(v4)+4000).get(3))/2;
      cost3=(list.get((int) Math.floor(v5)+5000).get(0)+list.get((int) Math.floor(v6)+6000).get(0))/2;
      time3=(list.get((int) Math.floor(v5)+5000).get(1)+list.get((int) Math.floor(v6)+6000).get(1))/2;
      quality3=(
              list.get((int) Math.floor(v5)+5000).get(3)*list.get((int) Math.floor(v5)+5000).get(3)+
                      list.get((int) Math.floor(v6)+6000).get(2)*list.get((int) Math.floor(v6)+6000).get(3))/2;

      cost+=cost1*cost2*cost3;
      time+=time1*time2*time3;
      quality+=quality1*quality2*quality3;
    }

    solution.setObjective(0,cost);
    solution.setObjective(1,time);
    solution.setObjective(2,-quality);
    //todo
  }

}
