package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Алексей on 13.03.2016.
 */
public class MakeBasicRules extends SecureObjectRoot {
    // Чтение файла
    private ArrayList<String> readFile(String url){
        String anchorStart="<begin>";
        String anchorStop="<end>";
        ArrayList<String> classes=new ArrayList<>();

        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(url));
            String tmpString = buffRead.readLine();

            // Выборка строк и прав на основании строк
            while(!tmpString.equals(anchorStop)){
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

    //Выборка имён классов взаимодействующих с данным
    private ArrayList<String> getClassNames(ArrayList<String> classList){
        ArrayList<String> fromClassNames=new ArrayList<>();
        for (int i=0; i<classList.size(); i++)
            fromClassNames.add(classList.get(i).substring(0, classList.get(i).indexOf(" ")));
        return fromClassNames;
    }

    //Общий список правил
    private ArrayList<String> getRulesList(ArrayList<String> classList){
        for (int i=0; i<classList.size(); i++)
            classList.set(i, classList.get(i).replace(classList.get(i).substring(0, classList.get(i).indexOf(" ")), "").trim());
        return classList;
    }

    // Создание базовой матрицы
    public void makeRules(){
        Scanner sc=new Scanner(System.in);
        File f=new File(sc.next());
        ArrayList<File> files=new ArrayList<File>(Arrays.asList(f.listFiles()));
        for(File path:files) {
            ArrayList<String> classList = readFile(path.getPath());
            String toClassName = classList.get(0);
            classList.remove(0);
            ArrayList<String> fromClassNames = getClassNames(classList);
            ArrayList<String> rulesList = getRulesList(classList);
            SecurityMonitor monitor = new SecurityMonitor(new SecureObjectContainer());
            String[] rulesArr = {"", "", ""};

            try {
                Class<?> classNameTo = Class.forName(toClassName);
                SecureObjectRoot toObj = (SecureObjectRoot) classNameTo.newInstance();

                for (int i = 0; i < fromClassNames.size(); i++) {
                    Class<?> from = Class.forName(fromClassNames.get(i));
                    SecureObjectRoot fromObj = (SecureObjectRoot) from.newInstance();
                    SecureObjectPair pair = new SecureObjectPair(fromObj, toObj);
                    rulesArr = rulesList.get(i).split(" ");
                    SecurityRights rights = new SecurityRights(Boolean.parseBoolean(rulesArr[0]),
                            Boolean.parseBoolean(rulesArr[1]),
                            Boolean.parseBoolean(rulesArr[2]), false);
                    monitor.getBaseRules().put(pair, rights);
                }

                monitor.getBaseRules().entrySet().forEach(System.out::println);
                File out = new File("default.dat");
                if (out.exists()) monitor.loadDefaultRules(out);
                else monitor.saveDefaultRules(out);

            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
