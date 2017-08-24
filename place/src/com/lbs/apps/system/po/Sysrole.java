package com.lbs.apps.system.po;

/**
 * Sysrole entity. @author MyEclipse Persistence Tools
 */

public class Sysrole implements java.io.Serializable {

	// Fields

	private Short roleid;
	private String rolename;
	private String roledesc;
	private String ispreset;
	private String sysname;

	// Constructors

	/** default constructor */
	public Sysrole() {
	}

	/** minimal constructor */
	public Sysrole(Short roleid) {
		this.roleid = roleid;
	}

	/** full constructor */
	public Sysrole(Short roleid, String rolename, String roledesc,
			String ispreset, String sysname) {
		this.roleid = roleid;
		this.rolename = rolename;
		this.roledesc = roledesc;
		this.ispreset = ispreset;
		this.sysname = sysname;
	}

	// Property accessors

	public Short getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Short roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoledesc() {
		return this.roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public String getIspreset() {
		return this.ispreset;
	}

	public void setIspreset(String ispreset) {
		this.ispreset = ispreset;
	}

	public String getSysname() {
		return this.sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

}