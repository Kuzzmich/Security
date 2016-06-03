package com.company;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static SecurityMonitor monitor=new SecurityMonitor();
    public static SecureObjectContainer container = new SecureObjectContainer();
    private static Random random=new Random();
    public static void main(String[] args) {
        File defaultRules=new File("default.dat");
        defaultRulesFile();

        try {
            loadOrSave(defaultRules);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        monitorInitialize();
        SecurityLogger logger=SecurityLogger.getInstance();
        logger.close();
    }

    public static void add(SecureObjectRoot from, SecureObjectRoot to,boolean create,boolean update,boolean delete){

        Class <?> frm=from.getClass();
        Class <?> too=to.getClass();
        SecurityRights rights=new SecurityRights(create,update,delete,true,true);

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

    public static void defaultRulesFile(){
        File f=new File("tmp");
        MakeBasicRules initialRules=new MakeBasicRules();
        initialRules.makeRules(f);
    }


    public static void monitorInitialize(){
        monitor.attachContainer(container);
        LocalPerson localPerson=new LocalPerson("LocalName1","birth1", "residence1");
        LocalPerson localPerson2=new LocalPerson("LocalName2","birth2", "residence2");
        Journal journal=new Journal("nameOfJournal1", "21.01.01");
        Learner learner1=new Learner("LearnerName1","trainProgram1","21.02.02");
        Learner learner2=new Learner("LearnerName2","trainProgram2","21.02.02");
        Employee employee1=new Employee("EmployeeName1","DeptEmployee1", "01.01.01");
        Employee employee2=new Employee("EmployeeName2","DeptEmployee2", "02.02.01");
        AcademicPlan academicPlan=new AcademicPlan("nameOfPlan1", "21.10.01");
        Schoolbook schoolbook=new Schoolbook("schoolbookName","author");
        Person person1=new Person("personFullname1", "12.12.00");
        Person person2=new Person("personFullname2", "12.12.12");
        Student student=new Student("StudentName1","IITT","IBAS");
        Teacher teacher=new Teacher("TeacherName2","IITT","Stavropol");




        //-------------------------
        System.out.println("Base rules:");
        System.out.printf("%-20s %-20s %-10s %-10s %-10s %-10s %-10s\n","FirsClass",  "SecondClass",  "Create","Update","Delete","IsDerived","IsSetByDefault");
        monitor.getBaseRules().entrySet().stream().forEach(x->
                {
                    String first=SecurityLogger.cutName(x.getKey().getFirst().getClass().getName());
                    String second=SecurityLogger.cutName(x.getKey().getSecond().getClass().getName());

                    SecurityRights value=x.getValue();
                    String rights=String.format("%-10s %-10s %-10s %-10s %-10s",value.isCreate(),
                            value.isUpdate(), value.isDelete(), value.isDerived(),value.isSetbydefault());
                    System.out.printf("%-20s %-20s %s\n",first,second,rights);


                }
        );
//        System.out.println("Count: "+monitor.getBaseRules().entrySet().stream().count());








        //========================




        monitor.addObjectToContainerRoot(person1);

        try {
            monitor.createRequest(person1,teacher);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();

        try {
            monitor.createRequest(person1,schoolbook);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();
        try {
            monitor.createRequest(person1, employee1);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();
        monitor.addObjectToContainerRoot(localPerson);
        try {
            monitor.createRequest(localPerson, academicPlan);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();
        try {
            monitor.createRequest(localPerson, employee2);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();
        try {
            monitor.createRequest(localPerson, person2);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();
        monitor.addObjectToContainerRoot(localPerson2);
        try {
            monitor.createRequest(localPerson2, learner1);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();
        try {
            monitor.createRequest(localPerson2, learner2);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();
        try {
            monitor.createRequest(localPerson2, journal);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        sleep();
        try {
            monitor.createRequest(localPerson2, student);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
        /*sleep();
        try {
            monitor.createRequest(localPerson2, teacher);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }*/
        System.out.println("Monitored objects:");
        container.getObjects().stream().forEach(item -> System.out.println("Object: " + item.getClass() + " : " + item.fullName()));


        System.out.println("---------------------------------Current rules matrix:--------------------------------------------");
        System.out.printf("%-20s %-20s %-20s %-20s %-10s %-10s %-10s %-10s %-10s\n","FirsClass", "Name", "SecondClass", "Name", "Create","Update","Delete","IsDerived","IsSetByDefault");
        monitor.getCurrentRules().entrySet().stream().forEach(x->
                {
                    String first=SecurityLogger.cutName(x.getKey().getFirst().getClass().getName());
                    String second=SecurityLogger.cutName(x.getKey().getSecond().getClass().getName());

                    String firstName=x.getKey().getFirst().fullName();
                    String secondName=x.getKey().getSecond().fullName();
                    SecurityRights value=x.getValue();
                    String rights=String.format("%-10s %-10s %-10s %-10s %-10s",value.isCreate(),
                            value.isUpdate(), value.isDelete(), value.isDerived(),value.isSetbydefault());

                    System.out.printf("%-20s %-20s %-20s %-20s %s\n",first,firstName,second,secondName,rights);


                }
        );
//        System.out.println("Count: "+monitor.getCurrentRules().entrySet().stream().count());
//        --------------------------==============================----------------------
        sleep();
        try {
            localPerson.update(localPerson2,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            journal.update(localPerson,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            journal.update(localPerson2,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

       /* sleep();
        try {
            student.update(teacher,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }*/

        /*sleep();
        try {
            student.update(teacher,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }*/

        sleep();
        try {
            learner1.update(student,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            learner2.update(student,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            person1.update(localPerson,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            person2.update(localPerson,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            employee1.update(localPerson,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            employee2.update(localPerson,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        /*sleep();
        try {
            academicPlan.update(teacher,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }*/

        sleep();
        try {
            academicPlan.update(schoolbook,monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            schoolbook.update(student, monitor);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
//---------------------------------==================================------------------------------

        sleep();
        try {
            monitor.deleteRequest(localPerson,employee1);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            monitor.deleteRequest(localPerson2,employee2);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            monitor.deleteRequest(learner1,schoolbook);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        /*sleep();
        try {
           monitor.deleteRequest(teacher,academicPlan);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }*/

        sleep();
        try {
            monitor.deleteRequest(person1,localPerson);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        sleep();
        try {
            monitor.deleteRequest(person1,learner1);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        System.out.println("Monitored objects:");
        container.getObjects().stream().forEach(item -> System.out.println("Object: " + item.getClass() + " : " + item.fullName()));
        //----------------======================--------------------
        System.out.printf("%-20s %-20s %-20s %-20s %-10s %-10s %-10s %-10s %-10s\n","FirsClass", "Name", "SecondClass", "Name", "Create","Update","Delete","IsDerived","IsSetByDefault");
        monitor.getCurrentRules().entrySet().stream().forEach(x->
            {
                String first=SecurityLogger.cutName(x.getKey().getFirst().getClass().getName());
                String second=SecurityLogger.cutName(x.getKey().getSecond().getClass().getName());

                String firstName=x.getKey().getFirst().fullName();
                String secondName=x.getKey().getSecond().fullName();
                SecurityRights value=x.getValue();
                String rights=String.format("%-10s %-10s %-10s %-10s %-10s",value.isCreate(),
                        value.isUpdate(), value.isDelete(), value.isDerived(),value.isSetbydefault());

                System.out.printf("%-20s %-20s %-20s %-20s %s\n",first,firstName,second,secondName,rights);


            }
        );
//        System.out.println("Count: "+monitor.getCurrentRules().entrySet().stream().count());






    }
    private static void sleep(){
        try {
            Thread.sleep(random.nextInt(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}