package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Emptyboxoutplace entity. @author MyEclipse Persistence Tools
 */

public class Emptyboxoutplace implements java.io.Serializable {

	// Fields

	private String emptyboxoutplaceid;
	private String emptyboxinplaceid;
	private String emptyboxinparkid;
	private String boxid;
	private String boxtype;
	private Integer companyid;
	private String placeboxid;
	private Timestamp inparktime;
	private Timestamp inplacetime;
	private Timestamp outplacetime;
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
	public Emptyboxoutplace() {
	}

	/** minimal constructor */
	public Emptyboxoutplace(String emptyboxoutplaceid, String emptyboxinplaceid) {
		this.emptyboxoutplaceid = emptyboxoutplaceid;
		this.emptyboxinplaceid = emptyboxinplaceid;
	}

	/** full constructor */
	public Emptyboxoutplace(String emptyboxoutplaceid,
			String emptyboxinplaceid, String emptyboxinparkid, String boxid,
			String boxtype, Integer companyid, String placeboxid,
			Timestamp inparktime, Timestamp inplacetime,
			Timestamp outplacetime, String isvalid, String cancelreason,
			Timestamp canceltime, Integer cancelby, Integer createdby,
			Integer createdorg, Timestamp createddate, Integer modifyby,
			Integer modifyorg, Timestamp modifydate) {
		this.emptyboxoutplaceid = emptyboxoutplaceid;
		this.emptyboxinplaceid = emptyboxinplaceid;
		this.emptyboxinparkid = emptyboxinparkid;
		this.boxid = boxid;
		this.boxtype = boxtype;
		this.companyid = companyid;
		this.placeboxid = placeboxid;
		this.inparktime = inparktime;
		this.inplacetime = inplacetime;
		this.outplacetime = outplacetime;
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

	public String getEmptyboxoutplaceid() {
		return this.emptyboxoutplaceid;
	}

	public void setEmptyboxoutplaceid(String emptyboxoutplaceid) {
		this.emptyboxoutplaceid = emptyboxoutplaceid;
	}

	public String getEmptyboxinplaceid() {
		return this.emptyboxinplaceid;
	}

	public void setEmptyboxinplaceid(String emptyboxinplaceid) {
		this.emptyboxinplaceid = emptyboxinplaceid;
	}

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

	public String getPlaceboxid() {
		return this.placeboxid;
	}

	public void setPlaceboxid(String placeboxid) {
		this.placeboxid = placeboxid;
	}

	public Timestamp getInparktime() {
		return this.inparktime;
	}

	public void setInparktime(Timestamp inparktime) {
		this.inparktime = inparktime;
	}

	public Timestamp getInplacetime() {
		return this.inplacetime;
	}

	public void setInplacetime(Timestamp inplacetime) {
		this.inplacetime = inplacetime;
	}

	public Timestamp getOutplacetime() {
		return this.outplacetime;
	}

	public void setOutplacetime(Timestamp outplacetime) {
		this.outplacetime = outplacetime;
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