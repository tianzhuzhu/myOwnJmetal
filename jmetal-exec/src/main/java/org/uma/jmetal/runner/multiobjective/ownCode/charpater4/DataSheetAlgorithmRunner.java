package org.uma.jmetal.runner.multiobjective.ownCode.charpater4;

import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Abstract class for Runner classes
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public abstract class DataSheetAlgorithmRunner {
  static double[] list =new double[30];
  static double[] cost=new double[30];
  static double[] time=new double[30];
  static double[] quality=new double[30];

  static double minc;
  static double mint;
  static double minq;
  static double sdc=0;
  static double sdt=0;
  static double sdq=0;


  public static int getNum() {
    return num;
  }

  public static void setNum(int num) {
    DataSheetAlgorithmRunner.num = num;
  }

  /**
   * Write the population into two files and prints some data on screen
   * @param population
   */
  static int num=30;
  static double igd=0;
  static int i=0;
  static int k=0;
  public static void printFinalSolutionSet(List<? extends Solution<?>> population) {

    new SolutionListOutput(population)
        .setSeparator("\t")
        .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
        .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
        .print();
    JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
  }

  /**
   * Print all the available quality indicators
   * @param population
   * @param paretoFrontFile
   * @throws FileNotFoundException
   */
  public static <S extends Solution<?>> void printQualityIndicators(List<S> population, String paretoFrontFile)
          throws IOException {
    Front referenceFront = new ArrayFront(paretoFrontFile);
    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront) ;

    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront) ;
    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(population)) ;
    List<PointSolution> normalizedPopulation = FrontUtils
        .convertFrontToSolutionList(normalizedFront) ;

    String outputString = "\n" ;
    Double evaluate = new GenerationalDistance<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation);
    igd+=evaluate;
//    System.out.println(igd);

    i++;
    if(i==num-1) {
      FileOutputStream fos = new FileOutputStream("igd.txt", true);
//true表示在文件末尾追加
      igd/=num;
      outputString=igd+"";
      fos.write(outputString.getBytes());
      fos.close();
    }
