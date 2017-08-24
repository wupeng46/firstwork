package util.com;

/*
*日期函数
*/
import java.text.DateFormat;
import java.util.*;
import java.text.*;
import java.sql.Date;

public class TyCalendar {

    Calendar calendar = null;
    //构造函数
    public TyCalendar() {
        calendar = Calendar.getInstance();
        java.util.Date trialTime = new java.util.Date();
        calendar.setTime(trialTime);
    }
    //换回年
    public int getYear()
    {
        return calendar.get(Calendar.YEAR);
    }
    //返回月
    public String getMonth(){
        int m=calendar.get(Calendar.MONTH)+1;
        String[] months=new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
        if(m>12)
            return "Unkown Month";
        return months[m-1];
    }
    public String getDayOfWeek()
    {
        int x=calendar.get(Calendar.DAY_OF_WEEK);
        String[] weekday=new String[]{"1","2","3","4","5","6","7"};
        if(x>7)
            return "unkown week day";
        return weekday[x-1];
    }
    public String getDayOfMonth()
    {
        String[] monday=new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};

        return monday[calendar.get(Calendar.DAY_OF_MONTH)-1];
    }
    public String getDate()
    {
        return this.getYear()+"-"+this.getMonth()+"-"+this.getDayOfMonth();
    }
    public int getHour()
    {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    public int getMinute()
    {
        return calendar.get(Calendar.MINUTE);

    }
    public int getSecond()
    {
        return calendar.get(Calendar.SECOND);
    }
    public String getTime()
    {
        String aa=this.getDate()+" "+this.getHour()+":"+this.getMinute()+":"+this.getSecond();
        return aa;
    }
    public static void main(String[] args)
    {
        TyCalendar ty=new TyCalendar();
        System.out.println(ty.getDate());
        System.out.println(ty.getYear());
        try{
        DateFormat df=DateFormat.getDateInstance();
        java.util.Date dd=df.parse("2003-3-32");
        java.sql.Date da=new java.sql.Date(dd.getTime());
        System.out.println(dd.getTime()+da.toString());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
