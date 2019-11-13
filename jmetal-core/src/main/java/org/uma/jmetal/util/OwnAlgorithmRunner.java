package org.uma.jmetal.util;

import org.apache.commons.math3.analysis.function.Pow;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.Solution;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for Runner classes
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public abstract class OwnAlgorithmRunner {
  static double[] list =new double[30];

  public static int getNum() {
    return num;
  }

  public static void setNum(int num) {
    OwnAlgorithmRunner.num = num;
  }

  /**
   * Write the population into two files and prints some data on screen
   * @param population
   */
  static int num=30;
  static double igd=0;
  static int i=0;
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
}
