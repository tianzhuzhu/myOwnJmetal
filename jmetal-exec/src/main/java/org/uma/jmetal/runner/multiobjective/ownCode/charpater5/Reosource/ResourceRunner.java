package org.uma.jmetal.runner.multiobjective.ownCode.charpater5.Reosource;

import org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QosResource;

public class ResourceRunner {
    public static void main(String[] args) {
        String problemName="org.uma.jmetal.runner.multiobjective.ownCode.OwnProblem.Capater4.QosResource";

        String referenceParetoFront="";
//        String problemName="org.uma.jmetal.problem.multiobjective.zdt.ZDT3";
//
//        String referenceParetoFront="jmetal-problem/src/test/resources/pareto_fronts/ZDT3.pf";
        if(args==null||args.length<2) {
            args = new String[2];
            args[0]=problemName;
            args[1]=referenceParetoFront;
        }
        int i=1;
        while(i<5) {
            QosResource.setAmount(i*200);

            try {
                ResourceEGDE3Runner.EDGE3(args);
//                ResourceMOCellRunner.MOCell(args);
//                ResoueceNSGAIIRunner.NSGAII(args);
//                ResoueceSMPSORunner.SMPSO(args);

            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
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