/*
<begin>
com.company.Student
com.company.Rector false true true
com.company.Director true true true
com.company.Deanery true true true
com.company.HeadOfDep false true true
com.company.Teacher false true true
com.company.Group false false false
<end>
*/

package com.company;

/**
 * Created by Алексей on 07.03.2016.
 */
public class Student extends Group {
    private String name;
    private String birth;

    public Student(){}

    public Student(String fullName, String birthday) {
        this.name = fullName;
        this.birth = birthday;
    }

    public String fullName(){
        return getName();
    }
    //--------getters generated--------

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBirth() {
        return birth;
    }

    //--------getters generated--------

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBirth(String birth) {
        this.birth = birth;
    }
}
