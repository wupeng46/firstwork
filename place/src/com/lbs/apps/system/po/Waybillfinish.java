package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Waybillfinish entity. @author MyEclipse Persistence Tools
 */

public class Waybillfinish implements java.io.Serializable {

	// Fields

	private String waybillid;
	private Timestamp finishdate;
	private String finishdesc;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Waybillfinish() {
	}

	/** minimal constructor */
	public Waybillfinish(String waybillid) {
		this.waybillid = waybillid;
	}

	/** full constructor */
	public Waybillfinish(String waybillid, Timestamp finishdate,
			String finishdesc, Integer createdby, Integer createdorg,
			Timestamp createddate, Integer modifyby, Integer modifyorg,
			Timestamp modifydate) {
		this.waybillid = waybillid;
		this.finishdate = finishdate;
		this.finishdesc = finishdesc;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
	}

	// Property accessors

	public String getWaybillid() {
		return this.waybillid;
	}

	public void setWaybillid(String waybillid) {
		this.waybillid = waybillid;
	}

	public Timestamp getFinishdate() {
		return this.finishdate;
	}

	public void setFinishdate(Timestamp finishdate) {
		this.finishdate = finishdate;
	}

	public String getFinishdesc() {
		return this.finishdesc;
	}

	public void setFinishdesc(String finishdesc) {
		this.finishdesc = finishdesc;
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