package com.company;

/**
 * Created by Алексей on 17.04.2016.
 */
public class Schoolbook extends SecureObjectRoot{
    private String name;
    private String author;

    public Schoolbook(){}

    public Schoolbook(String name, String author){
        this.name=name;
        this.author=author;
    }
    //--------getters generated--------
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    //--------setters generated--------
    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
