package com.company;

/**
 * Created by Алексей on 07.03.2016.
 */
public class Teacher extends Employee {
    private String institute;
    private String instituteDepartment;

    public Teacher(){}

    public Teacher(String name, String institute, String instituteDepartment) {
        this.institute = institute;
        this.instituteDepartment = instituteDepartment;
    }

    public String fullName(){
        return super.getName();
    }
    //--------getters generated--------
    public String getInstitute() {
        return institute;
    }

    public String getInstituteDepartment() {
        return instituteDepartment;
    }

    //--------getters generated--------
    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public void setInstituteDepartment(String instituteDepartment) {
        this.instituteDepartment = instituteDepartment;
    }
}