//    outputString += "Hypervolume (N) : " +
//        new PISAHypervolume<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
//    outputString += "Hypervolume     : " +
//        new PISAHypervolume<S>(referenceFront).evaluate(population) + "\n";
//    outputString += "Epsilon (N)     : " +
//        new Epsilon<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) +
//        "\n" ;
//    outputString += "Epsilon         : " +
//        new Epsilon<S>(referenceFront).evaluate(population) + "\n" ;
//    outputString += "GD (N)          : " +
//        new GenerationalDistance<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
//    outputString += "GD              : " +
//        new GenerationalDistance<S>(referenceFront).evaluate(population) + "\n";
//    outputString += "IGD (N)         : " +
//        new InvertedGenerationalDistance<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
//    outputString +="IGD             : " +
//        new InvertedGenerationalDistance<S>(referenceFront).evaluate(population) + "\n";
//    outputString += "IGD+ (N)        : " +
//        new InvertedGenerationalDistancePlus<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
//    outputString += "IGD+            : " +
//        new InvertedGenerationalDistancePlus<S>(referenceFront).evaluate(population) + "\n";
//    outputString += "Spread (N)      : " +
//        new Spread<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation) + "\n";
//    outputString += "Spread          : " +
//        new Spread<S>(referenceFront).evaluate(population) + "\n";
////    outputString += "R2 (N)          : " +
////        new R2<List<DoubleSolution>>(normalizedReferenceFront).runAlgorithm(normalizedPopulation) + "\n";
////    outputString += "R2              : " +
////        new R2<List<? extends Solution<?>>>(referenceFront).runAlgorithm(population) + "\n";
//    outputString += "Error ratio     : " +
//        new ErrorRatio<List<? extends Solution<?>>>(referenceFront).evaluate(population) + "\n";
    
    JMetalLogger.logger.info(outputString);
  }
  public static <S extends Solution<?>> void printQualityIndicators(List<S> population, String paretoFrontFile,String algorithm)
          throws IOException {

    Front referenceFront = new ArrayFront(paretoFrontFile);
    FrontNormalizer frontNormalizer = new FrontNormalizer(referenceFront) ;

    Front normalizedReferenceFront = frontNormalizer.normalize(referenceFront) ;
    Front normalizedFront = frontNormalizer.normalize(new ArrayFront(population)) ;
    List<PointSolution> normalizedPopulation = FrontUtils
            .convertFrontToSolutionList(normalizedFront) ;

    String outputString = "\n" ;
    Double evaluate = new GenerationalDistance<PointSolution>(normalizedReferenceFront).evaluate(normalizedPopulation);
     double sdc=0;
     double sdt=0;
     double sdq=0;
    list[i]=evaluate;
    System.out.println(list[i]+""+evaluate);
    i++;


    if(i==num-1) {
      StringBuilder stringBuilder=new StringBuilder();
      FileOutputStream fos = new FileOutputStream("igd.txt", true);
//true表示在文件末尾追加
      stringBuilder.append(algorithm+":\n");
      double mean=0;
      for (int i1 = 0; i1 < list.length; i1++) {
        mean+=list[i1];
      }
      mean=mean/num;

      stringBuilder.append("IGD mean="+mean+"\r\n");
      double sd=0;
      for (int i1 = 0; i1 < list.length; i1++) {
        sd+=Math.pow(list[i1]-mean,2);
      }
      sd=sd/num;
      sd= Math.pow(sd,0.5);

      stringBuilder.append("IGD SD  ="+sd+"\r\n");
      fos.write(stringBuilder.toString().getBytes());
      fos.close();
      i=0;
    }

    JMetalLogger.logger.info(outputString);
  }
  public static void printFinalSolutionSet(List<? extends Solution<?>> population,String algorithm,String taskName) {

    new SolutionListOutput(population)
            .setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
            .print();
    //成本0.5-1 时间0.2-1 可靠性0.8-1 质量0-1

    try {
      FileOutputStream fos = new FileOutputStream("Result"+taskName+".txt", true);

      int amount = population.size();
      double aCost=0;
      double aTime=0;
      double aQuality=0;
      for (Solution<?> solution : population) {
        aCost+= solution.getObjective(0);
        aTime+=solution.getObjective(1);
        aQuality+=solution.getObjective(2);
      }
      aCost/=amount;
      aTime/=amount;
      aQuality/=amount;
      cost[k]=aCost;
      time[k]=aTime;
      quality[k]=aQuality;
      if(k==num-1){
        double ac=0;
        double at=0;
        double aq=0;
        for (int j = 0; j < cost.length; j++) {
          ac+=cost[j];
          at+=time[j];
          aq+=quality[j];
        }
        ac/=num;
        at/=num;
        aq/=num;
        StringBuilder sb=new StringBuilder();
        sb.append(algorithm+"\r\n");
        sb.append("cost mean "+ac+" ");
        sb.append("time mean "+at+" ");
        sb.append("quality mean "+aq+"\r\n");
        double sdc=0;
        double sdt=0;
        double sdq=0;
        for (int j = 0; j < cost.length; j++) {
          sdc+=Math.pow(cost[j]-ac,2);
          sdt+=Math.pow(time[j]-at,2);
          sdq+= Math.pow(quality[j]-aq,2);
        }
        sdc/=num;
        sdt/=num;
        sdq/=num;
        sdc=Math.sqrt(sdc);
        sdt=Math.sqrt(sdt);
        sdq=Math.sqrt(sdq);
        sb.append("cost sd "+sdc+" ");
        sb.append("time sd "+sdt+" ");
        sb.append("quality sd "+sdq+"\r\n");
        double minc=Double.MAX_VALUE;
        double mint=Double.MAX_VALUE;
        double minq=Double.MAX_VALUE;
        for (int j = 0; j < cost.length; j++) {
          minc= Math.min(minc,cost[i]);
          mint=Math.min(mint,time[i]);
          minq= Math.min(minq,quality[i]);
        }
        sb.append("min cost"+ minc+" ");
        sb.append("min time"+mint+" ");
        sb.append("min quality"+minq+"\r\n");


        fos.write(sb.toString().getBytes());
        fos.close();
        k=0;
        sb.append("\r\n");
      }
      k++;
    } catch (Exception e) {
      e.printStackTrace();
    }


    JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
  }
  public static void printFinalSolutionSet(List<? extends Solution<?>> population,String algorithm,String taskName,String number) {

    new SolutionListOutput(population)
            .setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
            .print();
    //成本0.5-1 时间0.2-1 可靠性0.8-1 质量0-1

    try {
      FileOutputStream fos = new FileOutputStream("Result"+taskName+".txt", true);

      int amount = population.size();
      double aCost=0;
      double aTime=0;
      double aQuality=0;
      double sdtime=0,sdcost=0,sdquality=0;
      double mincost=Double.MAX_VALUE;
      double mintime=Double.MAX_VALUE;
      double minquality=Double.MAX_VALUE;


      for (Solution<?> solution : population) {
        aCost+= solution.getObjective(0);
        aTime+=solution.getObjective(1);
        aQuality+=solution.getObjective(2);
        mincost= Math.min(mincost,solution.getObjective(0));
        mintime= Math.min(mintime,solution.getObjective(1));
        minquality= Math.min(minquality,solution.getObjective(2));
      }
      minc+=mincost;
      mint+=mintime;
      minq+=minquality;
      aCost/=amount;
      aTime/=amount;
      aQuality/=amount;


      cost[k]=aCost;
      time[k]=aTime;
      quality[k]=aQuality;

      for (Solution<?> solution : population) {
        sdcost+=Math.pow(solution.getObjective(0)-aCost,2);
        sdtime+=Math.pow(solution.getObjective(1)-aTime,2);
        sdquality+= Math.pow(solution.getObjective(2)-aQuality,2);
      }
      sdcost/=num;
      sdtime/=num;
      sdquality/=num;
      sdcost=Math.sqrt(sdcost);
      sdtime=Math.sqrt(sdtime);
      sdcost=Math.sqrt(sdtime);
      sdc+=sdcost;
      sdq+=sdquality;
      sdt+=sdtime;
      if(k==num-1){
        double ac=0;
        double at=0;
        double aq=0;
        for (int j = 0; j < cost.length; j++) {
          ac+=cost[j];
          at+=time[j];
          aq+=quality[j];
        }
        ac/=num;
        at/=num;
        aq/=num;
        StringBuilder sb=new StringBuilder();
        sb.append(number+"\r\n");
        mint/=num;
        minq/=num;
        minc/=num;
        sdt/=num;
        sdc/=num;
        sdq/=num;
        sb.append("mean   sd   min\r\n");
        sb.append("cost"+ac+" "); sb.append(" "+sdc+" ");       sb.append(""+ minc+" \r\n");
        sb.append("time  "+at+" ");        sb.append(" "+sdt+" ");         sb.append(""+mint+"\r\n");
        sb.append("quality  "+aq+" ");
        sb.append("  "+sdq+" ");

        sb.append(" "+minq+"\r\n");


        fos.write(sb.toString().getBytes());
        fos.close();
        k=0;
        sb.append("\r\n");
        mint=0;minc=0;minq=0;sdc=0;sdq=0;sdt=0;
      }
      k++;
    } catch (Exception e) {
      e.printStackTrace();
    }


    JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
  }
}

