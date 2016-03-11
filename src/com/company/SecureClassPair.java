package com.company;

/**
 * Created by tokhchukov on 09.03.2016.
 */
public class SecureClassPair {
    private Class<?> first;
    private Class<?>  second;

public SecureClassPair(){}
    public SecureClassPair(Class<?> frst, Class<?> scnd)
    {
        first=frst;
        second=scnd;
    }
    @Override
    public int hashCode() {
        int code=first.getName().hashCode()>>3+second.getName().hashCode()<<2;
        //System.out.println(code);
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecureClassPair that = (SecureClassPair) o;

        if (!(first.getName().hashCode()==that.first.getName().hashCode())) return false;
        return second.getName().hashCode()==that.second.getName().hashCode();

    }
}
