package com.company;

/**
 * Created by Алексей on 07.03.2016.
 */
public class Student extends Learner {
    private String institute;
    private String group;

    public Student(){}

    public Student(String name, String institute, String group) {
        super.setName(name);
        this.institute = institute;
        this.group = group;
    }

    public String fullName(){
        return super.getName();
    }
    //--------getters generated--------
    public String getInstitute() {
        return institute;
    }

    public String getGroup() {
        return group;
    }

    //--------getters generated--------
    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
