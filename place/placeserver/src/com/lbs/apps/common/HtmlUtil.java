package com.lbs.apps.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;

import org.apache.struts2.ServletActionContext;

public class HtmlUtil {
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出json格式<br>
	 * @param response
	 * @param jsonStr
	 * @throws Exception
	 */
	public static void writerJson(ServletResponse response,String jsonStr) {
			writer(response,jsonStr);
	}
	
	public static void writerJson(String jsonStr) {
		writer(ServletActionContext.getResponse(),jsonStr);
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出HTML代码<br>
	 * @param response
	 * @param htmlStr
	 * @throws Exception
	 */
	public static void writerHtml(ServletResponse response,String htmlStr) {
		writer(response,htmlStr);
	}
	
	public static void writerHtml(String htmlStr) {
		writer(ServletActionContext.getResponse(),htmlStr);
	}
	
	private static void writer(ServletResponse response,String str){
		try {
			response.setContentType(
					"text/html;charset=GBK");
			PrintWriter out= null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	//将类似["test"]的数据返回为test
	public static String convertPara(String s){
		String ls_return=s;
		if (ls_return.substring(0,2).equals("[\"")){
			ls_return=ls_return.substring(2);
		};
		if (ls_return.substring(ls_return.length()-2,ls_return.length()).equals("\"]")){
			ls_return=ls_return.substring(0,ls_return.length()-2);
		}
		return ls_return;		
	}
}
