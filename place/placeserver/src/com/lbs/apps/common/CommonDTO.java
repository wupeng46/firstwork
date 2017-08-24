package com.lbs.apps.common;

import java.io.Serializable;

public class CommonDTO implements Serializable {
	public String success;      //是否成功标记 
	public String msg;          //返回消息
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
	
	

}
