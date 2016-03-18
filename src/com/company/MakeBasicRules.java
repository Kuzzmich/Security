package com.company;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Алексей on 13.03.2016.
 */
public class MakeBasicRules extends SecureObjectRoot {
    public void readAndMake(){
        String anchorStart="<begin>";
        String anchorStop="<end>";
        String className="";
        ArrayList<String> fromClasses=new ArrayList<>();
        SecurityMonitor monitor=new SecurityMonitor(new SecureObjectContainer());

        try {
            /*Class <?> from=Class.forName("com.company.AcademicPlan");
            Class <?> to =Class.forName("com.company.Rector");

            AcademicPlan acm=(AcademicPlan) from.newInstance();
            Rector rc=(Rector) to.newInstance();
            SecurityMonitor monitor=new SecurityMonitor();
            SecureObjectPair pair=new SecureObjectPair(acm,rc);
            SecurityRights rights=new SecurityRights(true,true,false,false);
            monitor.getBaseRules().put(pair,rights);

            File out=new File("out.dat");
            if (out.exists())monitor.loadDefaultRules(out);
            else monitor.saveDefaultRules(out);
            acm.create();
            monitor.getBaseRules().entrySet().forEach(System.out::println);*/

            FileWriter fw=new FileWriter("Matrix.txt", true);
            BufferedReader buffRead = new BufferedReader(new FileReader("src/com/company/Student.java"));
            String tmpString = buffRead.readLine();

            while(!tmpString.equals(anchorStop)){
                tmpString = buffRead.readLine();

                if(tmpString.indexOf(anchorStart) != -1)
                {
                    tmpString=buffRead.readLine();

                    if (tmpString.split(" ").length==1) {
                        className=tmpString;
                        tmpString=buffRead.readLine();
                    }

                    while(!tmpString.equals(anchorStop)) {
                        fromClasses.add(tmpString);
                        fw.append(tmpString).append(System.getProperty("line.separator"));
                        tmpString=buffRead.readLine();

                    }

                    fw.close();
                }
            }

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
                System.out.println(monitor.getBaseRules().entrySet());
                System.out.println(System.lineSeparator());
            }

            monitor.getBaseRules().entrySet().forEach(System.out::println);
            File out=new File("out.dat");
            if (out.exists())monitor.loadDefaultRules(out);
            else monitor.saveDefaultRules(out);


        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
