package com.lbs.apps.system.dao;

import java.io.Serializable;

public class SysroleDTO implements Serializable {
	private Short roleid;
    private String rolename;
    private String roledesc;
    private String ispreset;
    private String sysname;
	public Short getRoleid() {
		return roleid;
	}
	public void setRoleid(Short roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getRoledesc() {
		return roledesc;
	}
	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}
	public String getIspreset() {
		return ispreset;
	}
	public void setIspreset(String ispreset) {
		this.ispreset = ispreset;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}   
	
	

}
