package com.company;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        /*Scanner sc = new Scanner(System.in);
        String name = sc.next();
        Rector rc = new Rector("Gus", "default");
        rc.create();
        Director d = new Director("Chip", "4486");
        d.create();
        HeadOfDep h = new HeadOfDep("Sug", "4576");
        h.create();
        Teacher t = new Teacher("Linets", "4576");
        t.create();
        Group gr = new Group("ИБС-121", 21);
        gr.create();
        Student s = new Student("Martiros", "2222");
        s.create();
        Journal j = new Journal("Electro", "1234");
        j.create();
        AcademicPlan ap=new AcademicPlan("Plan","2016");
        ap.create();
        SecurityMonitor sm=new SecurityMonitor();
        HashMap<Class<?>,Class<?>> rules=new HashMap<Class<?>,Class<?>>();
        rules.put(rc.getClass(),d.getClass());
        SecureObjectContainer c=new SecureObjectContainer();

        Class<?> fromClass=rc.getClass();
        Class<?> toClass=d.getClass();


        try {
            SecureObjectRoot fromObject=(SecureObjectRoot)fromClass.newInstance();
            SecureObjectRoot toObject=(SecureObjectRoot)toClass.newInstance();
            SecureObjectPair pair=new SecureObjectPair();
            System.out.println(fromObject.fullName()+" "+toObject.getClass().getName());
            System.out.println(toObject.fullName()+" "+toObject.getClass().getName());

        } catch (InstantiationException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }
        try {
            Class <?> from=Class.forName("com.company.AcademicPlan");
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
            monitor.getBaseRules().entrySet().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }
}
