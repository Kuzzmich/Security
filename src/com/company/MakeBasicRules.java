package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
/*
 * На основании возвращаемого листа: Первая строка - имя вызываемого класаа, последующие - вызывающие
 * с правилами доступа. Нужно распарсить это всё
* */

/**
 * Created by Алексей on 13.03.2016.
 */
public class MakeBasicRules extends SecureObjectRoot {
    private ArrayList<String> readFile(String url){
        String anchorStart="<begin>";
        String anchorStop="<end>";
        ArrayList<String> classes=new ArrayList<>();

        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(url));
            String tmpString = buffRead.readLine();

            // Выборка строк и прав на основании строк
            while(!tmpString.equals(anchorStop)){
                tmpString = buffRead.readLine();

                if(tmpString.indexOf(anchorStart) != -1){
                    tmpString=buffRead.readLine();
                    while(!tmpString.equals(anchorStop)) {
                        classes.add(tmpString);
                        tmpString=buffRead.readLine();

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public void MakeRules(){
        /*String anchorStart="<begin>";
        String anchorStop="<end>";
        String className="";
        ArrayList<String> fromClasses=new ArrayList<>();*/
        String toClassName="";
        SecurityMonitor monitor=new SecurityMonitor(new SecureObjectContainer());

        try {
            File f=new File("src/com/company");
            ArrayList<File> files=new ArrayList<>();
            files=new ArrayList<File>(Arrays.asList(f.listFiles()));
            for(File path:files) System.out.println(path);

            /*BufferedReader buffRead = new BufferedReader(new FileReader("src/com/company/Student.java"));
            String tmpString = buffRead.readLine();

            // Выборка строк и прав на основании строк
            while(!tmpString.equals(anchorStop)){
                tmpString = buffRead.readLine();

                if(tmpString.indexOf(anchorStart) != -1){
                    tmpString=buffRead.readLine();

                    if (tmpString.split(" ").length==1) {
                        className=tmpString;
                        tmpString=buffRead.readLine();
                    }

                    while(!tmpString.equals(anchorStop)) {
                        fromClasses.add(tmpString);
                        tmpString=buffRead.readLine();

                    }
                }
            }*/

            // Выборка имён классов взаимодействующих с данным
            ArrayList<String> fromClassNames=new ArrayList<>();
            String[] rulesArr={"","",""};

            for (int i=0; i<fromClasses.size(); i++){
                fromClassNames.add(fromClasses.get(i).substring(0, fromClasses.get(i).indexOf(" ")));
                fromClasses.set(i, fromClasses.get(i).replace(fromClasses.get(i).substring(0, fromClasses.get(i).indexOf(" ")),"").trim());
            }

            Class <?> classNameTo = Class.forName(className);
            SecureObjectRoot toObj=(SecureObjectRoot)classNameTo.newInstance();


            for (int i=0; i<fromClassNames.size(); i++){
                Class <?> from=Class.forName(fromClassNames.get(i));
                SecureObjectRoot fromObj=(SecureObjectRoot)from.newInstance();
                SecureObjectPair pair=new SecureObjectPair(fromObj, toObj);
                rulesArr=fromClasses.get(i).split(" ");
                SecurityRights rights=new SecurityRights(Boolean.parseBoolean(rulesArr[0]),
                                                        Boolean.parseBoolean(rulesArr[1]),
                                                        Boolean.parseBoolean(rulesArr[2]), false);
                monitor.getBaseRules().put(pair,rights);
            }

            monitor.getBaseRules().entrySet().forEach(System.out::println);
            File out=new File("default.dat");
            if (out.exists())monitor.loadDefaultRules(out);
            else monitor.saveDefaultRules(out);


        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
