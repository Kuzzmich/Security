package com.company;

/**
 * Created by kuzzm on 05.03.2016.
 */
public class LocalPerson extends Person {
    private String placeOfBirth;
    private String placeOfResidence;
    {
        placeOfBirth ="default";
    }
    public LocalPerson(){}

    public LocalPerson(String name, String placeBirth, String placeResidence) {
        this.placeOfBirth = placeBirth;
        this.placeOfResidence = placeResidence;
    }

    @Override
    public String fullName(){
        return super.getName();
    }

    //--------getters generated--------
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    @Override
    public String getBirth() {
        return placeOfResidence;
    }

    //--------getters generated--------

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }
}
