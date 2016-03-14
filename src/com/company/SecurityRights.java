package com.company;



/**
 * Created by tokhchukov on 09.03.2016.
 */
public class SecurityRights {
    private boolean create=false;
    private boolean update=false;
    private boolean delete=false;
    private boolean current=true;
    public SecurityRights(){}
    public SecurityRights(boolean creatingRight, boolean updatingRight,boolean deletingRigth, boolean currentRules) {
        create=creatingRight;
        update=updatingRight;
        delete=deletingRigth;
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

    @Override
    public String toString() {
        return "SecurityRights{" +
                "create=" + create +
                ", update=" + update +
                ", delete=" + delete +
                ", is rurrent rule=" + current+
                '}';
    }
}
