package com.lbs.apps.system.po;

/**
 * Syspara entity. @author MyEclipse Persistence Tools
 */

public class Syspara implements java.io.Serializable {

	// Fields

	private String paracode;
	private String paravalue;
	private String isvalid;
	private String paradesc;

	// Constructors

	/** default constructor */
	public Syspara() {
	}

	/** minimal constructor */
	public Syspara(String paracode) {
		this.paracode = paracode;
	}

	/** full constructor */
	public Syspara(String paracode, String paravalue, String isvalid,
			String paradesc) {
		this.paracode = paracode;
		this.paravalue = paravalue;
		this.isvalid = isvalid;
		this.paradesc = paradesc;
	}

	// Property accessors

	public String getParacode() {
		return this.paracode;
	}

	public void setParacode(String paracode) {
		this.paracode = paracode;
	}

	public String getParavalue() {
		return this.paravalue;
	}

	public void setParavalue(String paravalue) {
		this.paravalue = paravalue;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getParadesc() {
		return this.paradesc;
	}

	public void setParadesc(String paradesc) {
		this.paradesc = paradesc;
	}

}