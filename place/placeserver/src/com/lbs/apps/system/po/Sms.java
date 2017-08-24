package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Sms entity. @author MyEclipse Persistence Tools
 */

public class Sms implements java.io.Serializable {

	// Fields

	private Integer smsid;
	private String smstype;
	private Timestamp plansendtime;
	private Timestamp actualsendtime;
	private String smsstatus;
	private String smsmsg;
	private String telephone;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Sms() {
	}

	/** minimal constructor */
	public Sms(Integer smsid) {
		this.smsid = smsid;
	}

	/** full constructor */
	public Sms(Integer smsid, String smstype, Timestamp plansendtime,
			Timestamp actualsendtime, String smsstatus, String smsmsg,
			String telephone, Integer createdby, Integer createdorg,
			Timestamp createddate, Integer modifyby, Integer modifyorg,
			Timestamp modifydate) {
		this.smsid = smsid;
		this.smstype = smstype;
		this.plansendtime = plansendtime;
		this.actualsendtime = actualsendtime;
		this.smsstatus = smsstatus;
		this.smsmsg = smsmsg;
		this.telephone = telephone;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
	}

	// Property accessors

	public Integer getSmsid() {
		return this.smsid;
	}

	public void setSmsid(Integer smsid) {
		this.smsid = smsid;
	}

	public String getSmstype() {
		return this.smstype;
	}

	public void setSmstype(String smstype) {
		this.smstype = smstype;
	}

	public Timestamp getPlansendtime() {
		return this.plansendtime;
	}

	public void setPlansendtime(Timestamp plansendtime) {
		this.plansendtime = plansendtime;
	}

	public Timestamp getActualsendtime() {
		return this.actualsendtime;
	}

	public void setActualsendtime(Timestamp actualsendtime) {
		this.actualsendtime = actualsendtime;
	}

	public String getSmsstatus() {
		return this.smsstatus;
	}

	public void setSmsstatus(String smsstatus) {
		this.smsstatus = smsstatus;
	}

	public String getSmsmsg() {
		return this.smsmsg;
	}

	public void setSmsmsg(String smsmsg) {
		this.smsmsg = smsmsg;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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