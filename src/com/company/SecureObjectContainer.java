package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by tokhchukov on 11.03.2016.
 */
public class SecureObjectContainer {
    private HashSet <SecureObjectRoot> objects;
    {
        objects=new HashSet<SecureObjectRoot>();
    }
    public synchronized void addObject(SecureObjectRoot obj){
        objects.add(obj);
    }

    public  synchronized void removeObject(SecureObjectRoot obj){
        if (objects.contains(obj))objects.remove(obj);
    }

    public synchronized void saveState(File out) {
        try {
            if (!out.exists()) out.createNewFile();
            FileOutputStream outstr = new FileOutputStream(out);
            ObjectOutputStream objout = new ObjectOutputStream(outstr);
            objout.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public synchronized boolean loadState(File in){
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
    public List<SecureObjectRoot> getObjects(){
        return Arrays.asList(objects.toArray(new SecureObjectRoot[objects.size()]));
    }

    public void printObjects(){
        objects.forEach(x->System.out.println(x.fullName()));
    }

}
