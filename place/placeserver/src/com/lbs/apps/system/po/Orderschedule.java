package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Orderschedule entity. @author MyEclipse Persistence Tools
 */

public class Orderschedule implements java.io.Serializable {

	// Fields

	private String orderid;
	private Timestamp boxarrivedate;
	private Timestamp packingsdate;
	private Timestamp packingedate;
	private Timestamp outdate;

	// Constructors

	/** default constructor */
	public Orderschedule() {
	}

	/** minimal constructor */
	public Orderschedule(String orderid) {
		this.orderid = orderid;
	}

	/** full constructor */
	public Orderschedule(String orderid, Timestamp boxarrivedate,
			Timestamp packingsdate, Timestamp packingedate, Timestamp outdate) {
		this.orderid = orderid;
		this.boxarrivedate = boxarrivedate;
		this.packingsdate = packingsdate;
		this.packingedate = packingedate;
		this.outdate = outdate;
	}

	// Property accessors

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Timestamp getBoxarrivedate() {
		return this.boxarrivedate;
	}

	public void setBoxarrivedate(Timestamp boxarrivedate) {
		this.boxarrivedate = boxarrivedate;
	}

	public Timestamp getPackingsdate() {
		return this.packingsdate;
	}

	public void setPackingsdate(Timestamp packingsdate) {
		this.packingsdate = packingsdate;
	}

	public Timestamp getPackingedate() {
		return this.packingedate;
	}

	public void setPackingedate(Timestamp packingedate) {
		this.packingedate = packingedate;
	}

	public Timestamp getOutdate() {
		return this.outdate;
	}

	public void setOutdate(Timestamp outdate) {
		this.outdate = outdate;
	}

}