package org.uma.jmetal.runner.multiobjective.ownCode.datagenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class data {
    public static void main(String[] args) {
        FarBetterGernerator();
    }
    public static void NodifferceGernerator(){
        File file = null;
        FileWriter fw = null;
        Date date = new Date();
        Random random = new Random();

        try {
            file = new File("./"+System.currentTimeMillis()%1000 + ".txt");
            if (!file.exists()) {

            }
            fw = new FileWriter(file);
            for (int i = 1; i <= 10000; i++) {
                //成本0.5-1 时间0.2-1 可靠性0.8-1 质量0-1
                double r1 = random.nextDouble();
                double r2 = random.nextDouble();
                double r3 = random.nextDouble();
                double r4 = random.nextDouble();

                double cost=0.5+r1*0.5;
                double time =0.2+r2*0.8;
                double rely=0.8+r3*0.1+cost*0.1;
                double quality=0.7*r4+0.2*(r2)+0.1*cost;
                fw.write(cost+" "+time+" "+rely+" "+quality+"\r\n");
                fw.flush();
            }
            System.out.println("写数据成功！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public static void FarBetterGernerator(){
        File file = null;
        FileWriter fw = null;
        Date date = new Date();
        Random random = new Random();

        try {
            file = new File("./"+System.currentTimeMillis()%1000 + "better.txt");
            if (!file.exists()) {

            }
            fw = new FileWriter(file);
            for (int i = 1; i <= 50000; i++) {
                //成本0.5-1 时间0.2-1 可靠性0.8-1 质量0-1
                double r1 = random.nextDouble();
                double r2 = random.nextDouble();
                double r3 = random.nextDouble();
                double r4 = random.nextDouble();
                double r5=random.nextDouble();
                double l=(i%1000)*0.0003*r5;
                double cost=0.5+r1*0.5-random.nextDouble()*l;
                double time =0.2+r2*0.8-l*random.nextDouble();
//                double rely=0.8+r3*0.2-cost*0.1;
//                double quality=0.7*r4+0.2*(r2)+0.1*cost;
                double rely=0.8+r3*0.1+cost*0.1+l*random.nextDouble(); //0.05
                double quality=0.7*r4+0.15*(r2)+0.1*r1+l*random.nextDouble(); //0.10
                fw.write(cost+" "+time+" "+rely+" "+quality+"\r\n");
                fw.flush();
            }
            System.out.println("写数据成功！");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
/**
 * @author ：mmzs
 * @date ：Created in 11/13 0013 18:42
 * @description：
 * @modified By：
 * @version: $
 */