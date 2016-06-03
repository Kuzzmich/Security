package com.company;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.io.*;
import java.util.HashMap;
import java.util.List;

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
    private SecurityLogger logger=SecurityLogger.getInstance();

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

    public void loadCurrentRules(File currrules)throws SerializationException,ClassNotFoundException{
        try(FileInputStream instream=new FileInputStream(currrules)){
            ObjectInputStream currinput=new ObjectInputStream(instream);
            currentRules=(HashMap<SecureObjectPair,SecurityRights>) currinput.readObject();
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

    public void saveCurrentRules(File currrules) throws IOException{
        try (FileOutputStream outstream=new FileOutputStream(currrules)){
            ObjectOutputStream curroutput=new ObjectOutputStream(outstream);
            curroutput.writeObject(currentRules);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    //------------------Requests------------------
    private void addObjectToContainer(SecureObjectRoot target) throws SCClassNotDescribedException {
        container.addObject(target);
        List<SecureObjectRoot> lst=container.getObjects();
        for (SecureObjectRoot secureObject: lst){
            if (!(secureObject.getClass().getName().equals(target.getClass().getName()))) {
                SecurityRights base = getDefaultRights(target, secureObject);
                if (base.isSetbydefault()) {

                    SecureObjectPair pair = new SecureObjectPair(target, secureObject);
                    addCurrentRule(pair, base);

                }
            }
            if (!(secureObject.getClass().getName().equals(target.getClass().getName()))) {
                SecurityRights base = getDefaultRights(secureObject, target );
                if (base.isSetbydefault()) {
                    SecureObjectPair pair = new SecureObjectPair(secureObject, target);
                    addCurrentRule(pair, base);
                }
            }
        }
    }

    private void removeObjectFromContainer(SecureObjectRoot target) throws SCClassNotDescribedException {
        container.removeObject(target);
        List<SecureObjectRoot> lst=container.getObjects();
        for (SecureObjectRoot secureObject: lst){
                    SecureObjectPair pairFrom = new SecureObjectPair(target, secureObject);
                    SecureObjectPair pairTo = new SecureObjectPair(secureObject,target);
                    removeCurrentRule(pairFrom);
                    removeCurrentRule(pairTo);
            }

    }


    public void addObjectToContainerRoot(SecureObjectRoot target){
        SecurityLogger logger=SecurityLogger.getInstance();
        logger.createRequestSuccess(null,target);
        container.addObject(target);

    }


    //запрос на создание объекта

    public void createRequest(@NotNull SecureObjectRoot from, @NotNull SecureObjectRoot target)
            throws RestrictedByCurrentRulesException, RestrictedByDefaultRulesException{
        try {
            if (!container.getObjects().contains(from)){
                logger.createRequestFailed(from,target);
                container.removeObject(target);
                throw new RestrictedByCurrentRulesException();

            }
            addObjectToContainer(target);
            SecurityRights rights=getRights(from,target);
            if (rights.isCreate()){ //проверка на разрешенность создания
                target.create();
                logger.createRequestSuccess(from,target);
            }
            else {
                logger.createRequestFailed(from,target);
                container.removeObject(target);
                if (rights.isSetbydefault())throw new RestrictedByCurrentRulesException();
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
             if (!container.getObjects().contains(from)||!container.getObjects().contains(target)){
                 logger.deleteRequestFailed(from, target);
                 throw new RestrictedByCurrentRulesException();

             }
             SecurityRights rights=getRights(from,target);
             if (rights.isDelete()){ //проверка на разрешенность удаления
                 target.delete();
                 removeObjectFromContainer(target);
                 logger.deleteRequestSuccess(from,target);
             }
             else {
                 logger.deleteRequestFailed(from,target);
                 if (rights.isSetbydefault())throw new RestrictedByCurrentRulesException();
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
            if (!container.getObjects().contains(from)||!container.getObjects().contains(target)){
                logger.execRequestFailed(from,target);
                throw new RestrictedByCurrentRulesException();

            }
            SecurityRights rights=getRights(from,target);
            if (!rights.isUpdate()){ //проверка на разрешенность удаления
                logger.execRequestFailed(from, target);
                if (rights.isSetbydefault())throw new RestrictedByCurrentRulesException();
                else throw new RestrictedByDefaultRulesException();
            }
            logger.execRequestSuccess(from,target);
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

    //Получить права доступа
   public SecurityRights getRights(SecureObjectRoot from, SecureObjectRoot to) throws SCClassNotDescribedException {

       SecureObjectPair currentPair = new SecureObjectPair(from, to);//для получения текущих прав

       if (currentRules.containsKey(currentPair)) return currentRules.get(currentPair);
       else {
                return new SecurityRights(false,false,false,false,false) ;
       }

   }


    @Deprecated
    public SecurityRights getTotalRights(SecureObjectRoot from, SecureObjectRoot to) throws SCClassNotDescribedException {
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
            //else throw new SCClassNotDescribedException();
            else return new SecurityRights(false,false,false,false,true);

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
