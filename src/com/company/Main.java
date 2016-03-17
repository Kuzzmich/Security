package com.company;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        /*Scanner sc = new Scanner(System.in);ы
        String name = sc.next();*/
        Rector rc = new Rector("Gus", "default");
        rc.create();
        try {
            rc.saveState();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }
}
