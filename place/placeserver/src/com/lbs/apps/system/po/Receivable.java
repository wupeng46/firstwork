package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Receivable entity. @author MyEclipse Persistence Tools
 */

public class Receivable implements java.io.Serializable {

	// Fields

	private String receivableid;
	private String orderid;
	private String weighid;
	private Integer memberid;
	private Integer companyid;
	private String goodstype;
	private String packagetype;
	private String carriagetype;
	private String conveyance;
	private String businesstype;
	private String carnumber;
	private String closenumber;
	private String boxid;
	private Double netweight;
	private Double price;
	private Double amount;
	private String receivablestatus;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;
	private String isvalid;
	private String placeboxid;

	// Constructors

	/** default constructor */
	public Receivable() {
	}

	/** minimal constructor */
	public Receivable(String receivableid) {
		this.receivableid = receivableid;
	}

	/** full constructor */
	public Receivable(String receivableid, String orderid, String weighid,
			Integer memberid, Integer companyid, String goodstype,
			String packagetype, String carriagetype, String conveyance,
			String businesstype, String carnumber, String closenumber,
			String boxid, Double netweight, Double price, Double amount,
			String receivablestatus, Integer createdby, Integer createdorg,
			Timestamp createddate, Integer modifyby, Integer modifyorg,
			Timestamp modifydate,String isvalid,String placeboxid) {
		this.receivableid = receivableid;
		this.orderid = orderid;
		this.weighid = weighid;
		this.memberid = memberid;
		this.companyid = companyid;
		this.goodstype = goodstype;
		this.packagetype = packagetype;
		this.carriagetype = carriagetype;
		this.conveyance = conveyance;
		this.businesstype = businesstype;
		this.carnumber = carnumber;
		this.closenumber = closenumber;
		this.boxid = boxid;
		this.netweight = netweight;
		this.price = price;
		this.amount = amount;
		this.receivablestatus = receivablestatus;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
		this.isvalid = isvalid;
		this.placeboxid = placeboxid;
	}

	// Property accessors

	public String getReceivableid() {
		return this.receivableid;
	}

	public void setReceivableid(String receivableid) {
		this.receivableid = receivableid;
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

	public String getBusinesstype() {
		return this.businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
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

	public Double getNetweight() {
		return this.netweight;
	}

	public void setNetweight(Double netweight) {
		this.netweight = netweight;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getReceivablestatus() {
		return this.receivablestatus;
	}

	public void setReceivablestatus(String receivablestatus) {
		this.receivablestatus = receivablestatus;
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

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getPlaceboxid() {
		return placeboxid;
	}

	public void setPlaceboxid(String placeboxid) {
		this.placeboxid = placeboxid;
	}
	
	

}