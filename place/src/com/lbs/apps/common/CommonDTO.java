package com.lbs.apps.common;

import java.io.Serializable;

public class CommonDTO implements Serializable {
	public String success;      //�Ƿ�ɹ���� 
	public String msg;          //������Ϣ
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
