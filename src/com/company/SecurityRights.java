package com.company;


import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by tokhchukov on 09.03.2016.
 */
public class SecurityRights implements Serializable{
    private boolean create=false;
    private boolean update=false;
    private boolean delete=false;
    private boolean current=true;
    public SecurityRights(){}
    public SecurityRights(boolean creatingRight, boolean updatingRight,boolean deletingRigth, boolean currentRules) {
        create=creatingRight;
        update=updatingRight;
        delete=deletingRigth;
        current=currentRules;
    }

    //---------overriding serialization

    public void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(create);
        stream.writeObject(update);
        stream.writeObject(delete);
        stream.writeObject(current);

    }

    public void readObject(java.io.ObjectInputStream stream)
            throws IOException,ClassNotFoundException{
        create=(boolean)stream.readObject();
        update=(boolean)stream.readObject();
        delete=(boolean)stream.readObject();
        current=(boolean)stream.readObject();
    }





    //----------getters generated----------


    public boolean isCreate() {
        return create;
    }

    public boolean isUpdate() {
        return update;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isCurrent() {
        return current;
    }
    //--------setters generated----------

    public void setCreate(boolean create) {
        this.create = create;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
    public static boolean isValid(@NotNull SecurityRights root,@NotNull SecurityRights child){
        if ((child.isCreate()==true && root.isCreate()!=true)
                || (child.isDelete()==true && root.isDelete()!=true)
                || (child.isUpdate()==true && root.isUpdate()!=true))
        {
            return false;
        }
        else return true;

    }

    @Override
    public String toString() {
        return "SecurityRights{" +
                "create=" + create +
                ", update=" + update +
                ", delete=" + delete +
                ", is current rule=" + current+
                '}';
    }
}