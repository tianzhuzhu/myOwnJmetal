package org.uma.jmetal.runner.multiobjective.ownCode.charpater4;

import org.uma.jmetal.runner.multiobjective.ownCode.EGDE3Runner;
import org.uma.jmetal.runner.multiobjective.ownCode.MOCellRunner;
import org.uma.jmetal.runner.multiobjective.ownCode.NSGAIIRunner;

public class DataSheetRunnerC3 {
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
            EGDE3Runner.EDGE3(args);
            MOCellRunner.MOCell(args);
            NSGAIIRunner.NSGAII(args);
//            MOEADRunner.MOEAD(args);

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