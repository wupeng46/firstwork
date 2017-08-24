package com.lbs.apps.basic.dao;

import java.io.Serializable;
import java.sql.Timestamp;

public class PlaceDTO implements Serializable {
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
	private String placeboxid;
	private Short xnum;
	private Short ynum;
	private String placeboxstatus;
	private String orderid;
	private String weighid;
	private String boxid;
	private Integer memberid;
	private Integer companyid;
	private Timestamp statusstartdate;
	private Timestamp statusenddate;
	private String starttime;
	private String endtime;
	public String getPlaceid() {
		return placeid;
	}
	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Short getXmax() {
		return xmax;
	}
	public void setXmax(Short xmax) {
		this.xmax = xmax;
	}
	public Short getYmax() {
		return ymax;
	}
	public void setYmax(Short ymax) {
		this.ymax = ymax;
	}
	public Short getLevelnum() {
		return levelnum;
	}
	public void setLevelnum(Short levelnum) {
		this.levelnum = levelnum;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
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
	public Short getXnum() {
		return xnum;
	}
	public void setXnum(Short xnum) {
		this.xnum = xnum;
	}
	public Short getYnum() {
		return ynum;
	}
	public void setYnum(Short ynum) {
		this.ynum = ynum;
	}
	public String getPlaceboxstatus() {
		return placeboxstatus;
	}
	public void setPlaceboxstatus(String placeboxstatus) {
		this.placeboxstatus = placeboxstatus;
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
	public String getBoxid() {
		return boxid;
	}
	public void setBoxid(String boxid) {
		this.boxid = boxid;
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
	public Timestamp getStatusstartdate() {
		return statusstartdate;
	}
	public void setStatusstartdate(Timestamp statusstartdate) {
		this.statusstartdate = statusstartdate;
	}
	public Timestamp getStatusenddate() {
		return statusenddate;
	}
	public void setStatusenddate(Timestamp statusenddate) {
		this.statusenddate = statusenddate;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
		
	

}
