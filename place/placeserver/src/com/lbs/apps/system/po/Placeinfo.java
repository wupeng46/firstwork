package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Placeinfo entity. @author MyEclipse Persistence Tools
 */

public class Placeinfo implements java.io.Serializable {

	// Fields

	private String placeid;
	private String prefix;
	private Short xmax;
	private Short ymax;
	private Short levelnum;
	private String isvalid;
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Placeinfo() {
	}

	/** minimal constructor */
	public Placeinfo(String placeid) {
		this.placeid = placeid;
	}

	/** full constructor */
	public Placeinfo(String placeid, String prefix, Short xmax, Short ymax,
			Short levelnum, String isvalid, String memo, Integer createdby,
			Integer createdorg, Timestamp createddate, Integer modifyby,
			Integer modifyorg, Timestamp modifydate) {
		this.placeid = placeid;
		this.prefix = prefix;
		this.xmax = xmax;
		this.ymax = ymax;
		this.levelnum = levelnum;
		this.isvalid = isvalid;
		this.memo = memo;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
	}

	// Property accessors

	public String getPlaceid() {
		return this.placeid;
	}

	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Short getXmax() {
		return this.xmax;
	}

	public void setXmax(Short xmax) {
		this.xmax = xmax;
	}

	public Short getYmax() {
		return this.ymax;
	}

	public void setYmax(Short ymax) {
		this.ymax = ymax;
	}

	public Short getLevelnum() {
		return this.levelnum;
	}

	public void setLevelnum(Short levelnum) {
		this.levelnum = levelnum;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
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

	public Integer getModifyby() {
		return this.modifyby;
	}

	public void setModifyby(Integer modifyby) {
		this.modifyby = modifyby;
	}

	public Integer getModifyorg() {
		return this.modifyorg;
	}

	public void setModifyorg(Integer modifyorg) {
		this.modifyorg = modifyorg;
	}

	public Timestamp getModifydate() {
		return this.modifydate;
	}

	public void setModifydate(Timestamp modifydate) {
		this.modifydate = modifydate;
	}

}