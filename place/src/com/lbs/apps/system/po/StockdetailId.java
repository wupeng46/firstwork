package com.lbs.apps.system.po;

/**
 * StockdetailId entity. @author MyEclipse Persistence Tools
 */

public class StockdetailId implements java.io.Serializable {

	// Fields

	private String stockid;
	private Integer mxid;

	// Constructors

	/** default constructor */
	public StockdetailId() {
	}

	/** full constructor */
	public StockdetailId(String stockid, Integer mxid) {
		this.stockid = stockid;
		this.mxid = mxid;
	}

	// Property accessors

	public String getStockid() {
		return this.stockid;
	}

	public void setStockid(String stockid) {
		this.stockid = stockid;
	}

	public Integer getMxid() {
		return this.mxid;
	}

	public void setMxid(Integer mxid) {
		this.mxid = mxid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StockdetailId))
			return false;
		StockdetailId castOther = (StockdetailId) other;

		return ((this.getStockid() == castOther.getStockid()) || (this
				.getStockid() != null && castOther.getStockid() != null && this
				.getStockid().equals(castOther.getStockid())))
				&& ((this.getMxid() == castOther.getMxid()) || (this.getMxid() != null
						&& castOther.getMxid() != null && this.getMxid()
						.equals(castOther.getMxid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStockid() == null ? 0 : this.getStockid().hashCode());
		result = 37 * result
				+ (getMxid() == null ? 0 : this.getMxid().hashCode());
		return result;
	}

}