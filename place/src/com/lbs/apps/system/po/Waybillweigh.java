package com.lbs.apps.system.po;

/**
 * Waybillweigh entity. @author MyEclipse Persistence Tools
 */

public class Waybillweigh implements java.io.Serializable {

	// Fields

	private WaybillweighId id;

	// Constructors

	/** default constructor */
	public Waybillweigh() {
	}

	/** full constructor */
	public Waybillweigh(WaybillweighId id) {
		this.id = id;
	}

	// Property accessors

	public WaybillweighId getId() {
		return this.id;
	}

	public void setId(WaybillweighId id) {
		this.id = id;
	}

}