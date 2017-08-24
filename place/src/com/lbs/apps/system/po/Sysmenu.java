package com.lbs.apps.system.po;

/**
 * Sysmenu entity. @author MyEclipse Persistence Tools
 */

public class Sysmenu implements java.io.Serializable {

	// Fields

	private Integer menuid;
	private String menuname;
	private Integer parentid;
	private String menutype;
	private String sysname;
	private String objectname;
	private Short orderno;
	private String isvalid;

	// Constructors

	/** default constructor */
	public Sysmenu() {
	}

	/** minimal constructor */
	public Sysmenu(Integer menuid) {
		this.menuid = menuid;
	}

	/** full constructor */
	public Sysmenu(Integer menuid, String menuname, Integer parentid,
			String menutype, String sysname, String objectname, Short orderno,
			String isvalid) {
		this.menuid = menuid;
		this.menuname = menuname;
		this.parentid = parentid;
		this.menutype = menutype;
		this.sysname = sysname;
		this.objectname = objectname;
		this.orderno = orderno;
		this.isvalid = isvalid;
	}

	// Property accessors

	public Integer getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public Integer getParentid() {
		return this.parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public String getMenutype() {
		return this.menutype;
	}

	public void setMenutype(String menutype) {
		this.menutype = menutype;
	}

	public String getSysname() {
		return this.sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getObjectname() {
		return this.objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

	public Short getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Short orderno) {
		this.orderno = orderno;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

}