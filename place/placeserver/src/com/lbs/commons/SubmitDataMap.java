package com.lbs.commons;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * desc: 
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @created 2004-12-2
 */
public class SubmitDataMap implements Serializable{
	private LinkedHashMap dataMap = new LinkedHashMap();

	public SubmitDataMap(String submitData){
		initStr(submitData);
	}
	public SubmitDataMap(){
		initStr(null);
	}
	public SubmitDataMap(HttpServletRequest req){
		initHttpRequest(req);
	}
	/**
	 * 初始化
	 * @param req
	 */
	private void initHttpRequest(HttpServletRequest req){
		Enumeration e = req.getParameterNames();
  		while(e.hasMoreElements()){
  			String paraName = (String)e.nextElement();
  			String[] paraValues = req.getParameterValues(paraName);
  			dataMap.put(paraName,paraValues);
  		}
	}
	/**
	 * 初始化
	 * @param submitData
	 */
	private void initStr(String submitData){
		if(null != submitData){
			String[] parameters = submitData.split("&");
			for(int i = 0; i < parameters.length; i++){
				String[] keyPair = parameters[i].split("=");
				//keyPair is name and keyPair[1] is value if value is not null
				List values = (List)dataMap.get(keyPair[0]);
				if(null == values){
					values = new ArrayList();
					if(keyPair.length > 1)
						values.add(keyPair[1]);
					else
						values.add(null);						
					dataMap.put(keyPair[0],values);
				}else{
					if(keyPair.length > 1)
						values.add(keyPair[1]);
					else
						values.add(null);	
				}
			}
			
		}
	}
	/**
	 * 获取提交的参数值列表
	 * @param name
	 * @return
	 */
	public String[] getParameterValues(String name){
		Object data = dataMap.get(name);
		if(null == data)
			return null;
		if(data instanceof String[])
		    return (String[])data;
		else{
		    List values = (List)data;
		    return (String[])values.toArray(new String[values.size()]);
		}		
	}
	/**
	 * 获取提交的参数值
	 * @param name
	 * @return
	 */
	public String getParameter(String name){
		List data = (List)dataMap.get(name);
		if(null == data)
			return null;
		return (String)data.get(0);
	}
}
