package com.company;

import java.io.*;
import java.util.HashSet;

/**
 * Created by tokhchukov on 11.03.2016.
 */
public class SecureObjectContainer {
    private static HashSet <SecureObjectRoot> objects;
    static
    {
        objects=new HashSet<SecureObjectRoot>();
    }
    public static synchronized void addObject(SecureObjectRoot obj){
        objects.add(obj);
    }

    public static synchronized void removeObject(SecureObjectRoot obj){
        if (objects.contains(obj))objects.remove(obj);
    }

    public static synchronized void saveState(File out) {
        try {
            if (!out.exists()) out.createNewFile();
            FileOutputStream outstr = new FileOutputStream(out);
            ObjectOutputStream objout = new ObjectOutputStream(outstr);
            objout.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static synchronized boolean loadState(File in){
        FileInputStream fis;
        try{
            if (!in.exists())return false;
            else {
                fis=new FileInputStream(in);
                ObjectInputStream oinp=new ObjectInputStream(fis);
                objects=(HashSet<SecureObjectRoot>)oinp.readObject();
                fis.close();
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
     return false;
    }

}
