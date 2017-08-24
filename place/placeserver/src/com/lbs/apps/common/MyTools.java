package com.lbs.apps.common;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.ResultSetDynaClass;
import org.apache.commons.beanutils.converters.SqlDateConverter;


/**
 * @author  WHD
 * @time  2010-12-7 下午01:52:41
 * @file  AjaxProtocol
 * @description ajax协议实现类以及util方法
 */
public class MyTools {
	
	
	
	/**
	 * 封装jdbc查询出来的结果集
	 * @param rs: jdbc的结果集
	 * @param className : javabean类的全称(包括类的路径) 
	 * @return 返回就是成为 面向对象的List(里面为javabean对象)
	 */
	public List sqlOO(ResultSet rs,String className){
		List result=new ArrayList();
		try {
			ResultSetDynaClass rsdc=new ResultSetDynaClass(rs,false);
			Iterator it=rsdc.iterator();
			while(it.hasNext()){
				DynaBean db=(DynaBean)it.next();
				Object one=Class.forName(className).newInstance();
				copy(one,db);
				result.add(one);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 读取ajax请求的数据
	 */
	public String readAjax(HttpServletRequest request){
		StringBuffer json=new StringBuffer();
		String line=null;
		try {
			BufferedReader reader=request.getReader();
			while((line=reader.readLine())!=null){json.append(line);}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//转码解决中文问题
		String str4json=json.toString();
		String re="";
		re=str4json;
		/*  
		try {
			if(str4json!=null){
				re=new String(str4json.getBytes("gbk"),"utf-8");
				//re=new String(str4json.getBytes("utf-8"),"gbk");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		return re;
	}
	
	
	/**
	 * 返回ajax数据
	 * 包装信息头状态
	 */
	public int returnAjaxObject(HttpServletResponse response,int code,JSONObject messageObject){

			//包装信息头
			AjaxProtocolBean one=new AjaxProtocolBean();
			one.setCode(code);
			one.setMessageObject(messageObject);
			
		return returnAjax(response,one,1);
	}
	public int returnAjaxArray(HttpServletResponse response,int code,JSONArray messageArray){

		//包装信息头
		AjaxProtocolBean one=new AjaxProtocolBean();
		one.setCode(code);
		one.setMessageArray(messageArray);
	return returnAjax(response,one,2);
}
	private int returnAjax(HttpServletResponse response,AjaxProtocolBean ajaxbean,int type){
		response.setContentType("text/html");

		
		int re=0;
		PrintWriter out;
		try {
			
		    response.setContentType("text/xml;charset=GBK");
		    
			//response.setContentType("text/html;charset=GBK");
//			response.setCharacterEncoding("ISO-8859-1");
//			response.setCharacterEncoding("GBK");
			out = response.getWriter();
			//包装信息头
			JSONObject json4ajaxbean=new JSONObject();
			
			json4ajaxbean.put("head", new Integer(ajaxbean.getCode()) );
			String returnStr="";
			if(type==1){//object
				JSONObject object4ajaxbean=ajaxbean.getMessageObject();
				
				json4ajaxbean.put("body", object4ajaxbean );
				
			}else if(type==2){//array
				JSONArray array4ajaxbean=ajaxbean.getMessageArray();
				json4ajaxbean.put("body", array4ajaxbean  );
			}else{}
			returnStr=json4ajaxbean.toString(1);
			
			String lastStr="";
			
			lastStr=returnStr;
			/*  
			try {
				if(returnStr!=null){
//					lastStr=new String(returnStr.getBytes("gbk"),"ISO-8859-1");
//					lastStr=new String(returnStr.getBytes("ISO-8859-1"),"utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			out.print(lastStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return re;
	}
	
	
	/**
	 * 返回ajax数据
	 * 包装信息头状态
	 * 解决java的字符串时会自动加上引号，导致jsonArray类型多了引号
	 */
	public int returnAjaxText(HttpServletResponse response,String message){
		response.setContentType("text/html");
		int re=0;
		PrintWriter out;
		try {
			
//		    response.setContentType("text/xml");			
//			response.setCharacterEncoding("ISO-8859-1");
			out = response.getWriter();
			String lastStr="";
			try {
				if(message!=null){
					lastStr=new String(message.getBytes("utf-8"),"ISO-8859-1");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			out.print(lastStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return re;
	}
	
	private class AjaxProtocolBean{
		
		private int code;
		private JSONObject messageObject;
		private JSONArray messageArray;
		
		public AjaxProtocolBean(){
			
		}
		
		public JSONObject toJSONObject(){
			return this.getMessageObject();
		}
		public JSONArray toJSONArray(){
			return this.getMessageArray();
		}
		
		public String toString() {
			StringBuffer sf=new StringBuffer();
			
			
			
//			sf.append("{\"code\":");
//			sf.append(Integer.toString(this.getCode()));
//			sf.append(",\"message\":\"");
//			sf.append(this.jo.toString());
//			sf.append("\"}");
			
//			sf.append("{'code':");
//			sf.append(Integer.toString(this.getCode()));
//			sf.append(",'message':'");
//			sf.append(this.getMessage());
//			sf.append("'}");
			
//			sf.append("{code:");
//			sf.append(Integer.toString(this.getCode()));
//			sf.append(",message:\"");
//			sf.append(this.getMessage());
//			sf.append("\"}");
			
			return sf.toString();
		}

		

		/**
		 * @return the code
		 */
		public int getCode() {
			return code;
		}

		/**
		 * @param code the code to set
		 */
		public void setCode(int code) {
			this.code = code;
		}

		/**
		 * @return the messageObject
		 */
		public JSONObject getMessageObject() {
			return messageObject;
		}

		/**
		 * @param messageObject the messageObject to set
		 */
		public void setMessageObject(JSONObject messageObject) {
			this.messageObject = messageObject;
		}

		/**
		 * @return the messageArray
		 */
		public JSONArray getMessageArray() {
			return messageArray;
		}

		/**
		 * @param messageArray the messageArray to set
		 */
		public void setMessageArray(JSONArray messageArray) {
			this.messageArray = messageArray;
		}

		
		
	}
	
	
/////////////////////////////////////////////////////////////////////////////////
	

	volatile private static MyTools one;
	private MyTools(){
		
		
	}
	public static MyTools getInstance(){
		if(one==null){
			synchronized(MyTools.class){
				if(one==null)one=new MyTools();
			}
		}
		return one;
	}
	////////////////////////////////////////////////////////////////////////////////tool method
    //////////////////////////////////////////////////////////////////////////////
	/**
	 * type 为  1  的时间格式为：    yyyy-MM-dd
	 * @param type
	 * @return
	 */
	public  String date(int type){
		String result="";
		SimpleDateFormat format=null;
		if(type==1)format=new SimpleDateFormat("yyyy-MM-dd");
		else if(type==2){format=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");    }
		else if(type==3){format=new SimpleDateFormat("yyyyMMddkkmmss");    }
		
		Date now=new Date();
		result=format.format(now);
	    return result;	
	}
	

	/**获取两个时间间隔*/
    public   long gap4Datetime(String sj1, String sj2) {
    	long hour=0;
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //long day = 0;        
        try {
         java.util.Date date1 = myFormatter.parse(sj1);
         java.util.Date date2 = myFormatter.parse(sj2);
         
         //day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
         hour= (date1.getTime() - date2.getTime()) / (1 * 60 * 1000);
         //这里精确到了秒，我们可以在做差的时候将时间精确到天
        } catch(Exception e){e.printStackTrace();}
        return hour;
    }
	
    /**
     * yyyy-MM-dd kk:mm:ss
     * @return
     */
    public final  String date(){
		Date date=new Date();
	    SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	    String result=formater.format(date);
	    return result;
	}
    
    /**
     * @author WHD
     * @datetime 2010-12-20 下午03:26:10
     * @method dateStr
     * @return String
     * @description 时间类型转字符串
     * @param ts
     * @return
     */
    public final  String dateStr(Timestamp ts){
	    SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	    String result="";
	    if(ts!=null)result=formater.format(ts);
	    return result;
    }
    
    
    public final  java.util.Date Str2date(String str,boolean isLong){
    	java.util.Date dt=null;
    	if(str==null || str.equals("")) return dt;
    	
    	SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
    	if(isLong)formater=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    	try {
			dt=formater.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	    return dt;
    }
	
    public final  Integer Str2int(String str){
    	Integer re=null;
    	if(str==null || str.equals("")) return re;
    	try{
    		re=new Integer(  Integer.parseInt(str) );    		
    	} catch (Exception e) {			
			e.printStackTrace();
		}
    	return re;
    }
    
    public final  Short Str2short(String str){
    	Short re=null;
    	if(str==null || str.equals("")) return re;
    	try{
    		re=new Short(  Short.parseShort(str) );    		
    	} catch (Exception e) {			
			e.printStackTrace();
		}
    	return re;
    }
    
    public final  Float Str2float(String str){
    	Float re=null;
    	if(str==null || str.equals("")) return re;
    	try{
    		re=new Float(  Float.parseFloat(str) );    		
    	} catch (Exception e) {			
			e.printStackTrace();
		}
    	return re;
    }
    
    
    
    
    /**
     * @author WHD
     * @datetime 2010-12-20 下午06:50:27
     * @method dateStr
     * @return String
     * @description 时间转字符串
     * @param dt
     * @return
     */
    public final  String dateStr(Date dt,boolean isLong){
    	SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
    	if( isLong )formater=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    	
    	String result="";
    	if(dt!=null)result=formater.format(dt);
	    return result;
    }

	
	
	
	
	
	
	/**
	 * compute percent data
	 * @param a
	 * @param b
	 * @return   such as :    30.0%
	 */
	public  String computePercent(int a,int b){
		String result=new String("");
		if(a==0||b==0)
		{
            result="0.0%";
		}
		else{
		DecimalFormat format=new DecimalFormat("##.0%");
		double compute=(double)a/b;
		result=format.format(compute);
		}
		return result;
	}
	
	/**
	 * 根据年龄反算  时间range
	 * @param age
	 * @param style
	 * @return array:
	 *   first is begin of range,second is end of range
	 */
	public  String[] deComputeAge(String age,String style){
		String[] result=new String[2];
		Calendar nowCal=Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		if(style==null||style.length()<1){style="local";}
		if("local".equals(style)){
			if("<1岁".equals(age)){
				result[1]=format.format(nowCal.getTime());
				nowCal.add(Calendar.YEAR,-1);
				result[0]=format.format(nowCal.getTime());
			}else if("1岁".equals(age)){
				nowCal.add(Calendar.YEAR,-1);
				result[1]=format.format(nowCal.getTime());
				nowCal.add(Calendar.YEAR,-1);
				result[0]=format.format(nowCal.getTime());
			}else if("2岁".equals(age)){
				nowCal.add(Calendar.YEAR,-2);
				result[1]=format.format(nowCal.getTime());
				nowCal.add(Calendar.YEAR,-1);
				result[0]=format.format(nowCal.getTime());
			}else if("3岁".equals(age)){
				nowCal.add(Calendar.YEAR,-3);
				result[1]=format.format(nowCal.getTime());
				nowCal.add(Calendar.YEAR,-1);
				result[0]=format.format(nowCal.getTime());
			}else if("4岁".equals(age)){
				nowCal.add(Calendar.YEAR,-4);
				result[1]=format.format(nowCal.getTime());
				nowCal.add(Calendar.YEAR,-1);
				result[0]=format.format(nowCal.getTime());
			}else if("5岁".equals(age)){
				nowCal.add(Calendar.YEAR,-5);
				result[1]=format.format(nowCal.getTime());
				nowCal.add(Calendar.YEAR,-1);
				result[0]=format.format(nowCal.getTime());
			}else if("6岁".equals(age)){
				nowCal.add(Calendar.YEAR,-6);
				result[1]=format.format(nowCal.getTime());
				nowCal.add(Calendar.YEAR,-1);
				result[0]=format.format(nowCal.getTime());
			}
			
			
		}else{
//			do nothing
		}

		return result;
	}
	
	
	/**
	 * 计算2个时间之间的月数(从begin到end的月数)
	 * @param begin
	 * @param end
	 * @return -1为无效的数字（表示结束时间在开始时间之前，失去意义）
	 */
	public  int countMonths(String begin,String end){
			int monthCount=0;
			Calendar beginCal=createCalendar(begin);
			Calendar endCal=createCalendar(end);
			
		if(endCal.after(beginCal)){
			
			boolean isDecrease=false;
			int beginDay4month=beginCal.get(Calendar.DAY_OF_MONTH);
			int endDay4month=endCal.get(Calendar.DAY_OF_MONTH);
			if(endDay4month<beginDay4month)isDecrease=true;
			
			while(endCal.after(beginCal)){
				endCal.add(Calendar.MONTH,-1);
				monthCount=monthCount+1;
			}
			if(isDecrease)monthCount=monthCount-1;
			}else{
				monthCount=-1;
			}
		return monthCount;
	}
	
	/**
	 * 计算2个时间之间的天数(从begin到end的天数)
	 * 实足天数
	 * @param begin
	 * @param end
	 * @return -1为无效的数字（表示结束时间在开始时间之前，失去意义）
	 */
	public  int countDays(String begin,String end){
		int result=0;
		Calendar beginCal=createCalendar(begin);
		Calendar endCal=createCalendar(end);
		if(endCal.after(beginCal)){
		while(endCal.after(beginCal)){
			endCal.add(Calendar.DATE,-1);
			result=result+1;
		}}else{
			result=-1;
		}
	return result;	
	}
	/**
	 * 计算2个时间之间的天数(从begin到end的天数)
	 * 无所谓开始时间和结束时间的先后
	 * @param begin
	 * @param end
	 * @return
	 */
	public  int bidiCountDays(String begin,String end){
		int result=0;
		Calendar beginCal=createCalendar(begin);
		Calendar endCal=createCalendar(end);
		
		while(endCal.after(beginCal)){
			endCal.add(Calendar.DATE,-1);
			result=result+1;
		}while(endCal.before(beginCal)){
			beginCal.add(Calendar.DATE,-1);
			result=result+1;
		}
	return result;	
	}
	private  Calendar createCalendar(String strDate){
		Calendar result=null;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(strDate != null){
			Date date=format.parse(strDate);
			result=new GregorianCalendar();
			result.setTime(date);	}		
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	/**
	 * gbk编码
	 * @param param
	 * @return
	 */
	public  String gbk(String param){
		String result=null;
		try {
			if(param!=null&&param.length()>0)
			result=new String(param.getBytes("iso8859-1"),"gbk");
		} catch (Exception e) {
             e.printStackTrace();
		}

		return result;
	}
	
	
	/**
     * 为web产生文件名
     * @return
     */
    public final  String generateWebFile(){
    	StringBuffer result=new StringBuffer();
    	Date now=new Date();
    	SimpleDateFormat format=new SimpleDateFormat("yyMMddkkmmssSSS");
    	String stamp=format.format(now);
    	result.append(stamp);
    	
    	Random random=new Random();
    	int num=random.nextInt();
    	if(num<0)num=-num;
    	result.append(Integer.toString(num));
    	
    	return result.toString();
    }
    public  Date parseToDate(String now,int type){
    	Date date=null;
    	SimpleDateFormat  dateformat=null;
    	try   
    	  {  if(type==1){
    		  dateformat=new SimpleDateFormat("yyyy-MM-dd");
    	     date=dateformat.parse(now); 
    	  }
    	    if(type==2){
    	    	dateformat=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    	    	 date=dateformat.parse(now); 
    	   }
    	    
    	    if(type==3){
    	    	dateformat=new SimpleDateFormat("dd/MM/yyyy");//Unparseable date: "11/10/2012"
    	    	 date=dateformat.parse(now); 
    	   }
    	    
    	    
    	   }   
         catch(Exception e){
        	 e.printStackTrace();
         }
    	return date;
    }
    
    /**
     * @author WHD
     * @datetime 2010-12-21 上午10:31:23
     * @method parseInt
     * @return int
     * @description 
     * @param s
     * @return
     */
    public int parseInt(String s){
    	int re=-1;
    	try{
    		if(!nullOrBlank(s))re=Integer.parseInt(s);
    	}catch(Exception e){
//    		e.printStackTrace();
    	}
    	return re;
    }
    
    
    /**
     * 防止后退
     */
    public  void preventBackforward(HttpServletResponse res){
    	res.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    	res.setHeader("Pragma","no-cache"); //HTTP 1.0
    	res.setDateHeader ("Expires", 0); //prevents caching at the proxy server        	
    	
    }

    
    public  boolean nullOrBlank(String param)
    {
        return param == null || param.trim().equals("");
    }

    public  String notNull(String param)
    {
        return param != null ? param.trim() : "";
    }
    
    public  long parseLong(String param, long defValue)
    {
        long l = defValue;
        try
        {
            l = Long.parseLong(param);
        }
        catch(Exception e) { }
        return l;
    }

    public  float parseFloat(String param, float defValue)
    {
        float f = defValue;
        try
        {
            f = Float.parseFloat(param);
        }
        catch(Exception e) { }
        return f;
    }

    public  double parseDouble(String param, double defValue)
    {
        double d = defValue;
        try
        {
            d = Double.parseDouble(param);
        }
        catch(Exception e) { }
        return d;
    }

    public  boolean parseBoolean(String param)
    {
        if(nullOrBlank(param))
            return false;
        switch(param.charAt(0))
        {
        case 49: // '1'
        case 84: // 'T'
        case 89: // 'Y'
        case 116: // 't'
        case 121: // 'y'
            return true;
        }
        return false;
    }
	

	////////////////////////////////////////////////////////////////////////////////tool method
    //////////////////////////////////////////////////////////////////////////////
	/**
	 * @author  WHD
	 * @time  2010-12-7 下午03:40:13
	 * @method  getObjectArray
	 * @return  Object[]
	 * @description 
	 */
	public Object[] getObjectArray(int length){
		Object o4arr = Array.newInstance(Object.class, length);
		Object[] params=(Object[])o4arr;
		return params;
	}
	
	
	
	
	/**
	 * 自动将  request范围内的数据组装到 JavaBean对象
	 * 注意：对于多选的字段，采用“逗号”隔开的方式连接成一个string字段
	 */
	public static void pop(Object bean,HttpServletRequest request){
		//solution 1:
		HashMap map = new HashMap();
		Field[] fields=bean.getClass().getDeclaredFields();
		List list=Arrays.asList(fields);
		Iterator it=list.iterator();
		while(it.hasNext()){
			Field f=(Field)it.next();
			String name=f.getName();
			String value=request.getParameter(name);
		    String[] array=request.getParameterValues(name);
		    
		    if(value!=null&&value.length()>0){//if has value and then 
		    	  if(array!=null&&array.length>1){//multiple values only string supported
		  	    	String values=concat(array);
		  	    	map.put(name,values);
		  	    	}else{//single value,but seperate every type
		  	    		String fulName= f.getType().getName();
		  	    		String typeName=fulName.substring(10);
		  	    		
		  	    		if("String".equals(typeName)){
		  	    			map.put(name,value);		  	    			
		  	    		}else if("Integer".equals(typeName)){
		  	    			Integer i=new Integer(value);
		  	    			map.put(name, i);
		  	    		}else if("Long".equals(typeName)){
		  	    			Long l=new Long(value);
		  	    			map.put(name,l);
		  	    		}else if("Short".equals(typeName)){
		  	    			Short s=new Short(value);
		  	    			map.put(name,s);
		  	    		}else if("Character".equals(typeName)){
		  	    			char c=value.charAt(0);
		  	    			Character ch=new Character(c);
		  	    			map.put(name,ch);
		  	    		}else if("Boolean".equals(typeName)){
		  	    			Boolean b=new Boolean(value);
		  	    			map.put(name,b);
		  	    		}else if("Byte".equals(typeName)){
		  	    			Byte b=new Byte(value);
		  	    			map.put(name,b);
		  	    		}else if("Double".equals(typeName)){
		  	    			Double d=new Double(value);
		  	    			map.put(name,d);
		  	    		}else if("Float".equals(typeName)){
		  	    			Float fl=new Float(value);
		  	    			map.put(name,fl);
		  	    		}else{
//		  	    			do nothing
		  	    		}
		  	    		
		  	    		
		  	    	}
		      }else if(value!=null&&value.length()==0){
		    	  String fulName= f.getType().getName();
	  	    		String typeName=fulName.substring(10);
		    	  if("String".equals(typeName)){map.put(name,value);}
		    	  
		      }else{
//		    	  when nothing ,ignore this field
		      }
			
		}
		
		
//		solution2: deprecated
//	    Enumeration names = request.getParameterNames();
//	    while (names.hasMoreElements())
//	    {
//	      String name = (String) names.nextElement();
//	      String value=request.getParameter(name);
//	      String[] array=request.getParameterValues(name);
//	      if(value!=null&&value.length()>0){
//	    	  if(array!=null&&array.length>1){
//	  	    	String values=concat(array);
//	  	    	map.put(name,values);
//	  	    	}else{
//	  	    		map.put(name, value);
//	  	    	}
//	      }
//	    }
		
		
		
	    try {
			BeanUtils.populate(bean, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}private static String concat(String[] array){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<array.length;i++){
			if(i==(array.length-1)){
				sb.append(array[i]);
			}else{
				sb.append(array[i]);
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	
	
	
	public final static String EMPTY_STRING = "";
	 
	   public static boolean isNull(Object obj) {
	       return obj == null;
	   }

	/**
	 *取 JavaBean内的数据（可避免　空指针错误） 
	 */
	public static String getProperty(Object bean, String property) {
	       if (bean == null) {
	            return EMPTY_STRING;
	       }
	     try {
	          String str = BeanUtils.getProperty(bean, property);
	          if (str == null) {
	              return EMPTY_STRING;
	         }
	        return str;
	     } catch (Exception e) {
	         return EMPTY_STRING;
	   }
	  }

	  /**
	 * @param bean
	 * @param props
	 */
	private static void populate(Object bean, Map props) {
	     if (bean == null) {
	        return;
	     }
	    try {
	     SqlDateConverter con = new SqlDateConverter(new Date(System.currentTimeMillis()));
	      ConvertUtils.register(con, java.sql.Date.class);
	      BeanUtils.populate(bean, props);
	   } catch (Exception e) {
	     e.printStackTrace();
	   }
	 }
	
	
	/**
	 * 自动将List列表里的多个对象组装到报表Bean
	 * 注意：报表bean里的属性名必须从0开始
	 * @param list
	 * @param bean
	 */
	public void listToBean(Object bean,List list){
		if(list!=null&&list.size()>0){
			Map reports=new HashMap();
			Field[] fields=null;
			
			for(int i=0;i<list.size();i++){
				Object one=list.get(i);
				if(i==0){
					fields=one.getClass().getDeclaredFields();
				}
				
				for(int j=0;j<fields.length;j++){
				   String field=fields[j].getName();
				   String value="";
				   try{
					   value=(String)PropertyUtils.getSimpleProperty(one,field);
					   reports.put(field+i,value);
					   PropertyUtils.copyProperties(bean,reports);
				   }catch(Exception e){
//					   e.printStackTrace();
				   }
				}				
			}
		}
	}
	

	/**
	 * 自动将报表Bean分解到List列表里的多个对象里
	 * 注意：报表bean里的属性名必须从0开始
	 * @param bean
	 * @param targets
	 */
	public void beanToList(Object bean,Object[] targets){
		int num=targets.length;
		Field[] fields=null;
		
		for(int i=0;i<num;i++){
		   	if(i==0){
		   		fields=targets[i].getClass().getDeclaredFields();
		   	}
		   	
		   	for(int j=0;j<fields.length;j++){
		   		String field=fields[j].getName();
		   		String value="";
		   		
		   		try {
					value=(String)PropertyUtils.getSimpleProperty(bean,field+Integer.toString(i));
					PropertyUtils.setSimpleProperty(targets[i],field,value);
				} catch (Exception e) {
//					e.printStackTrace();
				}
		   		
		   	}
		}
	}
	
	/**
	 * @author  WHD
	 * @time  2010-12-8 下午02:54:48
	 * @method  popMap
	 * @return  void
	 * @description 将map中的数据根据key弹出到bean中
	 */
	public void popMap(Object bean,Map map){
		try {
			BeanUtils.populate(bean, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 将orig对象的属性拷贝到dest对象
	 * @param dest
	 * @param orig
	 */
	public void copy(Object dest,Object orig){
		try {
			BeanUtils.copyProperties(dest,orig);
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();
		}
	}
	
	public void copyJson(Object dest,JSONObject json){
		//solution 1:
		Field[] fields=dest.getClass().getDeclaredFields();
		List list=Arrays.asList(fields);		
		Iterator it=list.iterator();
		while(it.hasNext()){
			Field f=(Field)it.next();
			String name=f.getName();
			//copy property now
			Object p;
			try {
				p = json.get(name);
				//need to make not null
				String className4p=p.getClass().getName();
				if( !JSONNull.class.getName().equalsIgnoreCase(className4p) ){
					PropertyUtils.setProperty(dest, name, p);
				}			
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		 }
		
	}
	/**
	 * 
	 * @author wangbo
	 * @date 2011-3-3
	 * @Description: 剔除不需要拷贝的字段
	 * @param dest
	 * @param json
	 */
	public void copyJsonBlackList(Object dest,JSONObject json,Map bl){
		//solution 1:
		Field[] fields=dest.getClass().getDeclaredFields();
		List list=Arrays.asList(fields);		
		Iterator it=list.iterator();
		while(it.hasNext()){
			Field f=(Field)it.next();
			String name=f.getName();
			//如果黑名单里包含了从javaBean反射过来的字段，则终止本次循环
			if(bl.containsKey(name)) continue;
			//copy property now
			Object p;
			try {
				p = json.get(name);
				//need to make not null
				String className4p=p.getClass().getName();
				if( !JSONNull.class.getName().equalsIgnoreCase(className4p) ){
					PropertyUtils.setProperty(dest, name, p);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		 }
		
	}
	
	/**
	 * @author WHD
	 * @datetime 2010-12-20 下午05:57:26
	 * @method copyIgnoreSensitive
	 * @return void
	 * @description 忽略大小写字段的拷贝
	 * @param dest
	 * @param orig
	 * @t
	 */
	//TODO
	public void copyBlackList(boolean isDest,Object dest,Object orig,Map bl){
		//solution 1:
		Field[] fields=null;
		if(isDest){
			fields=dest.getClass().getDeclaredFields();	
		}else{
			fields=orig.getClass().getDeclaredFields();
		}
		List list=Arrays.asList(fields);
		
		Iterator it=list.iterator();
		while(it.hasNext()){
			Field f=(Field)it.next();
			String name=f.getName();
			if(bl.containsKey(name)) continue;
			
			//copy property now
			Object p;
			try {
				p = PropertyUtils.getProperty(orig, name);
				PropertyUtils.setProperty(dest, name, p);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		 }
	}
	
	
	
	
	
	
	
	
	/**
	 * @author  WHD
	 * @time  2010-12-8 下午03:54:42
	 * @method  redirect
	 * @return  String
	 * @description 页面重定向解决分页bug
	 */
	public String redirect(HttpServletRequest req,HttpServletResponse res,String url){
		try {
			StringBuffer sf=new StringBuffer();
			sf.append( req.getContextPath() );
			sf.append(url);
			res.sendRedirect(sf.toString());
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}
	
	
	/**
	 * @author WHD
	 * @datetime 2010-12-19 下午04:18:14
	 * @method listPropertyNames
	 * @return void
	 * @description uses the Introspector class to get an array of PropertyDescriptor
		objects for a bean, and then walks through that array to display the name and type
		of the properties that the bean exposes, which can be a useful troubleshooting aid.
	 * @param c
	 * @throws IntrospectionException
	 */
	public void listPropertyNames(Class c) throws IntrospectionException {
		PropertyDescriptor[] pd;
		pd = Introspector.getBeanInfo(c).getPropertyDescriptors();
		for (int i = 0; i < pd.length; i++) {
			System.out.println(pd[i].getName() + " ("
					+ pd[i].getPropertyType().getName() + ")");
		}
	}
	
		
	/**
	 * @author WHD
	 * @datetime 2011-4-7 下午06:05:39
	 * @method go2page
	 * @return void
	 * @description 跳转页面
	 * @param url
	 * @param request
	 * @param response
	 */
	public void go2page(String url,HttpServletRequest request,HttpServletResponse response){
		try {
			//request.getRequestDispatcher("/study/do_result.jsp").forward(this.getRequest(), this.getResponse());
			request.getRequestDispatcher(url).forward(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * sql时间转string
	 * @param sDate
	 * @return
	 */
	public String sqlDate_to_String(java.sql.Date sDate) {
		  String re="";
		  if(sDate==null)return re;
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		  java.util.Date dSt = (java.util.Date) sDate;
		  re = sdf.format(dSt);
		  return re;
		 }
	
	/**
	 * 获取后缀名
	 * @param filename
	 * @param defExt
	 * @return
	 */
	public String getExtension(String filename, String defExt) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int i = filename.lastIndexOf('.'); 

            if ((i >-1) && (i < (filename.length() - 1))) { 
                return filename.substring(i + 1); 
            } 
        } 
        return defExt; 
    }
	
	/**
	 * 在路径下获取文件名
	 * @param filePath
	 * @return
	 */
	public String getFileNameInPath(String filePath){
		String temp[] = filePath.replaceAll("\\\\","/").split("/");
		String fileName = "";
		if(temp.length > 1){
		    fileName = temp[temp.length - 1];
		}
		return fileName;
	}
	
	
	public byte[] read(File file) throws IOException {

//	    if ( file.length() > MAX_FILE_SIZE ) {
//	        throw new FileTooBigException(file);
//	    }


	    byte []buffer = new byte[(int) file.length()];
	    InputStream ios = null;
	    try {
	        ios = new FileInputStream(file);
	        if ( ios.read(buffer) == -1 ) {
	            throw new IOException("EOF reached while trying to read the whole file");
	        }        
	    } finally { 
	        try {
	             if ( ios != null ) 
	                  ios.close();
	        } catch ( IOException e) {
	        }
	    }

	    return buffer;
	}
	
}

