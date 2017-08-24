package com.lbs.apps.common;

import java.io.UnsupportedEncodingException;

public class CodeHelper {
	public static String toUTF8(String isoString) {
      String utf8String = null;
      if (null != isoString && !isoString.equals("")) {
          try {
              byte[] stringBytesISO = isoString.getBytes("ISO-8859-1");
              utf8String = new String(stringBytesISO, "UTF-8");
          } catch (UnsupportedEncodingException e) {
              // As we can't translate just send back the best guess.
              System.out.println("UnsupportedEncodingException is: "
                     + e.getMessage());
             utf8String = isoString;
         }
     } else {
         utf8String = isoString;
     }
     return utf8String;
	}
	
	//接受EXT4查询时前台的中文参数转码为中文显示
	public static String chString(String s){
		String st=s;
	    try{
	         byte a[]=st.getBytes("ISO-8859-1");
	         st=new String(a,"utf-8");
	         return st;
	    }catch(Exception e){
	    return st;
	    }
	 }
}
