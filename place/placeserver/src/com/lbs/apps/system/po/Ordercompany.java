package com.lbs.apps.system.po;

/**
 * Ordercompany entity. @author MyEclipse Persistence Tools
 */

public class Ordercompany implements java.io.Serializable {

	// Fields

	private String ordercompanyid;
	private String orderid;
	private Integer companyid;

	// Constructors

	/** default constructor */
	public Ordercompany() {
	}

	/** minimal constructor */
	public Ordercompany(String ordercompanyid) {
		this.ordercompanyid = ordercompanyid;
	}

	/** full constructor */
	public Ordercompany(String ordercompanyid, String orderid, Integer companyid) {
		this.ordercompanyid = ordercompanyid;
		this.orderid = orderid;
		this.companyid = companyid;
	}

	// Property accessors

	public String getOrdercompanyid() {
		return this.ordercompanyid;
	}

	public void setOrdercompanyid(String ordercompanyid) {
		this.ordercompanyid = ordercompanyid;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

}