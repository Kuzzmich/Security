package com.company;

import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.io.*;
import java.util.HashMap;

/**
 * Created by tokhchukov on 09.03.2016.
 */
public class SecurityMonitor {
    private HashMap<SecureObjectPair,SecurityRights> baseRules;
    private HashMap<SecureObjectPair,SecurityRights> currentRules;
    {
        baseRules=new HashMap<SecureObjectPair,SecurityRights>();
        currentRules=new HashMap<SecureObjectPair,SecurityRights>();
    }
    private SecureObjectContainer container;

    public SecurityMonitor(){}
    public SecurityMonitor(SecureObjectContainer cont) {
        attachContainer(cont);
    }

    public void attachContainer(SecureObjectContainer cont) {
        container=cont;
    }

    public void loadDefaultRules(File defaultrules)throws SerializationException,ClassNotFoundException{
        try(FileInputStream stream=new FileInputStream(defaultrules)){
            ObjectInputStream baseinput=new ObjectInputStream(stream);
            baseRules=(HashMap<SecureObjectPair,SecurityRights>) baseinput.readObject();
        }
        catch (IOException e){
           e.printStackTrace();
        }
    }


    public void loadCurrentRules(File currRules)throws SerializationException,ClassNotFoundException{
        try(FileInputStream outstream=new FileInputStream(currRules)){
            ObjectInputStream currinput=new ObjectInputStream(outstream);
            baseRules=(HashMap<SecureObjectPair,SecurityRights>) currinput.readObject();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void saveDefaultRules(File defaultrules) {
        try (FileOutputStream outstream=new FileOutputStream(defaultrules)){
            ObjectOutputStream baseoutput=new ObjectOutputStream(outstream);
            baseoutput.writeObject(baseRules);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCurrentRules(File currRules) throws IOException{
        try (FileOutputStream outstream=new FileOutputStream(currRules)){
            ObjectOutputStream curroutput=new ObjectOutputStream(outstream);
            curroutput.writeObject(baseRules);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OperationStatus createRequest(SecureObjectRoot from, SecureObjectRoot target){
        try {
            if (from.getClass()==Journal.class){

                Class.forName(Journal.class.getName());
            }
            else
            {
                System.out.println("nah " +from.getClass().getName()+" "+target.getClass().getName());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return OperationStatus.ST_RESTRICTED_BY_CURRENT_RULES;
    }
    public OperationStatus addCurrentRule(SecureObjectPair pair, SecurityRights rights){
        return OperationStatus.ST_SUCCSSFULL;
    }
   /* public SecurityRights getRights(ISecureObj from, ISecureObj to) throws SCClassNotDescibedException{
            SecureObjectRoot r=new


    }*/
    //--------getters generated----------


    public HashMap<SecureObjectPair, SecurityRights> getBaseRules() {
        return baseRules;
    }

    public HashMap<SecureObjectPair, SecurityRights> getCurrentRules() {
        return currentRules;
    }


    //--------setters generated----------

    public void setBaseRules(HashMap<SecureObjectPair, SecurityRights> baseRules) {
        this.baseRules = baseRules;
    }

    public void setCurrentRules(HashMap<SecureObjectPair, SecurityRights> currentRules) {
        this.currentRules = currentRules;
    }
}
