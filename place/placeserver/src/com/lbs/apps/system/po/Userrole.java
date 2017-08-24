package com.lbs.apps.system.po;

/**
 * Userrole entity. @author MyEclipse Persistence Tools
 */

public class Userrole implements java.io.Serializable {

	// Fields

	private UserroleId id;

	// Constructors

	/** default constructor */
	public Userrole() {
	}

	/** full constructor */
	public Userrole(UserroleId id) {
		this.id = id;
	}

	// Property accessors

	public UserroleId getId() {
		return this.id;
	}

	public void setId(UserroleId id) {
		this.id = id;
	}

}