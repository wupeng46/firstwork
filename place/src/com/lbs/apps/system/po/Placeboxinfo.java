package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Placeboxinfo entity. @author MyEclipse Persistence Tools
 */

public class Placeboxinfo implements java.io.Serializable {

	// Fields

	private String placeboxid;
	private String placeid;
	private Short xnum;
	private Short ynum;
	private Short levelnum;
	private String placeboxstatus;
	private String isvalid;
	private String orderid;
	private String weighid;
	private String boxid;
	private Integer memberid;
	private Integer companyid;
	private Timestamp statusstartdate;
	private Timestamp statusenddate;
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;
	private String isempty;

	// Constructors

	/** default constructor */
	public Placeboxinfo() {
	}

	/** minimal constructor */
	public Placeboxinfo(String placeboxid, String placeid) {
		this.placeboxid = placeboxid;
		this.placeid = placeid;
	}

	/** full constructor */
	public Placeboxinfo(String placeboxid, String placeid, Short xnum,
			Short ynum, Short levelnum, String placeboxstatus, String isvalid,
			String orderid, String weighid, String boxid, Integer memberid,
			Integer companyid, Timestamp statusstartdate,
			Timestamp statusenddate, String memo, Integer createdby,
			Integer createdorg, Timestamp createddate, Integer modifyby,
			Integer modifyorg, Timestamp modifydate,String isempty) {
		this.placeboxid = placeboxid;
		this.placeid = placeid;
		this.xnum = xnum;
		this.ynum = ynum;
		this.levelnum = levelnum;
		this.placeboxstatus = placeboxstatus;
		this.isvalid = isvalid;
		this.orderid = orderid;
		this.weighid = weighid;
		this.boxid = boxid;
		this.memberid = memberid;
		this.companyid = companyid;
		this.statusstartdate = statusstartdate;
		this.statusenddate = statusenddate;
		this.memo = memo;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
		this.isempty = isempty;
	}

	// Property accessors

	public String getPlaceboxid() {
		return this.placeboxid;
	}

	public void setPlaceboxid(String placeboxid) {
		this.placeboxid = placeboxid;
	}

	public String getPlaceid() {
		return this.placeid;
	}

	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}

	public Short getXnum() {
		return this.xnum;
	}

	public void setXnum(Short xnum) {
		this.xnum = xnum;
	}

	public Short getYnum() {
		return this.ynum;
	}

	public void setYnum(Short ynum) {
		this.ynum = ynum;
	}

	public Short getLevelnum() {
		return this.levelnum;
	}

	public void setLevelnum(Short levelnum) {
		this.levelnum = levelnum;
	}

	public String getPlaceboxstatus() {
		return this.placeboxstatus;
	}

	public void setPlaceboxstatus(String placeboxstatus) {
		this.placeboxstatus = placeboxstatus;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getWeighid() {
		return this.weighid;
	}

	public void setWeighid(String weighid) {
		this.weighid = weighid;
	}

	public String getBoxid() {
		return this.boxid;
	}

	public void setBoxid(String boxid) {
		this.boxid = boxid;
	}

	public Integer getMemberid() {
		return this.memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public Integer getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public Timestamp getStatusstartdate() {
		return this.statusstartdate;
	}

	public void setStatusstartdate(Timestamp statusstartdate) {
		this.statusstartdate = statusstartdate;
	}

	public Timestamp getStatusenddate() {
		return this.statusenddate;
	}

	public void setStatusenddate(Timestamp statusenddate) {
		this.statusenddate = statusenddate;
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

	public String getIsempty() {
		return isempty;
	}

	public void setIsempty(String isempty) {
		this.isempty = isempty;
	}
	
	

}