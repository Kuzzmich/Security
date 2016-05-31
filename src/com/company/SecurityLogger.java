package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Tokhchukov on 25.04.2016.
 */
public class SecurityLogger {
    private static SecurityLogger logger;
    private File out=new File("security_log.txt");
    private PrintWriter writer;

    {
        try {
        writer=new PrintWriter(new FileOutputStream(out));
        }catch (IOException e){e.printStackTrace();}
    }

    static {
        logger=new SecurityLogger();
    }

    private synchronized void println(String s){
        writer.println(s);
    }

    private String getDate(){
        LocalDateTime time=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String strTime=formatter.format(time);
        return strTime;
    }

    public void createRequestSuccess(SecureObjectRoot from, SecureObjectRoot to){
        if (from!=null) println(getDate()+" Create request provided. Caller: "
                +cutName(from.getClass().getName())+": "
                +from.fullName()+"; Target: "
                +cutName(to.getClass().getName())
                +": "+to.fullName()+";");
        else
            println(getDate()+" Create request provided. Caller: Root : RootObject; Target: "
                    +cutName(to.getClass().getName())
                    +": "+to.fullName()+";");
    }


    public void createRequestFailed(SecureObjectRoot from, SecureObjectRoot to){
        println(getDate()+" Create request failed. Caller: "+cutName(from.getClass().getName())+": "+from.fullName()+"; Target: "+cutName(to.getClass().getName())+": "+to.fullName()+";");

    }

    public void deleteRequestSuccess(SecureObjectRoot from, SecureObjectRoot to){
        println(getDate()+" Delete request provided. Caller: "+cutName(from.getClass().getName())+": "+from.fullName()+"; Target: "+cutName(to.getClass().getName())+": "+to.fullName()+";");
    }

    public void deleteRequestFailed(SecureObjectRoot from, SecureObjectRoot to){
        println(getDate()+" Delete request failed. Caller: "+cutName(from.getClass().getName())+": "+from.fullName()+"; Target: "+cutName(to.getClass().getName())+": "+to.fullName()+";");
    }

    public void execRequestSuccess(SecureObjectRoot from, SecureObjectRoot to){
        println(getDate()+" Method execute request provided. Caller: "+cutName(from.getClass().getName())+": "+from.fullName()+"; Target: "+cutName(to.getClass().getName())+": "+to.fullName()+";");
    }

    public void execRequestFailed(SecureObjectRoot from, SecureObjectRoot to){
        println(getDate()+" Method execute failed. Caller: "+cutName(from.getClass().getName())+": "+from.fullName()+"; Target: "+cutName(to.getClass().getName())+": "+to.fullName()+";");
    }

    public static SecurityLogger getInstance(){
        return logger;
    }

    public void close(){
        writer.close();
    }
    public static String cutName(String classname){
        int index=classname.lastIndexOf('.');
        if (index!=-1)return classname.substring(index+1);
        else return classname;

    }

    @Override
    public void finalize(){
        writer.close();
        System.out.println("Logger closed;");
    }
    private SecurityLogger(){};

}
