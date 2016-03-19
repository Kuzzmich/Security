package com.company;

/**
 * Created by Алексей on 07.03.2016.
 */
public class Group extends Director {
    private String name;
    private int quantity;
    {
        name ="default";
    }
    public Group(){}

    public Group(String nameOfGr, int quantOfStudents){
        name=nameOfGr;
        quantity=quantOfStudents;
    }

    //--------getters generated--------
    @Override
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    //--------setters generated--------
    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
