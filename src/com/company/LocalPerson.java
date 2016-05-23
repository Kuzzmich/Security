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

    public LocalPerson(String placeBirth, String placeResidence) {
        this.placeOfBirth = placeBirth;
        this.placeOfResidence = placeResidence;
    }

    @Override
    public String fullName(){
        return getPlaceOfBirth();
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

    @Override
    public void setName(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Override
    public void setBirth(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }
}
