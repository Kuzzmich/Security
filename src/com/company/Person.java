package com.company;

/**
 * Created by kuzzm on 05.03.2016.
 */

public class Person extends SecureObjectRoot{
    private String name="Default name";
    private String birth="Default birth";
    {
        name ="default";
    }

    public Person(String fullName, String birthday){
        name=fullName;
        birth=birthday;
    }

    public Person(){}

    /*public String fullName(){
        return getPlaceOfBirth();
    }*/

    /*public void saveState(Object obj) throws IOException {
        FileOutputStream fos = new FileOutputStream(fullName()+".out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
    }*/

    //---------------getters generated----------------
    public String getPlaceOfBirth() {
        return name;
    }

    public String getPlaceOfResidence() {
        return birth;
    }

    //---------------setters generated----------------

    public void setPlaceOfBirth(String name) {
        this.name = name;
    }

    public void setPlaceOfResidence(String birth) {
        this.birth = birth;
    }
    }

