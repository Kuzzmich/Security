package com.company;

import java.io.*;

/**
 * Created by Алексей on 13.03.2016.
 */
public class MakeBasicRules {
    public void readAndMake(){
        int maxLineNum=20;
        int lineNum=0;
        String srchString=null;
        String anchor="////";
        try {
            LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader("Student.java")));
            for (int i=0;i<maxLineNum; i++){
                String tmpString = lnr.readLine();
                if(tmpString == null)
                    break;s

                if(tmpString.indexOf(anchor) != -1)
                {
                    srchString=tmpString;
                    break;
                }
            }

            srchString=srchString.replaceAll("\\////|\\--", "");    // Топорный метод, но удаляемые
            srchString=srchString.trim();                           // элементы изначально известны

            FileWriter fw=new FileWriter("Matrix.txt", true);
            fw.append(srchString+System.getProperty("line.separator"));
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
