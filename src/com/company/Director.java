package com.company;

/**
 * Created by kuzzm on 05.03.2016.
 */
public class Director extends Rector {
    private String name;
    private String birth;
    {
        name ="default";
    }
    public Director(){}

    public Director(String fullName, String birthday) {
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
