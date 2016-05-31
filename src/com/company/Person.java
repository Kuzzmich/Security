package com.company;

/**
 * Created by kuzzm on 05.03.2016.
 */

public class Person extends SecureObjectRoot{
    protected String name="Default name";
    private String birth="Default birth";
    {
        name ="default";
    }

    public Person(String fullName, String birthday){
        name=fullName;
        birth=birthday;
    }

    public Person(){}

    public String fullName(){
        return getName();
    }

    /*public void saveState(Object obj) throws IOException {
        FileOutputStream fos = new FileOutputStream(fullName()+".out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
    }*/

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

