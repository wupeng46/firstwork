package com.lbs.apps.system.dao;

import java.io.Serializable;

public class NoticemsgDTO implements Serializable{
	  private String noticemsgid;
	  private String noticetype ; 
	  private String  receiverid; 
	  private String  msgcontent;
	  private String  isread; 
	  private String  readtime;
	  private String createdby  ;
	  private String  createddate;
	public String getNoticemsgid() {
		return noticemsgid;
	}
	public void setNoticemsgid(String noticemsgid) {
		this.noticemsgid = noticemsgid;
	}
	public String getNoticetype() {
		return noticetype;
	}
	public void setNoticetype(String noticetype) {
		this.noticetype = noticetype;
	}
	public String getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public String getIsread() {
		return isread;
	}
	public void setIsread(String isread) {
		this.isread = isread;
	}
	public String getReadtime() {
		return readtime;
	}
	public void setReadtime(String readtime) {
		this.readtime = readtime;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	} 
	  
}
