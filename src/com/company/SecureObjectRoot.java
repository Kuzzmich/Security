package com.company;

import java.io.Console;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by kuzzm on 05.03.2016.
 */
public abstract class SecureObjectRoot implements ISecureObj{
    public final void create(){
        System.out.println(this.toString()+" "+this.fullName()+" was created");
    }

    public final void delete(){
        System.out.println(this.toString()+" was deleted");
    }

    public final void update(){
        System.out.println(this.toString()+" was changed");
    }

    public void saveState() throws IOException {
        FileOutputStream fos = new FileOutputStream(fullName()+".out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
}
