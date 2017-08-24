package util.com;

import java.io.*;

public class Log {

    public static final int ERROR=1;
    public static final int WARNING=2;
    public static final int INFORMATION=3;
    public static final int DEGUG=4;
    private static boolean ifDebug=true;
    private static Log instance;
    String file="log.txt";
    PrintWriter writer=null;
    public Log() {
        if(instance==null) instance=this;
    }
    public static Log getInstance()
    {
        if(instance==null) instance=new Log();
        return instance;
    }
    public void open()
    {


// Open the current log file
        try {
            //File dir = new File(System.getProperty("catalina.base"),"hzyl_log");
            File dir = new File("c:\\temp","hzyl_log");
            if(!dir.exists())
            dir.mkdirs();
            String pathname = dir.getAbsolutePath() + File.separator +file;
            writer = new PrintWriter(new FileWriter(pathname, true), true);
        } catch (IOException e) {
            writer = null;
            System.out.println("Log file open failed "+e.getMessage());
        }


    }
    public void close()
    {
        if (writer == null)
            return;
        writer.flush();
        writer.close();
        writer = null;
    }
    public void setVerbose(boolean bool)
    {
        Log.ifDebug=bool;
    }
    public void log(int info,String sInfo)
    {
        if(ifDebug){
            try{
                synchronized(this){
                    open();
                    TyCalendar ty=new TyCalendar();
                    switch(info){
                        case 1:writer.println(ty.getTime()+"---Error : "+sInfo);break;
                        case 2:writer.println(ty.getTime()+"---Warning : "+sInfo);break;
                        case 3:writer.println(ty.getTime()+"---Information : "+sInfo);break;
                        case 4:writer.println(ty.getTime()+"---Debug : "+sInfo);break;
                        default:writer.println(ty.getTime()+"---Unkown : "+sInfo);
                    }
                    close();
                }
            }catch(Exception e){
                System.out.println("log failed "+e.getMessage());
            }
        }
    }
    public static void main(String[] args)
    {
        Log l=Log.getInstance();
        l.setVerbose(true);
        System.out.println(2<<3);
        l.log(Log.ERROR,"²âÊÔ³É¹¦£¡");
    }
}

