package com.lbs.apps.system.po;

/**
 * WaybillweighId entity. @author MyEclipse Persistence Tools
 */

public class WaybillweighId implements java.io.Serializable {

	// Fields

	private String waybillid;
	private String weighid;

	// Constructors

	/** default constructor */
	public WaybillweighId() {
	}

	/** full constructor */
	public WaybillweighId(String waybillid, String weighid) {
		this.waybillid = waybillid;
		this.weighid = weighid;
	}

	// Property accessors

	public String getWaybillid() {
		return this.waybillid;
	}

	public void setWaybillid(String waybillid) {
		this.waybillid = waybillid;
	}

	public String getWeighid() {
		return this.weighid;
	}

	public void setWeighid(String weighid) {
		this.weighid = weighid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WaybillweighId))
			return false;
		WaybillweighId castOther = (WaybillweighId) other;

		return ((this.getWaybillid() == castOther.getWaybillid()) || (this
				.getWaybillid() != null && castOther.getWaybillid() != null && this
				.getWaybillid().equals(castOther.getWaybillid())))
				&& ((this.getWeighid() == castOther.getWeighid()) || (this
						.getWeighid() != null && castOther.getWeighid() != null && this
						.getWeighid().equals(castOther.getWeighid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getWaybillid() == null ? 0 : this.getWaybillid().hashCode());
		result = 37 * result
				+ (getWeighid() == null ? 0 : this.getWeighid().hashCode());
		return result;
	}

}