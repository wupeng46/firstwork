package com.lbs.apps.system.dao;

import java.io.Serializable;

public class SysparaDTO implements Serializable {
	private String paracode;
    private String paravalue;
    private String isvalid;
    private String paradesc;
	public String getParacode() {
		return paracode;
	}
	public void setParacode(String paracode) {
		this.paracode = paracode;
	}
	public String getParavalue() {
		return paravalue;
	}
	public void setParavalue(String paravalue) {
		this.paravalue = paravalue;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	public String getParadesc() {
		return paradesc;
	}
	public void setParadesc(String paradesc) {
		this.paradesc = paradesc;
	}
    
    

}
