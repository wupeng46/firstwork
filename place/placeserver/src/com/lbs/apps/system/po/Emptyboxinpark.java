package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Emptyboxinpark entity. @author MyEclipse Persistence Tools
 */

public class Emptyboxinpark implements java.io.Serializable {

	// Fields

	private String emptyboxinparkid;
	private String boxid;
	private String boxtype;
	private Integer companyid;
	private Timestamp inparktime;
	private String isvalid;
	private String cancelreason;
	private Timestamp canceltime;
	private Integer cancelby;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Emptyboxinpark() {
	}

	/** minimal constructor */
	public Emptyboxinpark(String emptyboxinparkid) {
		this.emptyboxinparkid = emptyboxinparkid;
	}

	/** full constructor */
	public Emptyboxinpark(String emptyboxinparkid, String boxid,
			String boxtype, Integer companyid, Timestamp inparktime,
			String isvalid, String cancelreason, Timestamp canceltime,
			Integer cancelby, Integer createdby, Integer createdorg,
			Timestamp createddate, Integer modifyby, Integer modifyorg,
			Timestamp modifydate) {
		this.emptyboxinparkid = emptyboxinparkid;
		this.boxid = boxid;
		this.boxtype = boxtype;
		this.companyid = companyid;
		this.inparktime = inparktime;
		this.isvalid = isvalid;
		this.cancelreason = cancelreason;
		this.canceltime = canceltime;
		this.cancelby = cancelby;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
	}

	// Property accessors

	public String getEmptyboxinparkid() {
		return this.emptyboxinparkid;
	}

	public void setEmptyboxinparkid(String emptyboxinparkid) {
		this.emptyboxinparkid = emptyboxinparkid;
	}

	public String getBoxid() {
		return this.boxid;
	}

	public void setBoxid(String boxid) {
		this.boxid = boxid;
	}

	public String getBoxtype() {
		return this.boxtype;
	}

	public void setBoxtype(String boxtype) {
		this.boxtype = boxtype;
	}

	public Integer getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public Timestamp getInparktime() {
		return this.inparktime;
	}

	public void setInparktime(Timestamp inparktime) {
		this.inparktime = inparktime;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getCancelreason() {
		return this.cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}

	public Timestamp getCanceltime() {
		return this.canceltime;
	}

	public void setCanceltime(Timestamp canceltime) {
		this.canceltime = canceltime;
	}

	public Integer getCancelby() {
		return this.cancelby;
	}

	public void setCancelby(Integer cancelby) {
		this.cancelby = cancelby;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Integer getCreatedorg() {
		return this.createdorg;
	}

	public void setCreatedorg(Integer createdorg) {
		this.createdorg = createdorg;
	}

	public Timestamp getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public Integer getModifyby() {
		return this.modifyby;
	}

	public void setModifyby(Integer modifyby) {
		this.modifyby = modifyby;
	}

	public Integer getModifyorg() {
		return this.modifyorg;
	}

	public void setModifyorg(Integer modifyorg) {
		this.modifyorg = modifyorg;
	}

	public Timestamp getModifydate() {
		return this.modifydate;
	}

	public void setModifydate(Timestamp modifydate) {
		this.modifydate = modifydate;
	}

}