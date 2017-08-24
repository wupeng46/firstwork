package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Syserrorlog entity. @author MyEclipse Persistence Tools
 */

public class Syserrorlog implements java.io.Serializable {

	// Fields

	private Integer syserrorlogid;
	private String modmethod;
	private String logdesc;
	private String errorreason;
	private Integer createdby;
	private Timestamp createddate;

	// Constructors

	/** default constructor */
	public Syserrorlog() {
	}

	/** minimal constructor */
	public Syserrorlog(Integer syserrorlogid) {
		this.syserrorlogid = syserrorlogid;
	}

	/** full constructor */
	public Syserrorlog(Integer syserrorlogid, String modmethod, String logdesc,
			String errorreason, Integer createdby, Timestamp createddate) {
		this.syserrorlogid = syserrorlogid;
		this.modmethod = modmethod;
		this.logdesc = logdesc;
		this.errorreason = errorreason;
		this.createdby = createdby;
		this.createddate = createddate;
	}

	// Property accessors

	public Integer getSyserrorlogid() {
		return this.syserrorlogid;
	}

	public void setSyserrorlogid(Integer syserrorlogid) {
		this.syserrorlogid = syserrorlogid;
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

	public String getErrorreason() {
		return this.errorreason;
	}

	public void setErrorreason(String errorreason) {
		this.errorreason = errorreason;
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