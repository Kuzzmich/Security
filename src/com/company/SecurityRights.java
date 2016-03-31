package com.company;


import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by tokhchukov on 09.03.2016.
 */
public class SecurityRights implements Serializable{
    private boolean create=false;     //
    private boolean update=false;    //
    private boolean delete=false;   //
    private boolean derived=true; //По умолчанию права наследуются
    private boolean setbydefault=true;

    public SecurityRights(){}
    //Простой конструктор
    public SecurityRights(boolean creatingRight, boolean updatingRight,boolean deletingRight) {
        create=creatingRight;
        update=updatingRight;
        delete=deletingRight;
        derived=true;
        setbydefault=true;
    }
    //расширенный конструктор
    public SecurityRights(boolean creatingRight, boolean updatingRight,boolean deletingRigth,boolean isDerived, boolean isSetByDefault) {
        create=creatingRight;
        update=updatingRight;
        delete=deletingRigth;
        derived=isDerived;
        setbydefault=isSetByDefault;
    }
    //Вернет истину, если child ужесточает права root
    public static boolean isValid(@NotNull SecurityRights root,@NotNull SecurityRights child){
        return !((child.isCreate() == true && root.isCreate() != true)
                || (child.isDelete() == true && root.isDelete() != true)
                || (child.isUpdate() == true && root.isUpdate() != true));

    }

    @Override
    public String toString() {
        return "SecurityRights{" +
                "create=" + create +
                ", update=" + update +
                ", delete=" + delete +
                ", is derived=" + derived+
                ", is setbydefault=" + setbydefault+
                '}';
    }





    //---------overriding serialization

    public void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(create);
        stream.writeObject(update);
        stream.writeObject(delete);
        stream.writeObject(derived);
        stream.writeObject(setbydefault);


    }

    public void readObject(java.io.ObjectInputStream stream)
            throws IOException,ClassNotFoundException{
        create=(boolean)stream.readObject();
        update=(boolean)stream.readObject();
        delete=(boolean)stream.readObject();
        derived=(boolean)stream.readObject();
        setbydefault=(boolean)stream.readObject();
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

    public boolean isDerived() {
        return derived;
    }

    public boolean isSetbydefault() {
        return setbydefault;
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

    public void setSetbydefault(boolean setbydefault) {
        this.setbydefault = setbydefault;
    }

    public void setDerived(boolean derived) {
        this.derived = derived;
    }
}




