package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Boxinfo entity. @author MyEclipse Persistence Tools
 */

public class Boxinfo implements java.io.Serializable {

	// Fields

	private String boxid;
	private String boxtype;
	private String postype;
	private String isvalid;
	private Integer memberid;
	private Integer companyid;
	private String orderid;
	private String weighid;
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;
	private String placeboxid;

	// Constructors

	/** default constructor */
	public Boxinfo() {
	}

	/** minimal constructor */
	public Boxinfo(String boxid) {
		this.boxid = boxid;
	}

	/** full constructor */
	public Boxinfo(String boxid, String boxtype, String postype,
			String isvalid, Integer memberid, Integer companyid,
			String orderid, String weighid, String memo, Integer createdby,
			Integer createdorg, Timestamp createddate, Integer modifyby,
			Integer modifyorg, Timestamp modifydate,String placeboxid) {
		this.boxid = boxid;
		this.boxtype = boxtype;
		this.postype = postype;
		this.isvalid = isvalid;
		this.memberid = memberid;
		this.companyid = companyid;
		this.orderid = orderid;
		this.weighid = weighid;
		this.memo = memo;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
		this.placeboxid = placeboxid;
	}

	// Property accessors

	public String getBoxid() {
		return this.boxid;
	}

	public void setBoxid(String boxid) {
		this.boxid = boxid;
	}

	public String getBoxtype() {
		return this.boxtype;
	}

	public void setBoxtype(String boxtype) {
		this.boxtype = boxtype;
	}

	public String getPostype() {
		return this.postype;
	}

	public void setPostype(String postype) {
		this.postype = postype;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
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

	public String getPlaceboxid() {
		return placeboxid;
	}

	public void setPlaceboxid(String placeboxid) {
		this.placeboxid = placeboxid;
	}
	
	

}