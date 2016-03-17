package com.company;



/**
 * Created by tokhchukov on 09.03.2016.
 */
public class SecurityRights {
    private boolean create=false;
    private boolean update=false;
    private boolean delete=false;
    public SecurityRights(){}
    public SecurityRights(boolean creatingright, boolean updatingright,boolean deletingrigth) {
        create=creatingright;
        update=updatingright;
        delete=deletingrigth;
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

    @Override
    public String toString() {
        return "SecurityRights{" +
                "create=" + create +
                ", update=" + update +
                ", delete=" + delete +
                '}';
    }
}
