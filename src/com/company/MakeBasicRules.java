package com.company;

import java.io.*;

/**
 * Created by Алексей on 13.03.2016.
 */
public class MakeBasicRules {
    public void readAndMake(){
        int maxLineNum=20;
        String tmpString="";
        String anchorStart="<begin>";
        String anchorStop="<end>";
        try {
            FileWriter fw=new FileWriter("Matrix.txt", true);
            LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader("src/com/company/Student.java")));
            while(!tmpString.equals(anchorStop)){
                tmpString = lnr.readLine();
                if(tmpString == null)
                    break;

                if(tmpString.indexOf(anchorStart) != -1)
                {
                    tmpString=lnr.readLine();
                    while(!tmpString.equals("<end>")) {
                        fw.append(tmpString + System.getProperty("line.separator"));
                        tmpString=lnr.readLine();

                    }
                    fw.close();
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
