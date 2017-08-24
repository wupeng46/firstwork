package com.lbs.apps.common;

import com.lbs.commons.StringHelper;

public class HtmlBean {
	public static String HTMLDecode(String s){
		if   (s==null)   return   s; 
        s=StringHelper.replace(s, "&amp;","&"); 
        s=StringHelper.replace(s, "&lt;","<"); 
        s=StringHelper.replace(s, "&gt;", ">"); 
        s=StringHelper.replace(s, "&quot;", "\\");
        s=StringHelper.replace(s, "&nbsp;", " "); 
        return   s; 		
	} 

}
