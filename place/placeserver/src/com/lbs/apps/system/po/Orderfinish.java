package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Orderfinish entity. @author MyEclipse Persistence Tools
 */

public class Orderfinish implements java.io.Serializable {

	// Fields

	private String orderid;
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
	public Orderfinish() {
	}

	/** minimal constructor */
	public Orderfinish(String orderid) {
		this.orderid = orderid;
	}

	/** full constructor */
	public Orderfinish(String orderid, Timestamp finishdate, String finishdesc,
			Integer createdby, Integer createdorg, Timestamp createddate,
			Integer modifyby, Integer modifyorg, Timestamp modifydate) {
		this.orderid = orderid;
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

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
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