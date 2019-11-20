package org.uma.jmetal.runner.multiobjective.ownCode.charpater5.app;

import org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QoSApp;

public class AppRunner {
    public static void main(String[] args) {
        String problemName="org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QoSApp";

        String referenceParetoFront="";
//        String problemName="org.uma.jmetal.problem.multiobjective.zdt.ZDT3";
//
//        String referenceParetoFront="jmetal-problem/src/test/resources/pareto_fronts/ZDT3.pf";
        if(args==null||args.length<2) {
            args = new String[2];
            args[0]=problemName;
            args[1]=referenceParetoFront;
        }
        int i=0;
        while(i<5) {

            QoSApp.setAmount(i*200);
            System.out.println(QoSApp.getAmount());
            i++;
            try {
                AppEDGERunner.EDGE3(args);
//                ResourceMOCellRunner.MOCell(args);
//                ResoueceNSGAIIRunner.NSGAII(args);
//                ResoueceSMPSORunner.SMPSO(args);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
/**
 * @author ：mmzs
 * @date ：Created in 11/14 0014 15:35
 * @description：
 * @modified By：
 * @version: $
 */