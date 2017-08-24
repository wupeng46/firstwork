package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Weigh entity. @author MyEclipse Persistence Tools
 */

public class Weigh implements java.io.Serializable {

	// Fields

	private String weighid;
	private String businesstype;
	private String orderid;
	private String carnumber;
	private String closenumber;
	private Integer companyid;
	private String boxid;
	private String boxtype;
	private Timestamp storeagetime;
	private String boxaddr;
	private String outwarehouseid;
	private String sendcompany;
	private Double tare;
	private Double grossweight;
	private Double netweight;
	private String goodstype;
	private String productlevel;
	private Double water;
	private Double impurity;
	private Double capacity;
	private String smell;
	private Double imperfecttotal;
	private Double imperfectnum;
	private Double imperfectnum2;
	private String weighstatus;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;
	private String packman;
	private String receivecompany;
	private String conveyance;
	private Integer memberid;
	private String outweighid;
	private String placeboxid;
	private String istwobox;

	// Constructors

	/** default constructor */
	public Weigh() {
	}

	/** minimal constructor */
	public Weigh(String weighid) {
		this.weighid = weighid;
	}

	/** full constructor */
	public Weigh(String weighid, String businesstype, String orderid,
			String carnumber, String closenumber, Integer companyid,
			String boxid, String boxtype, Timestamp storeagetime,
			String boxaddr, String outwarehouseid, String sendcompany,
			Double tare, Double grossweight, Double netweight,
			String goodstype, String productlevel, Double water,
			Double impurity, Double capacity, String smell,
			Double imperfecttotal, Double imperfectnum, Double imperfectnum2,
			String weighstatus, Integer createdby, Integer createdorg,
			Timestamp createddate, Integer modifyby, Integer modifyorg,
			Timestamp modifydate, String packman, String receivecompany,
			String conveyance, Integer memberid, String outweighid,
			String placeboxid,String istwobox) {
		this.weighid = weighid;
		this.businesstype = businesstype;
		this.orderid = orderid;
		this.carnumber = carnumber;
		this.closenumber = closenumber;
		this.companyid = companyid;
		this.boxid = boxid;
		this.boxtype = boxtype;
		this.storeagetime = storeagetime;
		this.boxaddr = boxaddr;
		this.outwarehouseid = outwarehouseid;
		this.sendcompany = sendcompany;
		this.tare = tare;
		this.grossweight = grossweight;
		this.netweight = netweight;
		this.goodstype = goodstype;
		this.productlevel = productlevel;
		this.water = water;
		this.impurity = impurity;
		this.capacity = capacity;
		this.smell = smell;
		this.imperfecttotal = imperfecttotal;
		this.imperfectnum = imperfectnum;
		this.imperfectnum2 = imperfectnum2;
		this.weighstatus = weighstatus;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
		this.packman = packman;
		this.receivecompany = receivecompany;
		this.conveyance = conveyance;
		this.memberid = memberid;
		this.outweighid = outweighid;
		this.placeboxid = placeboxid;
		this.istwobox = istwobox;
	}

	// Property accessors

	public String getWeighid() {
		return this.weighid;
	}

	public void setWeighid(String weighid) {
		this.weighid = weighid;
	}

	public String getBusinesstype() {
		return this.businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
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

	public Integer getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

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

	public Timestamp getStoreagetime() {
		return this.storeagetime;
	}

	public void setStoreagetime(Timestamp storeagetime) {
		this.storeagetime = storeagetime;
	}

	public String getBoxaddr() {
		return this.boxaddr;
	}

	public void setBoxaddr(String boxaddr) {
		this.boxaddr = boxaddr;
	}

	public String getOutwarehouseid() {
		return this.outwarehouseid;
	}

	public void setOutwarehouseid(String outwarehouseid) {
		this.outwarehouseid = outwarehouseid;
	}

	public String getSendcompany() {
		return this.sendcompany;
	}

	public void setSendcompany(String sendcompany) {
		this.sendcompany = sendcompany;
	}

	public Double getTare() {
		return this.tare;
	}

	public void setTare(Double tare) {
		this.tare = tare;
	}

	public Double getGrossweight() {
		return this.grossweight;
	}

	public void setGrossweight(Double grossweight) {
		this.grossweight = grossweight;
	}

	public Double getNetweight() {
		return this.netweight;
	}

	public void setNetweight(Double netweight) {
		this.netweight = netweight;
	}

	public String getGoodstype() {
		return this.goodstype;
	}

	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	public String getProductlevel() {
		return this.productlevel;
	}

	public void setProductlevel(String productlevel) {
		this.productlevel = productlevel;
	}

	public Double getWater() {
		return this.water;
	}

	public void setWater(Double water) {
		this.water = water;
	}

	public Double getImpurity() {
		return this.impurity;
	}

	public void setImpurity(Double impurity) {
		this.impurity = impurity;
	}

	public Double getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public String getSmell() {
		return this.smell;
	}

	public void setSmell(String smell) {
		this.smell = smell;
	}

	public Double getImperfecttotal() {
		return this.imperfecttotal;
	}

	public void setImperfecttotal(Double imperfecttotal) {
		this.imperfecttotal = imperfecttotal;
	}

	public Double getImperfectnum() {
		return this.imperfectnum;
	}

	public void setImperfectnum(Double imperfectnum) {
		this.imperfectnum = imperfectnum;
	}

	public Double getImperfectnum2() {
		return this.imperfectnum2;
	}

	public void setImperfectnum2(Double imperfectnum2) {
		this.imperfectnum2 = imperfectnum2;
	}

	public String getWeighstatus() {
		return this.weighstatus;
	}

	public void setWeighstatus(String weighstatus) {
		this.weighstatus = weighstatus;
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

	public String getPackman() {
		return this.packman;
	}

	public void setPackman(String packman) {
		this.packman = packman;
	}

	public String getReceivecompany() {
		return this.receivecompany;
	}

	public void setReceivecompany(String receivecompany) {
		this.receivecompany = receivecompany;
	}

	public String getConveyance() {
		return this.conveyance;
	}

	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	public Integer getMemberid() {
		return this.memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public String getOutweighid() {
		return this.outweighid;
	}

	public void setOutweighid(String outweighid) {
		this.outweighid = outweighid;
	}

	public String getPlaceboxid() {
		return placeboxid;
	}

	public void setPlaceboxid(String placeboxid) {
		this.placeboxid = placeboxid;
	}

	public String getIstwobox() {
		return istwobox;
	}

	public void setIstwobox(String istwobox) {
		this.istwobox = istwobox;
	}
	
	

}