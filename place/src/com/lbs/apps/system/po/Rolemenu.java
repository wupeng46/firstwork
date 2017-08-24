package com.lbs.apps.system.po;

/**
 * Rolemenu entity. @author MyEclipse Persistence Tools
 */

public class Rolemenu implements java.io.Serializable {

	// Fields

	private RolemenuId id;

	// Constructors

	/** default constructor */
	public Rolemenu() {
	}

	/** full constructor */
	public Rolemenu(RolemenuId id) {
		this.id = id;
	}

	// Property accessors

	public RolemenuId getId() {
		return this.id;
	}

	public void setId(RolemenuId id) {
		this.id = id;
	}

}