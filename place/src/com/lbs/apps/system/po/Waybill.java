package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Waybill entity. @author MyEclipse Persistence Tools
 */

public class Waybill implements java.io.Serializable {

	// Fields

	private String waybillid;
	private String orderid;
	private String ordercontractid;
	private Timestamp startdate;
	private Timestamp enddate;
	private String batchno;
	private Double price;
	private Double weight;
	private Double amount;
	private String memo;
	private String isvalid;
	private String waybillstatus;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Waybill() {
	}

	/** minimal constructor */
	public Waybill(String waybillid, String orderid, String ordercontractid) {
		this.waybillid = waybillid;
		this.orderid = orderid;
		this.ordercontractid = ordercontractid;
	}

	/** full constructor */
	public Waybill(String waybillid, String orderid, String ordercontractid,
			Timestamp startdate, Timestamp enddate, String batchno,
			Double price, Double weight, Double amount, String memo,
			String isvalid, String waybillstatus, Integer createdby,
			Integer createdorg, Timestamp createddate, Integer modifyby,
			Integer modifyorg, Timestamp modifydate) {
		this.waybillid = waybillid;
		this.orderid = orderid;
		this.ordercontractid = ordercontractid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.batchno = batchno;
		this.price = price;
		this.weight = weight;
		this.amount = amount;
		this.memo = memo;
		this.isvalid = isvalid;
		this.waybillstatus = waybillstatus;
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

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrdercontractid() {
		return this.ordercontractid;
	}

	public void setOrdercontractid(String ordercontractid) {
		this.ordercontractid = ordercontractid;
	}

	public Timestamp getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public String getBatchno() {
		return this.batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getWaybillstatus() {
		return this.waybillstatus;
	}

	public void setWaybillstatus(String waybillstatus) {
		this.waybillstatus = waybillstatus;
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