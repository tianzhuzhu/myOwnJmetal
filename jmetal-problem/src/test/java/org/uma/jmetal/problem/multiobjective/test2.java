package org.uma.jmetal.problem.multiobjective;

import org.junit.Test;

import java.io.*;
import java.util.*;


public class test2 {
    @Test
    public    void test2() throws IOException {
        List<List<Double>> list=new ArrayList<>();
        File file = null;
        FileReader fw = null;
        Date date = new Date();
        Random random = new Random();
        File wfile=null;
        try {
            FileOutputStream fos = new FileOutputStream("full.txt", false);
//      Scanner scanner=new Scanner(new FileInputStream("C:\\Users\\lujin\\OneDrive\\论文\\基于药事服务资源协同认知的调度方法研究\\myOwnJmetal\\459.txt"));
            reader.ExcelData sheet1 = new reader.ExcelData("C:\\Users\\lujin\\OneDrive\\论文\\基于药事服务资源协同认知的调度方法研究\\myOwnJmetal\\distance.xlsx", "sheet1");
            //获取第二行第4列
            String cell2 = sheet1.getExcelDateByIndex(1, 3);
            //根据第3列值为“customer23”的这一行，来获取该行第2列的值
            double max = Double.MIN_VALUE;
            Scanner scanner=new Scanner(new FileInputStream("C:\\Users\\lujin\\OneDrive\\论文\\基于药事服务资源协同认知的调度方法研究\\myOwnJmetal\\length.txt"));
            for (int i = 1; i <35 ; i++) {
                List<Double> doubleList=new ArrayList<>();
                for (int j = 1; j < 35; j++) {
                    double b = Double.parseDouble(sheet1.getExcelDateByIndex(i,j));
                    doubleList.add(b);
                    max = Math.max(max, b);
                }
                list.add(doubleList);
            }
            for (int k = 0; k < list.size(); k++) {
                StringBuilder stringBuilder=new StringBuilder();
                List<Double> doubleList1 = list.get(k);
                for (int l = 0; l < doubleList1.size(); l++) {
                    stringBuilder.append(doubleList1.get(l)/max+" ");
                }
                stringBuilder.append("\r\n");
                fos.write(stringBuilder.toString().getBytes());
            }
            fos.close();
        } catch (Exception e) {
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