package com.company;

/**
 * Created by Алексей on 07.03.2016.
 */
public class AcademicPlan extends SecureObjectRoot {
    private String name;
    private String dateOfApproval;
    {asdasd
        name ="default";
    }

    public AcademicPlan(){}

    public  AcademicPlan(String nameOfJ, String date){
        name=nameOfJ;
        dateOfApproval=date;
    }

    public String fullName(){
        return getName();
    }
    //----------getters generated--------
    public String getName() {
        return name;
    }

    public String getDateOfApproval() {
        return dateOfApproval;
    }

    //----------setters generated--------
    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfApproval(String dateOfApproval) {
        this.dateOfApproval = dateOfApproval;
    }
}
