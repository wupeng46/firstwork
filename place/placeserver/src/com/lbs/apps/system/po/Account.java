package com.lbs.apps.system.po;

/**
 * Account entity. @author MyEclipse Persistence Tools
 */

public class Account implements java.io.Serializable {

	// Fields

	private Integer userid;
	private Double totalamount;
	private Double useramount;
	private Double freezeamount;
	private Double integral;

	// Constructors

	/** default constructor */
	public Account() {
	}

	/** minimal constructor */
	public Account(Integer userid) {
		this.userid = userid;
	}

	/** full constructor */
	public Account(Integer userid, Double totalamount, Double useramount,
			Double freezeamount, Double integral) {
		this.userid = userid;
		this.totalamount = totalamount;
		this.useramount = useramount;
		this.freezeamount = freezeamount;
		this.integral = integral;
	}

	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Double getTotalamount() {
		return this.totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	public Double getUseramount() {
		return this.useramount;
	}

	public void setUseramount(Double useramount) {
		this.useramount = useramount;
	}

	public Double getFreezeamount() {
		return this.freezeamount;
	}

	public void setFreezeamount(Double freezeamount) {
		this.freezeamount = freezeamount;
	}

	public Double getIntegral() {
		return this.integral;
	}

	public void setIntegral(Double integral) {
		this.integral = integral;
	}

}