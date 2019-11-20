package org.uma.jmetal.problem.multiobjective;

import org.junit.Test;

import java.io.*;
import java.util.*;


public class test {
    @Test
    public    void test1(){
        List<List<Double>> list=new ArrayList<>();
        File file = null;
        FileReader fw = null;
        Date date = new Date();
        Random random = new Random();
        File wfile=null;

        try {
            FileOutputStream fos = new FileOutputStream("full.txt", true);
//      Scanner scanner=new Scanner(new FileInputStream("C:\\Users\\lujin\\OneDrive\\论文\\基于药事服务资源协同认知的调度方法研究\\myOwnJmetal\\459.txt"));

            double max = Double.MIN_VALUE;
            Scanner scanner=new Scanner(new FileInputStream("C:\\Users\\lujin\\OneDrive\\论文\\基于药事服务资源协同认知的调度方法研究\\myOwnJmetal\\length.txt"));
            while(scanner.hasNext()) {

                String s = scanner.nextLine();
                String[] s1 = s.split("\t");
                List<Double> doubleList=new ArrayList<>();
                for (int i1 = 0; i1 < s1.length; i1++) {
                    double b = Double.parseDouble(s1[i1]);
                    doubleList.add(b);
                    max = Math.max(max, b);
                }

                list.add(doubleList);

                for (int i = 0; i < list.size(); i++) {
                    StringBuilder stringBuilder=new StringBuilder();
                    List<Double> doubleList1 = list.get(i);
                    for (int i1 = 0; i1 < doubleList1.size(); i1++) {
                        stringBuilder.append(doubleList1.get(i1)+"");
                    }
                    stringBuilder.append(i);
                    fos.write(stringBuilder.toString().getBytes());
                }
//
            }

            fos.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
    }
}
/**
 * @author ：mmzs
 * @date ：Created in 11/19 0019 19:00
 * @description：
 * @modified By：
 * @version: $
 */