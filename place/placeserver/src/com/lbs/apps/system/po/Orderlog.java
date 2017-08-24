package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Orderlog entity. @author MyEclipse Persistence Tools
 */

public class Orderlog implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderid;
	private String orderopertype;
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;

	// Constructors

	/** default constructor */
	public Orderlog() {
	}

	/** minimal constructor */
	public Orderlog(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Orderlog(Integer id, String orderid, String orderopertype,
			String memo, Integer createdby, Integer createdorg,
			Timestamp createddate) {
		this.id = id;
		this.orderid = orderid;
		this.orderopertype = orderopertype;
		this.memo = memo;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderopertype() {
		return this.orderopertype;
	}

	public void setOrderopertype(String orderopertype) {
		this.orderopertype = orderopertype;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

}