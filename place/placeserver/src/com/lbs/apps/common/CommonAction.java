package com.lbs.apps.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class CommonAction extends ActionSupport {	
	private String _dc;
	
	public String get_dc() {
		return _dc;
	}
	public void set_dc(String _dc) {
		this._dc = _dc;
	}	
	
	
	/*
	 * ����json��ʽajax����
	 * ������javabean��
	 */
	public JSONObject popJSON2Bean(HttpServletRequest request,Object dto,Map bl) {
		String rocAjaxContent = MyTools.getInstance().readAjax(request);
		JSONObject jsonObject = JSONObject.fromObject(rocAjaxContent);
		MyTools.getInstance().copyJsonBlackList(dto, jsonObject, bl);
		
		return jsonObject;
	}
	
	/*
	 * ����json��ʽajax����
	 * ������javabean��
	 */
	public JSONObject parseJSON(HttpServletRequest request) {
		String rocAjaxContent = MyTools.getInstance().readAjax(request);
		JSONObject jsonObject = JSONObject.fromObject(rocAjaxContent);		
		return jsonObject;
	}
	
	/*
	 * ����json�������
	 */
	public void returnJSONToBrowser(HttpServletResponse response,JSONObject body,int head){
		//JSONObject returnInfo = new JSONObject();
		//returnInfo.put("success", 2);
		MyTools.getInstance().returnAjaxObject(response, head,body);		
	}
	
	/*
	 * ����json�������
	 */
	public void returnJSONArrayToBrowser(HttpServletResponse response,JSONArray body,int head){
		//JSONObject returnInfo = new JSONObject();
		//returnInfo.put("success", 2);
		MyTools.getInstance().returnAjaxArray(response, head,body);		
	}
	
	/*
	 * ����json�������
	 */
	public void returnExtJSONToBrowser(HttpServletResponse response,Object o){
		response.setContentType("text/html");
		int re=0;
		PrintWriter out;
		try {
			
			//modify by whd 20130913 ������ڷ��ظ�ʽ������
			JsonConfig jsonConfig = new JsonConfig();   //JsonConfig��net.sf.json.JsonConfig�е������Ϊ�̶�д��  
			jsonConfig.registerJsonValueProcessor(Date.class , new DateJsonValueProcessor()); 

			
			String paramClass= o.getClass().getName();
			boolean isArray=false;
			if( paramClass.indexOf( "java.util.ArrayList" )>=0 ){
				isArray=true;
			}
			String lastStr=null;			
			if(isArray){
				//JSONArray arr=JSONArray.fromObject(o);
				JSONArray arr=JSONArray.fromObject(o,jsonConfig);  //modify by whd 20130913 ������ڷ��ظ�ʽ������
				lastStr=arr.toString();
			}else{
				//JSONObject obj=JSONObject.fromObject(o);
				JSONObject obj=JSONObject.fromObject(o,jsonConfig);  //modify by whd 20130913 ������ڷ��ظ�ʽ������
				lastStr=obj.toString();
			}			
			//System.out.println( "lastStr:"+ lastStr );
			
		    //response.setContentType("text/xml");
		    response.setContentType("text/html; charset=GBK");
			//response.setCharacterEncoding("ISO-8859-1");
			out = response.getWriter();
			
			/*
			try {
				if(returnStr!=null){
					lastStr=new String(returnStr.getBytes("utf-8"),"ISO-8859-1");
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
	}
	
	
	/*
	 * ����json�������,����jsonp�ص�
	 */
	public void returnExtJSONToBrowser(HttpServletResponse response,Object o,String callback){
		response.setContentType("text/html");
		int re=0;
		PrintWriter out;
		try {
			
			//modify by whd 20130913 ������ڷ��ظ�ʽ������
			JsonConfig jsonConfig = new JsonConfig();   //JsonConfig��net.sf.json.JsonConfig�е������Ϊ�̶�д��  
			jsonConfig.registerJsonValueProcessor(Date.class , new DateJsonValueProcessor()); 

			
			String paramClass= o.getClass().getName();
			boolean isArray=false;
			if( paramClass.indexOf( "java.util.ArrayList" )>=0 ){
				isArray=true;
			}
			String lastStr=null;			
			if(isArray){
				//JSONArray arr=JSONArray.fromObject(o);
				JSONArray arr=JSONArray.fromObject(o,jsonConfig);  //modify by whd 20130913 ������ڷ��ظ�ʽ������
				lastStr=arr.toString();
			}else{
				//JSONObject obj=JSONObject.fromObject(o);
				JSONObject obj=JSONObject.fromObject(o,jsonConfig);  //modify by whd 20130913 ������ڷ��ظ�ʽ������
				lastStr=obj.toString();
			}			
			//System.out.println( "lastStr:"+ lastStr );
			
		    //response.setContentType("text/xml");
		    response.setContentType("text/html; charset=GBK");
			//response.setCharacterEncoding("ISO-8859-1");
			out = response.getWriter();
			
			/*
			try {
				if(returnStr!=null){
					lastStr=new String(returnStr.getBytes("utf-8"),"ISO-8859-1");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			
			if (!(callback==null || callback.equals(""))){
				lastStr=callback+"("+lastStr+")";
			}			
			out.print(lastStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ����json�������,����jsonp�ص�
	 * 
	 */
	public void PrintValueToJsonRoot(HttpServletResponse response,String fieldname,String fieldvalue,String callback){
		response.setContentType("text/html");
		PrintWriter out;
		int li_count=1;
		fieldvalue=fieldvalue.replace("\"", "'");
		StringBuffer sb=new StringBuffer("{\"success\":\"true\",\"msg\":\"�ɹ�\",\"total\":"+li_count+",\"root\":[");
		sb.append("{\"").append(fieldname).append("\":\"").append(fieldvalue).append("\"}");
		sb.append("]}");
		String lastStr=sb.toString();
		try {
			response.setContentType("text/html; charset=GBK");
			out = response.getWriter();
			if (!(callback==null || callback.equals(""))){
				lastStr=callback+"("+lastStr+")";
			}						
			out.print(lastStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * ����json�������
	 */
	public void returnExtGridJSONToBrowser(HttpServletResponse response,PageBean page){
		response.setContentType("text/html");
		int re=0;
		PrintWriter out;
		try {
			
			String lastStr=null;
			JSONObject obj=JSONObject.fromObject(page);
			lastStr=obj.toString();
			//System.out.println( "lastStr:"+ lastStr );
			
//		    response.setContentType("text/xml");
			response.setContentType("text/html; charset=GBK");
//			response.setCharacterEncoding("ISO-8859-1");
			out = response.getWriter();
			
			/*
			try {
				if(returnStr!=null){
					lastStr=new String(returnStr.getBytes("utf-8"),"ISO-8859-1");
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
	}
	
		
	//������
	public void WriteResult(String ls_result) throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(ls_result);		
	}
	
	
}
