package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Алексей on 13.03.2016.
 */
public class MakeBasicRules extends SecureObjectRoot {
    // список пройденных классов
    ArrayList<String> doneClasses=new ArrayList<>();
    SecurityMonitor monitor = new SecurityMonitor(new SecureObjectContainer());
    // Чтение файла и формирование списка классов
    private ArrayList<String> readFile(String url){
        String anchorStart="<begin>";
        String anchorStop="<end>";
        ArrayList<String> classes=new ArrayList<>();

        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(url));
            String tmpString = buffRead.readLine();

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
        for (String classListElem : classList)
            fromClassNames.add(classListElem.substring(0, classListElem.indexOf(" ")));
        return fromClassNames;
    }

    //Общий список правил
    private ArrayList<String> getRulesList(ArrayList<String> classList){
        for (int i=0; i<classList.size(); i++)
            classList.set(i, classList.get(i).replace(classList.get(i).substring(0, classList.get(i).indexOf(" ")), "").trim());
        return classList;
    }

    private void getRules(String path, String child, ArrayList<String> tempArray){
        ArrayList<String> fileContent = readFile(path);
        String toClassName = fileContent.get(0);
        String parentName = fileContent.get(1).split("\\.")[2]+".sec";
        String childName = child;
        if(childName!=null)
            tempArray.add(child);
        fileContent.remove(0);
        fileContent.remove(0);
        // получаю имена и правила для вызывающих классов
        ArrayList<String> fromClassNames = getClassNames(fileContent);
        ArrayList<String> rulesList = getRulesList(fileContent);

        String[] rulesArr = {"", "", "", "", ""};

        try {
            Class<?> classNameTo = Class.forName(toClassName);
            SecureObjectRoot toObj = (SecureObjectRoot) classNameTo.newInstance();
            for (int i = 0; i < fromClassNames.size(); i++) {
                Class<?> from = Class.forName(fromClassNames.get(i));
                SecureObjectRoot fromObj = (SecureObjectRoot) from.newInstance();
                SecureObjectPair pair = new SecureObjectPair(fromObj, toObj);
                rulesArr = rulesList.get(i).split(" ");
                SecurityRights rights = new SecurityRights(
                        Boolean.parseBoolean(rulesArr[0]),
                        Boolean.parseBoolean(rulesArr[1]),
                        Boolean.parseBoolean(rulesArr[2]),
                        Boolean.parseBoolean(rulesArr[3]),
                        Boolean.parseBoolean(rulesArr[4]));
                if ((rulesArr[3].equals("true"))&&(childName!=null)){
                    for (int j=tempArray.size()-1; j>=0; j--) {
                        Class<?> classNameToChild = Class.forName(tempArray.get(j));
                        SecureObjectRoot toObjChild = (SecureObjectRoot) classNameToChild.newInstance();
                        SecureObjectPair pairChild = new SecureObjectPair(fromObj, toObjChild);
                        monitor.getBaseRules().replace(pairChild, rights);
                    }
                }
                monitor.getBaseRules().put(pair, rights);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (!(parentName.equals("SecureObjectRoot.sec"))) {
            getRules("tmp\\" + parentName, toClassName, tempArray);
        } else {
            return;
        }
    }
// Запись правил в файл
    private void writeRulesToFile(){
        monitor.getBaseRules().entrySet().forEach(System.out::println);
        File out = new File("default.dat");
        if (out.exists()) try {
            monitor.loadDefaultRules(out);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        else monitor.saveDefaultRules(out);
    }

    // Создание базовой матрицы
    public void makeRules(File f) throws NullPointerException{
        ArrayList<File> files=new ArrayList<>(Arrays.asList(f.listFiles()));
        for(File filePath:files) {
            String path=filePath.getPath();
            ArrayList<String> tmpArr=new ArrayList<>();
            getRules(path, null, tmpArr);
        }
        writeRulesToFile();
    }
}
