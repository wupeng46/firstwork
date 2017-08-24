package com.lbs.apps.system.po;

/**
 * SyscodeId entity. @author MyEclipse Persistence Tools
 */

public class SyscodeId implements java.io.Serializable {

	// Fields

	private String domainid;
	private String domaincode;

	// Constructors

	/** default constructor */
	public SyscodeId() {
	}

	/** full constructor */
	public SyscodeId(String domainid, String domaincode) {
		this.domainid = domainid;
		this.domaincode = domaincode;
	}

	// Property accessors

	public String getDomainid() {
		return this.domainid;
	}

	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}

	public String getDomaincode() {
		return this.domaincode;
	}

	public void setDomaincode(String domaincode) {
		this.domaincode = domaincode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SyscodeId))
			return false;
		SyscodeId castOther = (SyscodeId) other;

		return ((this.getDomainid() == castOther.getDomainid()) || (this
				.getDomainid() != null && castOther.getDomainid() != null && this
				.getDomainid().equals(castOther.getDomainid())))
				&& ((this.getDomaincode() == castOther.getDomaincode()) || (this
						.getDomaincode() != null
						&& castOther.getDomaincode() != null && this
						.getDomaincode().equals(castOther.getDomaincode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDomainid() == null ? 0 : this.getDomainid().hashCode());
		result = 37
				* result
				+ (getDomaincode() == null ? 0 : this.getDomaincode()
						.hashCode());
		return result;
	}

}