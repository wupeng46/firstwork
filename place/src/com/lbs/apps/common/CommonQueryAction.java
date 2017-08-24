package com.lbs.apps.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.lbs.commons.GlobalNames;
import com.lbs.commons.log.LogHelper;

public class CommonQueryAction extends CommonAction {
	
	public int start;  //起始记录数
	public int limit;  //每页总记录数
	public int total;  //总记录数
	public int page;   //页数
	public String callback;  //jsonp回调时使用
	public String os;           //操作系统 
	public String version;      //版本号
	public String devicetype;   //设备类型
	public String success;      //是否成功标记 
	public String msg;          //返回消息
	public String msgdata;      //请求消息体 
	public String msgdatasign;  //数据签名（请求消息体）    
	public String convertcode;  //查询时是否转换代码为中文实际含义  
	
	LogHelper log = new LogHelper(this.getClass());
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}	
	
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}	
	
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}	
	
	public String getMsgdata() {		
		return msgdata;
	}
	public void setMsgdata(String msgdata) {
		this.msgdata = msgdata;
	}	
	
	public String getMsgdatasign() {
		return msgdatasign;
	}
	public void setMsgdatasign(String msgdatasign) {
		this.msgdatasign = msgdatasign;
	}
		
	
	public String getConvertcode() {
		return convertcode;
	}
	public void setConvertcode(String convertcode) {
		this.convertcode = convertcode;
	}
	public String  QueryDataBySql(String sql) throws ApplicationException{
		return QueryDataBySql(sql,"true");
	}
	
	
	
	public String QueryException(String errormsg)throws ApplicationException{
		String s="{\"success\":\"false\",\"msg\":\""+errormsg+"\",\"total\":0,\"root\":[]}";
		if (!(getCallback()==null || getCallback().equals(""))){
	 		s=getCallback()+"("+s+")";
	 	}
	 	s=s.replaceAll("\r", "");  //替换回车换行，解决EXTJS不显示问题
	 	s=s.replaceAll("\n", "<br>");  //替换回车换行，解决EXTJS不显示问题
		ServletActionContext.getResponse().setContentType("text/html;charset=GBK");
		try {
			ServletActionContext.getResponse().getWriter().write(s);	
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	 	return this.SUCCESS;		
	}
	
	public String QueryDataBySqlForAll(String sql) throws ApplicationException{
		return QueryDataBySqlForAll(sql,"true");
	}
	//根据SQL查询分页数据	
	public String QueryDataBySqlForAll(String sql,String convertcode) throws ApplicationException{		
		
		ServletActionContext.getResponse().setContentType("text/html;charset=GBK");
		
		JsonQueryBPO bpo=new JsonQueryBPO();
	 	String s=bpo.QueryDataBySqlForAll(sql,convertcode);		 	
	 	
	 	if (!(getCallback()==null || getCallback().equals(""))){
	 		s=getCallback()+"("+s+")";
	 	}
	 	s=s.replaceAll("\r", "");  //替换回车换行，解决EXTJS不显示问题
	 	s=s.replaceAll("\n", "<br>");  //替换回车换行，解决EXTJS不显示问题
	 	
	 	//ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");	 	
		try {
			ServletActionContext.getResponse().getWriter().write(s);	
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	 	return this.SUCCESS;		
	}
		
			
	
	//根据SQL查询分页数据	
	public String QueryDataBySql(String sql,String convertcode) throws ApplicationException{		
		
		ServletActionContext.getResponse().setContentType("text/html;charset=GBK");
		String startRow = new Integer(start).toString();     //获取开始行 
		if (startRow==null){
			startRow="0";
		}
	 	
		if (limit==0){                  //默认为每页10行
	 		limit=10;
	 	}
		int pageSize = limit;              //页记录数
	 	
	 	//int index = Integer.parseInt(startRow)/pageSize;     //开始页
		int index = Integer.parseInt(startRow)/pageSize+1;     //开始页   modify by whd 2014.11.08 针对SQLSERVER进行调整
	 	
	 	JsonQueryBPO bpo=new JsonQueryBPO();
	 	String s="";	 	
	 	/*
	 	String ls_FieldList=bpo.GetFieldList(sql);
	 	if (!(fieldList==null || fieldList.equals(""))){
	 		s=bpo.QueryDataBySql(sql,fieldList,index,pageSize);			   
	 	}else{
	 		s=bpo.QueryDataBySql(sql,ls_FieldList,index,pageSize);
	 	}	 	
	 	*/
	 	
	 	s=bpo.QueryDataBySql(sql,convertcode,index,pageSize);
	 	
	 	if (!(getCallback()==null || getCallback().equals(""))){
	 		s=getCallback()+"("+s+")";
	 	}
	 	s=s.replaceAll("\r", "");  //替换回车换行，解决EXTJS不显示问题
	 	s=s.replaceAll("\n", "<br>");  //替换回车换行，解决EXTJS不显示问题
	 	
	 	//ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");	 	
		try {
			ServletActionContext.getResponse().getWriter().write(s);	
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	 	return this.SUCCESS;		
	}
	
	//根据SQL查询分页数据	
	public String QueryDataBySql(String sql,String fieldList,String callback) throws ApplicationException{		
		
		ServletActionContext.getResponse().setContentType("text/html;charset=GBK");
		String startRow = new Integer(start).toString();     //获取开始行 
		if (startRow==null){
			startRow="0";
		}
	 	
		if (limit==0){                  //默认为每页10行
	 		limit=10;
	 	}
		int pageSize = limit;              //页记录数
	 	
	 	int index = Integer.parseInt(startRow)/pageSize;     //开始页	 	
	 	
	 	JsonQueryBPO bpo=new JsonQueryBPO();
	 	String s="";	 	
	 	String ls_FieldList=bpo.GetFieldList(sql);
	 	
	 	if (!(fieldList==null || fieldList.equals(""))){
	 		s=bpo.QueryDataBySql(sql,fieldList,index,pageSize,callback);			   
	 	}else{
	 		s=bpo.QueryDataBySql(sql,ls_FieldList,index,pageSize,callback);
	 	}	 	
	 	
	 	if (!(getCallback()==null || getCallback().equals(""))){
	 		s=getCallback()+"("+s+")";
	 	}
	 	s=s.replaceAll("\r", "");  //替换回车换行，解决EXTJS不显示问题
	 	s=s.replaceAll("\n", "<br>");  //替换回车换行，解决EXTJS不显示问题
	 	
	 	//ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");	 	
		try {
			ServletActionContext.getResponse().getWriter().write(s);	
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	 	return this.SUCCESS;		
	}
	
	
	//根据HQL查询分页数据	
	public String QueryData(String sql) throws ApplicationException{
		return QueryData(sql,"");
	}
	
	
	//根据HQL查询分页数据,带字段列表|分割
	public String QueryData(String sql,String fieldList) throws ApplicationException{		
		
		ServletActionContext.getResponse().setContentType("text/html;charset=GBK");
		String startRow = new Integer(start).toString();     //获取开始行 
		if (startRow==null){
			startRow="0";
		}	 	
		if (limit==0){                  //默认为每页10行
	 		limit=10;
	 	}
		int pageSize = limit;              //页记录数
	 	
	 	int index = Integer.parseInt(startRow)/pageSize;     //开始页	 	
	 	
	 	JsonQueryBPO bpo=new JsonQueryBPO();
	 	String s="";
	 	String ls_FieldList=bpo.GetFieldList(sql);
	 	
	 	if (!(fieldList==null || fieldList.equals(""))){
	 		s=bpo.QueryData(sql,fieldList,index,pageSize);	 		
	 	}else{
	 		s=bpo.QueryData(sql,ls_FieldList,index,pageSize);
	 	}	 	
	 	//ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");	 
	 	//s="{\"success\":true,\"msg\":\"查询数据成功\"},"+s;
	 	if (!(getCallback()==null || getCallback().equals(""))){
	 		s=getCallback()+"("+s+")";
	 	}
	 	s=s.replaceAll("\r", "");  //替换回车换行，解决EXTJS不显示问题
	 	s=s.replaceAll("\n", "<br>");  //替换回车换行，解决EXTJS不显示问题
		try {
			ServletActionContext.getResponse().getWriter().write(s);	
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
			//super.setSuccess(true);
			//super.setMsg("查询数据成功");
		} catch (IOException e) {
			e.printStackTrace();
		} 			
	 	return this.SUCCESS;		
	}
	
	//检查msgdata验证
	public boolean CheckMsgdata()throws ApplicationException{
		boolean lb_check=true;
		StringBuffer sb_debug=new StringBuffer("加密数据包内容:");
		if (GlobalNames.COMM_CHECK_KEY_SET.equals("3")){	
			if (msgdata==null || msgdatasign==null) return false;
			String aesPassword = GlobalNames.AESPASSWORD;
			String sha512Password=GlobalNames.SHA512PASSWORD;
			sb_debug.append("aespassword:"+aesPassword+"\r\n");
			//先得到UrlDecode后的内容
			try {
				sb_debug.append("msgdata:"+msgdata+"\r\n");
				String old_msgdata = URLDecoder.decode(msgdata,"UTF-8");				
				String old_msgdatasign=URLDecoder.decode(msgdatasign,"UTF-8");
				
				sb_debug.append("msgdata UrlDecoder:"+old_msgdata+"\r\n");
				sb_debug.append("old_msgdatasign:"+old_msgdatasign+"\r\n");
				//URL解码
				String dataDigest=Md5Util.toMD5(sha512Password+old_msgdata);
				if (!dataDigest.equals(old_msgdatasign)) return false;  //签名包不一致
				//数据包还原
				
				msgdata = AesUtil.desEncrypt(old_msgdata,aesPassword);		
				sb_debug.append("aes解密后内容:"+msgdata);
				//log.info(sb_debug.toString());
				//System.out.println(sb_debug.toString());
			} catch (Exception e) {
				return false;
			}  			
			//获取签名
			//HMACSha512Encode hmac = new HMACSha512Encode();
			//String dataDigest = hmac.encryptHMAC(msgdata, sha512Password);
						 
			//AES256CBCEncode aes = new AES256CBCEncode();
			//msgdata=aes.decrypt(msgdata, aesPassword);  //数据包还原						
		}	
		if (msgdata.toLowerCase().indexOf("select")>=0 || msgdata.toLowerCase().indexOf("insert")>=0 || msgdata.toLowerCase().indexOf("delete")>=0  || msgdata.toLowerCase().indexOf("update")>=0 || msgdata.toLowerCase().indexOf("drop")>=0 || msgdata.toLowerCase().indexOf("truncate")>=0 || msgdata.toLowerCase().indexOf("alter")>=0){
			return false;
		}
		return lb_check;
	}
	
	/*
	@Override
	public void validate() {
		super.validate();
	  // 重写ActionSupport的alidate方法
	  // 验证提交数据是否合法
		if (GlobalNames.COMM_CHECK_KEY_SET.equals("3")){			
			String aesPassword = GlobalNames.AESPASSWORD;
			String sha512Password=GlobalNames.SHA512PASSWORD;
			//获取签名
			HMACSha512Encode hmac = new HMACSha512Encode();
			String dataDigest = hmac.encryptHMAC(msgdata, sha512Password);
			if (!dataDigest.equals(msgdatasign)){
				this.addFieldError("verify","数据包签名异常");
			}else{		 
				AES256CBCEncode aes = new AES256CBCEncode();
				msgdata=aes.decrypt(msgdata, aesPassword);  //数据包还原
			}
		}	
	}
	*/
	//根据SQL查询分页排序 解决 相同字段值排序问题	
		public String QueryDataBySqlOrderBy(String sql,String convertcode,Map<String,String> map) throws ApplicationException{		
			
			ServletActionContext.getResponse().setContentType("text/html;charset=GBK");
			String startRow = new Integer(start).toString();     //获取开始行 
			if (startRow==null){
				startRow="0";
			}
		 	
			if (limit==0){                  //默认为每页10行
		 		limit=10;
		 	}
			int pageSize = limit;              //页记录数
		 	
		 	//int index = Integer.parseInt(startRow)/pageSize;     //开始页
			int index = Integer.parseInt(startRow)/pageSize+1;     //开始页   modify by whd 2014.11.08 针对SQLSERVER进行调整
		 	
		 	JsonQueryBPO bpo=new JsonQueryBPO();
		 	String s="";	 	
		 	/*
		 	String ls_FieldList=bpo.GetFieldList(sql);
		 	if (!(fieldList==null || fieldList.equals(""))){
		 		s=bpo.QueryDataBySql(sql,fieldList,index,pageSize);			   
		 	}else{
		 		s=bpo.QueryDataBySql(sql,ls_FieldList,index,pageSize);
		 	}	 	
		 	*/
		 	
		 	s=bpo.QueryDataBySqlOrderBy(sql,convertcode,index,pageSize, map);
		 	
		 	if (!(getCallback()==null || getCallback().equals(""))){
		 		s=getCallback()+"("+s+")";
		 	}
		 	s=s.replaceAll("\r", "");  //替换回车换行，解决EXTJS不显示问题
		 	s=s.replaceAll("\n", "<br>");  //替换回车换行，解决EXTJS不显示问题
		 	
		 	//ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");	 	
			try {
				ServletActionContext.getResponse().getWriter().write(s);	
				ServletActionContext.getResponse().getWriter().flush();
				ServletActionContext.getResponse().getWriter().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 		
		 	return this.SUCCESS;		
		}
		
		public String PrintDTO(Object dto){
			HttpServletResponse response = ServletActionContext.getResponse();
			returnExtJSONToBrowser(response, dto, callback);
			return null;
		}
		
		/**
		 * 输出某个值到json的root格式中
		 * 主要用于展示类似文章内容之类的有特殊字符的内容
		 * @param fieldname
		 * @param fieldvalue
		 * @return
		 */
		public String PrintValueToJsonRoot(String fieldname,String fieldvalue){
			HttpServletResponse response = ServletActionContext.getResponse();
			PrintValueToJsonRoot(response, fieldname, fieldvalue,callback);
			return null;
		}
		
}
