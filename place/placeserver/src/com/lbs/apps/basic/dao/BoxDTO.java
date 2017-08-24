package com.lbs.apps.basic.dao;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoxDTO implements Serializable {
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
	public String getBoxid() {
		return boxid;
	}
	public void setBoxid(String boxid) {
		this.boxid = boxid;
	}
	public String getBoxtype() {
		return boxtype;
	}
	public void setBoxtype(String boxtype) {
		this.boxtype = boxtype;
	}
	public String getPostype() {
		return postype;
	}
	public void setPostype(String postype) {
		this.postype = postype;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getWeighid() {
		return weighid;
	}
	public void setWeighid(String weighid) {
		this.weighid = weighid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getCreatedby() {
		return createdby;
	}
	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}
	public Integer getCreatedorg() {
		return createdorg;
	}
	public void setCreatedorg(Integer createdorg) {
		this.createdorg = createdorg;
	}
	public Timestamp getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}
	public Integer getModifyby() {
		return modifyby;
	}
	public void setModifyby(Integer modifyby) {
		this.modifyby = modifyby;
	}
	public Integer getModifyorg() {
		return modifyorg;
	}
	public void setModifyorg(Integer modifyorg) {
		this.modifyorg = modifyorg;
	}
	public Timestamp getModifydate() {
		return modifydate;
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
