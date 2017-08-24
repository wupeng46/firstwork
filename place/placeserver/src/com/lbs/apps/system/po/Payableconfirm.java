package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Payableconfirm entity. @author MyEclipse Persistence Tools
 */

public class Payableconfirm implements java.io.Serializable {

	// Fields

	private String payableconfirmid;
	private String payableapplyid;
	private String payableid;
	private Double netweight;
	private Double price;
	private Double amount;
	private Timestamp confirmtime;
	private Timestamp applytime;
	private String applybatch;
	private String isvalid;
	private String cancelreson;
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
	public Payableconfirm() {
	}

	/** minimal constructor */
	public Payableconfirm(String payableconfirmid, String payableapplyid,
			String payableid) {
		this.payableconfirmid = payableconfirmid;
		this.payableapplyid = payableapplyid;
		this.payableid = payableid;
	}

	/** full constructor */
	public Payableconfirm(String payableconfirmid, String payableapplyid,
			String payableid, Double netweight, Double price, Double amount,
			Timestamp confirmtime, Timestamp applytime, String applybatch,
			String isvalid, String cancelreson, Timestamp canceltime,
			Integer cancelby, Integer createdby, Integer createdorg,
			Timestamp createddate, Integer modifyby, Integer modifyorg,
			Timestamp modifydate) {
		this.payableconfirmid = payableconfirmid;
		this.payableapplyid = payableapplyid;
		this.payableid = payableid;
		this.netweight = netweight;
		this.price = price;
		this.amount = amount;
		this.confirmtime = confirmtime;
		this.applytime = applytime;
		this.applybatch = applybatch;
		this.isvalid = isvalid;
		this.cancelreson = cancelreson;
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

	public String getPayableconfirmid() {
		return this.payableconfirmid;
	}

	public void setPayableconfirmid(String payableconfirmid) {
		this.payableconfirmid = payableconfirmid;
	}

	public String getPayableapplyid() {
		return this.payableapplyid;
	}

	public void setPayableapplyid(String payableapplyid) {
		this.payableapplyid = payableapplyid;
	}

	public String getPayableid() {
		return this.payableid;
	}

	public void setPayableid(String payableid) {
		this.payableid = payableid;
	}

	public Double getNetweight() {
		return this.netweight;
	}

	public void setNetweight(Double netweight) {
		this.netweight = netweight;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Timestamp getConfirmtime() {
		return this.confirmtime;
	}

	public void setConfirmtime(Timestamp confirmtime) {
		this.confirmtime = confirmtime;
	}

	public Timestamp getApplytime() {
		return this.applytime;
	}

	public void setApplytime(Timestamp applytime) {
		this.applytime = applytime;
	}

	public String getApplybatch() {
		return this.applybatch;
	}

	public void setApplybatch(String applybatch) {
		this.applybatch = applybatch;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getCancelreson() {
		return this.cancelreson;
	}

	public void setCancelreson(String cancelreson) {
		this.cancelreson = cancelreson;
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