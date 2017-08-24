package com.lbs.apps.system.po;

/**
 * Syscode entity. @author MyEclipse Persistence Tools
 */

public class Syscode implements java.io.Serializable {

	// Fields

	private SyscodeId id;
	private String domainname;
	private String isvalid;
	private String parameter1;
	private String parameter2;
	private String parameter3;
	private String parameter4;

	// Constructors

	/** default constructor */
	public Syscode() {
	}

	/** minimal constructor */
	public Syscode(SyscodeId id) {
		this.id = id;
	}

	/** full constructor */
	public Syscode(SyscodeId id, String domainname, String isvalid,
			String parameter1, String parameter2, String parameter3,
			String parameter4) {
		this.id = id;
		this.domainname = domainname;
		this.isvalid = isvalid;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.parameter3 = parameter3;
		this.parameter4 = parameter4;
	}

	// Property accessors

	public SyscodeId getId() {
		return this.id;
	}

	public void setId(SyscodeId id) {
		this.id = id;
	}

	public String getDomainname() {
		return this.domainname;
	}

	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getParameter1() {
		return this.parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	public String getParameter2() {
		return this.parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}

	public String getParameter3() {
		return this.parameter3;
	}

	public void setParameter3(String parameter3) {
		this.parameter3 = parameter3;
	}

	public String getParameter4() {
		return this.parameter4;
	}

	public void setParameter4(String parameter4) {
		this.parameter4 = parameter4;
	}

}