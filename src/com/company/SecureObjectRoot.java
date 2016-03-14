package com.company;

import java.io.Console;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by kuzzm on 05.03.2016.
 */
public  class SecureObjectRoot implements ISecureObj{
    public final void create(){
        System.out.println(this.toString()+" "+this.fullName()+" was created");
    }

    public final void delete(){
        System.out.println(this.toString()+" was deleted");
    }

    public final void update(){
        System.out.println(this.toString()+" method callg");
    }

    public void saveState() throws IOException {
        FileOutputStream fos = new FileOutputStream(fullName()+".out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
    public String fullName(){
        return "Root object";
    }
    @Override
    public final int hashCode(){
       return (fullName()+getClass().getName()).hashCode();
    }

    public final boolean equals(Object o){
        if (!(o instanceof SecureObjectRoot))return false;
        SecureObjectRoot that=(SecureObjectRoot)o;
        return this.hashCode()==that.hashCode();

    }
}
