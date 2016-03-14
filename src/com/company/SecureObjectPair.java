package com.company;

import java.io.Serializable;

/**
 * Created by tokhchukov on 09.03.2016.
 */
public class SecureObjectPair implements Serializable {
    private ISecureObj first;
    private ISecureObj  second;

public SecureObjectPair(){}
    public SecureObjectPair(ISecureObj frst, ISecureObj scnd)
    {
        first=frst;
        second=scnd;
    }
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
}
