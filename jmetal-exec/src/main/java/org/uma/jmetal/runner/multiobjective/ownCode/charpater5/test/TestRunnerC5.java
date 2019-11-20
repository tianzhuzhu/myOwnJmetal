package org.uma.jmetal.runner.multiobjective.ownCode.charpater5.test;

import org.uma.jmetal.algorithm.multiobjective.cellde.CellDE;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3;

public class TestRunnerC5 {
    public static void main(String[] args) {
        String problemName="org.uma.jmetal.problem.multiobjective.zdt.ZDT3";

        String referenceParetoFront="jmetal-problem/src/test/resources/pareto_fronts/ZDT3.pf";
//        String problemName="org.uma.jmetal.problem.multiobjective.zdt.ZDT3";
//
//        String referenceParetoFront="jmetal-problem/src/test/resources/pareto_fronts/ZDT3.pf";
        if(args==null||args.length<2) {
            args = new String[2];
            args[0]=problemName;
            args[1]=referenceParetoFront;
        }
        try {
            CellDERunner.CELLDE(args);
            DCellDERunner.MyDCellDE(args);
//            EGDE3Runner.EDGE3(args);
            GDE3Runner.GDE3(args);
            NSGAIIRunner.NSGAII(args);
            SMPSORunner.SMPSO(args);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
/**
 * @author ：mmzs
 * @date ：Created in 11/13 0013 15:51
 * @description：
 * @modified By：
 * @version: $
 */