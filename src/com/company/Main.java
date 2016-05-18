package com.company;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.IOException;


public class Main {
    public static SecurityMonitor monitor=new SecurityMonitor();
    public static SecureObjectContainer container = new SecureObjectContainer();

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
        LocalPerson localPerson=new LocalPerson("birth1", "residence1");
        LocalPerson localPerson2=new LocalPerson("birth2", "residence2");
        Journal journal=new Journal("nameOfJournal1", "21.01.01");
        Learner learner1=new Learner("trainProgram1","21.02.02");
        Learner learner2=new Learner("trainProgram2","21.02.02");
        Employee employee1=new Employee("fullNameEmployee1", "01.01.01");
        Employee employee2=new Employee("fullNameEmployee2", "02.02.01");
        AcademicPlan academicPlan=new AcademicPlan("nameOfPlan1", "21.10.01");
        Schoolbook schoolbook=new Schoolbook("schoolbookName","author");
        Person person1=new Person("personFullname1", "12.12.00");
        Person person2=new Person("personFullname2", "12.12.12");
        Student student=new Student("IITT","IBAS");
        Teacher teacher=new Teacher("IITT","Stavropol");

        //========================

        monitor.addObjectToContainerRoot(person1);
        try {
            monitor.createRequest(person1,schoolbook);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        try {
            monitor.createRequest(person1, employee1);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        monitor.addObjectToContainerRoot(localPerson);
        try {
            monitor.createRequest(localPerson, academicPlan);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        try {
            monitor.createRequest(localPerson, employee2);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        try {
            monitor.createRequest(localPerson, person2);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        monitor.addObjectToContainerRoot(localPerson2);
        try {
            monitor.createRequest(localPerson2, learner1);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        try {
            monitor.createRequest(localPerson2, learner2);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        try {
            monitor.createRequest(localPerson2, journal);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        try {
            monitor.createRequest(localPerson2, student);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }

        try {
            monitor.createRequest(localPerson2, teacher);
        } catch (RestrictedByCurrentRulesException e) {
            e.printStackTrace();
        } catch (RestrictedByDefaultRulesException e) {
            e.printStackTrace();
        }
    }
}