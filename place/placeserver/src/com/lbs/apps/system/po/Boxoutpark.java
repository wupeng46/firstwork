package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Boxoutpark entity. @author MyEclipse Persistence Tools
 */

public class Boxoutpark implements java.io.Serializable {

	// Fields

	private String boxoutparkid;
	private String boxoutplaceid;
	private String boxinplaceid;
	private String weighid;
	private String carnumber;
	private String closenumber;
	private String boxid;
	private String orderid;
	private Integer companyid;
	private String goodstype;
	private String packagetype;
	private String carriagetype;
	private String conveyance;
	private String placeboxid;
	private Timestamp inplacetime;
	private Timestamp outplacetime;
	private Timestamp outparktime;
	private String isvalid;
	private String cancelreason;
	private Timestamp canceltime;
	private Integer cancelby;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Boxoutpark() {
	}

	/** minimal constructor */
	public Boxoutpark(String boxoutparkid, String boxoutplaceid,
			String boxinplaceid) {
		this.boxoutparkid = boxoutparkid;
		this.boxoutplaceid = boxoutplaceid;
		this.boxinplaceid = boxinplaceid;
	}

	/** full constructor */
	public Boxoutpark(String boxoutparkid, String boxoutplaceid,
			String boxinplaceid, String weighid, String carnumber,
			String closenumber, String boxid, String orderid,
			Integer companyid, String goodstype, String packagetype,
			String carriagetype, String conveyance, String placeboxid,
			Timestamp inplacetime, Timestamp outplacetime,
			Timestamp outparktime, String isvalid, String cancelreason,
			Timestamp canceltime, Integer cancelby, Integer createdby,
			Integer createdorg, Timestamp createddate, Integer modifyby,
			Integer modifyorg, Timestamp modifydate) {
		this.boxoutparkid = boxoutparkid;
		this.boxoutplaceid = boxoutplaceid;
		this.boxinplaceid = boxinplaceid;
		this.weighid = weighid;
		this.carnumber = carnumber;
		this.closenumber = closenumber;
		this.boxid = boxid;
		this.orderid = orderid;
		this.companyid = companyid;
		this.goodstype = goodstype;
		this.packagetype = packagetype;
		this.carriagetype = carriagetype;
		this.conveyance = conveyance;
		this.placeboxid = placeboxid;
		this.inplacetime = inplacetime;
		this.outplacetime = outplacetime;
		this.outparktime = outparktime;
		this.isvalid = isvalid;
		this.cancelreason = cancelreason;
		this.canceltime = canceltime;
		this.cancelby = cancelby;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
	}

	// Property accessors

	public String getBoxoutparkid() {
		return this.boxoutparkid;
	}

	public void setBoxoutparkid(String boxoutparkid) {
		this.boxoutparkid = boxoutparkid;
	}

	public String getBoxoutplaceid() {
		return this.boxoutplaceid;
	}

	public void setBoxoutplaceid(String boxoutplaceid) {
		this.boxoutplaceid = boxoutplaceid;
	}

	public String getBoxinplaceid() {
		return this.boxinplaceid;
	}

	public void setBoxinplaceid(String boxinplaceid) {
		this.boxinplaceid = boxinplaceid;
	}

	public String getWeighid() {
		return this.weighid;
	}

	public void setWeighid(String weighid) {
		this.weighid = weighid;
	}

	public String getCarnumber() {
		return this.carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	public String getClosenumber() {
		return this.closenumber;
	}

	public void setClosenumber(String closenumber) {
		this.closenumber = closenumber;
	}

	public String getBoxid() {
		return this.boxid;
	}

	public void setBoxid(String boxid) {
		this.boxid = boxid;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getGoodstype() {
		return this.goodstype;
	}

	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	public String getPackagetype() {
		return this.packagetype;
	}

	public void setPackagetype(String packagetype) {
		this.packagetype = packagetype;
	}

	public String getCarriagetype() {
		return this.carriagetype;
	}

	public void setCarriagetype(String carriagetype) {
		this.carriagetype = carriagetype;
	}

	public String getConveyance() {
		return this.conveyance;
	}

	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	public String getPlaceboxid() {
		return this.placeboxid;
	}

	public void setPlaceboxid(String placeboxid) {
		this.placeboxid = placeboxid;
	}

	public Timestamp getInplacetime() {
		return this.inplacetime;
	}

	public void setInplacetime(Timestamp inplacetime) {
		this.inplacetime = inplacetime;
	}

	public Timestamp getOutplacetime() {
		return this.outplacetime;
	}

	public void setOutplacetime(Timestamp outplacetime) {
		this.outplacetime = outplacetime;
	}

	public Timestamp getOutparktime() {
		return this.outparktime;
	}

	public void setOutparktime(Timestamp outparktime) {
		this.outparktime = outparktime;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getCancelreason() {
		return this.cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}

	public Timestamp getCanceltime() {
		return this.canceltime;
	}

	public void setCanceltime(Timestamp canceltime) {
		this.canceltime = canceltime;
	}

	public Integer getCancelby() {
		return this.cancelby;
	}

	public void setCancelby(Integer cancelby) {
		this.cancelby = cancelby;
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