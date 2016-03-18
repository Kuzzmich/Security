package com.company;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by tokhchukov on 09.03.2016.
 */
public class SecureObjectPair implements Serializable{
    private SecureObjectRoot first;
    private SecureObjectRoot  second;

public SecureObjectPair(){}

    public SecureObjectPair(SecureObjectRoot frst, SecureObjectRoot scnd)
    {
        first=frst;
        second=scnd;
    }
    //--------------overriding hash and equals-------------

    @Override
    public int hashCode() {
        int code=first.hashCode()>>3+second.hashCode()<<2;
        //System.out.println(code);
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecureObjectPair that = (SecureObjectPair) o;

        if (!(first.hashCode()==that.first.hashCode())) return false;
        return second.hashCode()==that.second.hashCode();

    }


    //--------------overriding serialization----------

    public void writeObject(java.io.ObjectOutputStream stream)
            throws IOException{
        stream.writeObject(first);
        stream.writeObject(second);
    }

    public void readObject(java.io.ObjectInputStream stream)
            throws IOException,ClassNotFoundException{
        first=(SecureObjectRoot)stream.readObject();
        second=(SecureObjectRoot)stream.readObject();
    }
    //----------------getters generated-------------

    public ISecureObj getFirst() {
        return first;
    }

    public ISecureObj getSecond() {
        return second;
    }

    //---------------setters generated--------------

    public void setFirst(SecureObjectRoot first) {
        this.first = first;
    }

    public void setSecond(SecureObjectRoot second) {
        this.second = second;
    }
}
