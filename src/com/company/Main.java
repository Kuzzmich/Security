package com.company;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;


public class Main {
    public static SecurityMonitor monitor=new SecurityMonitor();
    public static void main(String[] args) {
       /* while(true){
            Scanner sc=new Scanner(System.in);
            sc.next();
            sc.close();
        }*/

        Person rc = new Person("Gus", "default");

        /*Scanner sc = new Scanner(System.in);
        String name = sc.next();*/


        LocalPerson d = new LocalPerson("Chip", "4486");
        Employee h = new Employee("Sug", "4576");
        Teacher t = new Teacher("Linets", "4576");
        Student s = new Student("Martiros", "2222");
        Schedule j = new Schedule("Electro", "1234");
        AcademicPlan ap = new AcademicPlan("Plan", "2016");

        add(rc, d, true, true, false);
        add(d, rc, false, false, true);
        add(rc, h, true, false, false);
        add(h, rc, false, false, false);
        add(d, h, true, false, true);
        add(h, d, false, false, false);

        SecureObjectContainer container = new SecureObjectContainer();
        /*try {
            ObjectInputStream oi=new ObjectInputStream(new FileInputStream("collection.dat"));
            container=oi.readObject();
        }*/
        monitor.attachContainer(container);

        try {
            Scanner sc=new Scanner(System.in);
            File f=new File(sc.next());
            MakeBasicRules mbr=new MakeBasicRules();
            mbr.makeRules(f);
//            monitor.createRequest(rc, d);
            SecureObjectPair pair=new SecureObjectPair(rc,d);
           // System.out.println(monitor.addCurrentRule(pair, new SecurityRights(false, false, false,true)));
          //  System.out.println(monitor.addCurrentRule(pair, new SecurityRights(false, false, true, true)));
            loadOrSave(new File("default.dat"));
            loadOrSaveCurrent(new File("current.dat"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void add(SecureObjectRoot from, SecureObjectRoot to,boolean create,boolean update,boolean delete){

        Class <?> frm=from.getClass();
        Class <?> too=to.getClass();
        SecurityRights rights=new SecurityRights(create,update,delete,false,false);

        try {
            SecureObjectRoot fromObj=(SecureObjectRoot)frm.newInstance();
            SecureObjectRoot toObj=(SecureObjectRoot)too.newInstance();
            SecureObjectPair pair=new SecureObjectPair(fromObj,toObj);
            monitor.getBaseRules().put(pair,rights);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static void loadOrSave(File file)throws IOException,ClassNotFoundException{
        if (!file.exists()){
            monitor.saveDefaultRules(file);
        }
        else monitor.loadDefaultRules(file);
    }
    public static void loadOrSaveCurrent(File file)throws IOException,ClassNotFoundException{
        if (!file.exists()){
            monitor.saveCurrentRules(file);
        }
        else monitor.loadCurrentRules(file);
    }

}
