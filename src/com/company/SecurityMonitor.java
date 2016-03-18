package com.company;

import com.sun.istack.internal.NotNull;
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

    //-------------Serialization and deserialization----------------

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
        try(FileInputStream instream=new FileInputStream(currRules)){
            ObjectInputStream currinput=new ObjectInputStream(instream);
            currentRules=(HashMap<SecureObjectPair,SecurityRights>) currinput.readObject();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void saveDefaultRules(File defaultRules) {
        try (FileOutputStream outstream=new FileOutputStream(defaultRules)){
            ObjectOutputStream baseoutput=new ObjectOutputStream(outstream);
            baseoutput.writeObject(baseRules);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCurrentRules(File currRules) throws IOException{
        try (FileOutputStream outstream=new FileOutputStream(currRules)){
            ObjectOutputStream curroutput=new ObjectOutputStream(outstream);
            curroutput.writeObject(currentRules);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    //------------------Requests------------------

    //запрос на создание объекта
    public void createRequest(@NotNull SecureObjectRoot from, @NotNull SecureObjectRoot target)
            throws RestrictedByCurrentRulesException, RestrictedByDefaultRulesException{
        try {
            SecurityRights rights=getRights(from,target);
            if (rights.isCreate()){ //проверка на разрешенность создания
                container.addObject(target);
                target.create();
            }
            else {
                if (rights.isCurrent())throw new RestrictedByCurrentRulesException();
                else throw new RestrictedByDefaultRulesException();
            }
        } catch (SCClassNotDescribedException e) {
            e.printStackTrace();
        }
    }
     // запрос на удаление объекта
     public void deleteRequest(@NotNull SecureObjectRoot from, @NotNull SecureObjectRoot target)
             throws RestrictedByCurrentRulesException, RestrictedByDefaultRulesException{
         try {
             SecurityRights rights=getRights(from,target);
             if (rights.isDelete()){ //проверка на разрешенность удаления
                 target.delete();
                 container.removeObject(target);
             }
             else {
                 if (rights.isCurrent())throw new RestrictedByCurrentRulesException();
                 else throw new RestrictedByDefaultRulesException();
             }
         } catch (SCClassNotDescribedException e) {
             e.printStackTrace();
         }
     }

    //запрос на запуск метода
    public void methodExecRequest(@NotNull SecureObjectRoot from, @NotNull SecureObjectRoot target)
            throws RestrictedByCurrentRulesException, RestrictedByDefaultRulesException{
        try {
            SecurityRights rights=getRights(from,target);
            if (!rights.isUpdate()){ //проверка на разрешенность удаления
                if (rights.isCurrent())throw new RestrictedByCurrentRulesException();
                else throw new RestrictedByDefaultRulesException();
            }
        } catch (SCClassNotDescribedException e) {
            e.printStackTrace();
        }
    }




    //добавление и удаление текущих правил для объктов
    public OperationStatus addCurrentRule(SecureObjectPair pair,SecurityRights rights) throws SCClassNotDescribedException{
        SecurityRights baseRights=getDefaultRights(pair.getFirst(),pair.getSecond());
        if (SecurityRights.isValid(baseRights,rights)){
            currentRules.put(pair,rights);
            return OperationStatus.ST_SUCCESSFUL;
        }
        else return OperationStatus.ST_FAILURE;
    }

    public OperationStatus removeCurrentRule(SecureObjectPair pair){
        if (currentRules.containsKey(pair)){
            currentRules.remove(pair);
            return OperationStatus.ST_SUCCESSFUL;
        }
        else return OperationStatus.ST_FAILURE;
    }

    //Получить общие права доступа
   public SecurityRights getRights(SecureObjectRoot from, SecureObjectRoot to) throws SCClassNotDescribedException {
       Class<?> fromClass=from.getClass();  //получение классов для создания временных объектов, предназначенных
       Class<?> toClass=to.getClass();      //для получение информации из базовой таблицы
       try {
           SecureObjectRoot fromObject=(SecureObjectRoot)fromClass.newInstance();
           SecureObjectRoot toObject=(SecureObjectRoot)toClass.newInstance();

           SecureObjectPair defaultPair=new SecureObjectPair(fromObject,toObject); //создан из временных объектов для получения базовых прав
           SecureObjectPair currentPair=new SecureObjectPair(from,to);//для получения текущих прав

          if (currentRules.containsKey(currentPair))return currentRules.get(currentPair);
          else{
              if (baseRules.containsKey(defaultPair))return baseRules.get(defaultPair);
              else throw new SCClassNotDescribedException();
          }

       } catch (InstantiationException e) {
           e.printStackTrace();
           return null;
       } catch (IllegalAccessException e) {
           e.printStackTrace();
           return null;
       }
   }

//    Получить базовые права доступа

    public SecurityRights getDefaultRights(ISecureObj from, ISecureObj to) throws SCClassNotDescribedException {
        Class<?> fromClass=from.getClass();  //получение классов для создания временных объектов, предназначенных
        Class<?> toClass=to.getClass();      //для получение информации из базовой таблицы
        try {
            SecureObjectRoot fromObject=(SecureObjectRoot)fromClass.newInstance();
            SecureObjectRoot toObject=(SecureObjectRoot)toClass.newInstance();
            SecureObjectPair defaultPair=new SecureObjectPair(fromObject,toObject); //создан из временных объектов для получения базовых прав

            if (baseRules.containsKey(defaultPair))return baseRules.get(defaultPair);
            else throw new SCClassNotDescribedException();

        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


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
