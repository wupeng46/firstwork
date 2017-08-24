package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Systemlog entity. @author MyEclipse Persistence Tools
 */

public class Systemlog implements java.io.Serializable {

	// Fields

	private Integer systemlogid;
	private String modmethod;
	private String logdesc;
	private Integer createdby;
	private Timestamp createddate;

	// Constructors

	/** default constructor */
	public Systemlog() {
	}

	/** minimal constructor */
	public Systemlog(Integer systemlogid) {
		this.systemlogid = systemlogid;
	}

	/** full constructor */
	public Systemlog(Integer systemlogid, String modmethod, String logdesc,
			Integer createdby, Timestamp createddate) {
		this.systemlogid = systemlogid;
		this.modmethod = modmethod;
		this.logdesc = logdesc;
		this.createdby = createdby;
		this.createddate = createddate;
	}

	// Property accessors

	public Integer getSystemlogid() {
		return this.systemlogid;
	}

	public void setSystemlogid(Integer systemlogid) {
		this.systemlogid = systemlogid;
	}

	public String getModmethod() {
		return this.modmethod;
	}

	public void setModmethod(String modmethod) {
		this.modmethod = modmethod;
	}

	public String getLogdesc() {
		return this.logdesc;
	}

	public void setLogdesc(String logdesc) {
		this.logdesc = logdesc;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

}