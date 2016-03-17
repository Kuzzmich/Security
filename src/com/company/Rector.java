package com.company;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by kuzzm on 05.03.2016.
 */
public class Rector extends SecureObjectRoot{
    private String name="Default name";
    private String birth="Default birth";

    public Rector(String fullName, String birthday){
        name=fullName;
        birth=birthday;
    }

    public Rector(){}

    public String fullName(){
        return getName();
    }

    //---------------getters generated----------------
    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    //---------------setters generated----------------

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}

