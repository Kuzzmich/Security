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
        return getName();
    }

    //--------getters generated--------
    @Override
    public String getName() {
        return placeOfBirth;
    }

    @Override
    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    //--------getters generated--------

    @Override
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Override
    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }
}
