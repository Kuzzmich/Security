package com.company;

/**
 * Created by Алексей on 07.03.2016.
 */
public class Employee extends LocalPerson {
    private String department;
    private String status;
    {
        department ="default";
    }

    public Employee(){}

    public Employee(String name, String fullName, String birthday) {
        super(name, "default", "default");
        this.department = fullName;
        this.status = birthday;
    }

    public String fullName(){
        return super.getName();
    }
    //--------getters generated--------
    public String getDepartment() {
        return department;
    }

    public String getStatus() {
        return status;
    }

    //--------getters generated--------
    public void setName(String placeBirth) {
        this.department = placeBirth;
    }

    public void setBirth(String placeResidence) {
        this.status = placeResidence;
    }
}