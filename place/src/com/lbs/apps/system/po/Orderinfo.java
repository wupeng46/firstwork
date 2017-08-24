package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Orderinfo entity. @author MyEclipse Persistence Tools
 */

public class Orderinfo implements java.io.Serializable {

	// Fields

	private String orderid;
	private Integer memberid;
	private String ordertype;
	private String goodstype;
	private String packagetype;
	private String carriagetype;
	private Integer qty;
	private Integer boxqty;
	private Integer valuation;
	private Double expectprice;
	private String startareaid;
	private String startaddress;
	private String endareaid;
	private String endaddress;
	private String needcarriagesafe;
	private Timestamp havestartdate;
	private Timestamp requestfinishdate;
	private String invoicerequest;
	private String businesstype;
	private String consigner;
	private String consignermobilephone;
	private String consignertelephone;
	private String consignerfax;
	private String consigneremail;
	private String receiver;
	private String receivermobilephone;
	private String receivertelephone;
	private String receiverfax;
	private String receiveremail;
	private Timestamp losedate;
	private String orderstatus;
	private Double orderamount;
	private Double discount;
	private Double realamount;
	private Double payamount;
	private Integer relatedorderid;
	private String isvalid;
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;
	private String membersignstatus;
	private String companysignstatus;
	private Double price;
	private Double safeprice;
	private Double publishdeposit;
	private Double companybidbond;
	private Double memberbidbond;
	private Double memberpoundage;
	private Double companypoundage;
	private Double memberinsurance;
	private Double companyinsurance;
	private String ifspottrans;
	private String safeorder;
	private Timestamp askpublishdate;
	private Integer agreeqty;
	private Double agreeprice;
	private Double agreediscount;
	private Double agreeamount;
	private String extorderid;
	private String origin;
	private String brand;
	private String sourcename;
	private String productlevel;
	private String yearnum;
	private String retriveshop;
	private Timestamp retrievesdate;
	private Timestamp retrieveedate;
	private String conveyance;
	private String invoicetitle;
	private String invoiceno;
	private Double water;
	private Double impurity;
	private Double capacity;
	private String smell;
	private Double imperfecttotal;
	private Double imperfectnum;
	private Double imperfectnum2;
	private Double outqty;
	private String ordersource;
	private String sendcompany;
	private String retrievecompany;
	private String sendcontact;
	private String retrievecontact;
	private Double productprice;
	private String isplan;

	// Constructors

	/** default constructor */
	public Orderinfo() {
	}

	/** minimal constructor */
	public Orderinfo(String orderid, Integer memberid) {
		this.orderid = orderid;
		this.memberid = memberid;
	}

	/** full constructor */
	public Orderinfo(String orderid, Integer memberid, String ordertype,
			String goodstype, String packagetype, String carriagetype,
			Integer qty, Integer boxqty, Integer valuation, Double expectprice,
			String startareaid, String startaddress, String endareaid,
			String endaddress, String needcarriagesafe,
			Timestamp havestartdate, Timestamp requestfinishdate,
			String invoicerequest, String businesstype, String consigner,
			String consignermobilephone, String consignertelephone,
			String consignerfax, String consigneremail, String receiver,
			String receivermobilephone, String receivertelephone,
			String receiverfax, String receiveremail, Timestamp losedate,
			String orderstatus, Double orderamount, Double discount,
			Double realamount, Double payamount, Integer relatedorderid,
			String isvalid, String memo, Integer createdby, Integer createdorg,
			Timestamp createddate, Integer modifyby, Integer modifyorg,
			Timestamp modifydate, String membersignstatus,
			String companysignstatus, Double price, Double safeprice,
			Double publishdeposit, Double companybidbond, Double memberbidbond,
			Double memberpoundage, Double companypoundage,
			Double memberinsurance, Double companyinsurance,
			String ifspottrans, String safeorder, Timestamp askpublishdate,
			Integer agreeqty, Double agreeprice, Double agreediscount,
			Double agreeamount, String extorderid, String origin, String brand,
			String sourcename, String productlevel, String yearnum,
			String retriveshop, Timestamp retrievesdate,
			Timestamp retrieveedate, String conveyance, String invoicetitle,
			String invoiceno, Double water, Double impurity, Double capacity,
			String smell, Double imperfecttotal, Double imperfectnum,
			Double imperfectnum2, Double outqty, String ordersource,
			String sendcompany, String retrievecompany, String sendcontact,
			String retrievecontact, Double productprice,String isplan) {
		this.orderid = orderid;
		this.memberid = memberid;
		this.ordertype = ordertype;
		this.goodstype = goodstype;
		this.packagetype = packagetype;
		this.carriagetype = carriagetype;
		this.qty = qty;
		this.boxqty = boxqty;
		this.valuation = valuation;
		this.expectprice = expectprice;
		this.startareaid = startareaid;
		this.startaddress = startaddress;
		this.endareaid = endareaid;
		this.endaddress = endaddress;
		this.needcarriagesafe = needcarriagesafe;
		this.havestartdate = havestartdate;
		this.requestfinishdate = requestfinishdate;
		this.invoicerequest = invoicerequest;
		this.businesstype = businesstype;
		this.consigner = consigner;
		this.consignermobilephone = consignermobilephone;
		this.consignertelephone = consignertelephone;
		this.consignerfax = consignerfax;
		this.consigneremail = consigneremail;
		this.receiver = receiver;
		this.receivermobilephone = receivermobilephone;
		this.receivertelephone = receivertelephone;
		this.receiverfax = receiverfax;
		this.receiveremail = receiveremail;
		this.losedate = losedate;
		this.orderstatus = orderstatus;
		this.orderamount = orderamount;
		this.discount = discount;
		this.realamount = realamount;
		this.payamount = payamount;
		this.relatedorderid = relatedorderid;
		this.isvalid = isvalid;
		this.memo = memo;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
		this.membersignstatus = membersignstatus;
		this.companysignstatus = companysignstatus;
		this.price = price;
		this.safeprice = safeprice;
		this.publishdeposit = publishdeposit;
		this.companybidbond = companybidbond;
		this.memberbidbond = memberbidbond;
		this.memberpoundage = memberpoundage;
		this.companypoundage = companypoundage;
		this.memberinsurance = memberinsurance;
		this.companyinsurance = companyinsurance;
		this.ifspottrans = ifspottrans;
		this.safeorder = safeorder;
		this.askpublishdate = askpublishdate;
		this.agreeqty = agreeqty;
		this.agreeprice = agreeprice;
		this.agreediscount = agreediscount;
		this.agreeamount = agreeamount;
		this.extorderid = extorderid;
		this.origin = origin;
		this.brand = brand;
		this.sourcename = sourcename;
		this.productlevel = productlevel;
		this.yearnum = yearnum;
		this.retriveshop = retriveshop;
		this.retrievesdate = retrievesdate;
		this.retrieveedate = retrieveedate;
		this.conveyance = conveyance;
		this.invoicetitle = invoicetitle;
		this.invoiceno = invoiceno;
		this.water = water;
		this.impurity = impurity;
		this.capacity = capacity;
		this.smell = smell;
		this.imperfecttotal = imperfecttotal;
		this.imperfectnum = imperfectnum;
		this.imperfectnum2 = imperfectnum2;
		this.outqty = outqty;
		this.ordersource = ordersource;
		this.sendcompany = sendcompany;
		this.retrievecompany = retrievecompany;
		this.sendcontact = sendcontact;
		this.retrievecontact = retrievecontact;
		this.productprice = productprice;
		this.isplan = isplan;
	}

	// Property accessors

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getMemberid() {
		return this.memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public String getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
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

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getBoxqty() {
		return this.boxqty;
	}

	public void setBoxqty(Integer boxqty) {
		this.boxqty = boxqty;
	}

	public Integer getValuation() {
		return this.valuation;
	}

	public void setValuation(Integer valuation) {
		this.valuation = valuation;
	}

	public Double getExpectprice() {
		return this.expectprice;
	}

	public void setExpectprice(Double expectprice) {
		this.expectprice = expectprice;
	}

	public String getStartareaid() {
		return this.startareaid;
	}

	public void setStartareaid(String startareaid) {
		this.startareaid = startareaid;
	}

	public String getStartaddress() {
		return this.startaddress;
	}

	public void setStartaddress(String startaddress) {
		this.startaddress = startaddress;
	}

	public String getEndareaid() {
		return this.endareaid;
	}

	public void setEndareaid(String endareaid) {
		this.endareaid = endareaid;
	}

	public String getEndaddress() {
		return this.endaddress;
	}

	public void setEndaddress(String endaddress) {
		this.endaddress = endaddress;
	}

	public String getNeedcarriagesafe() {
		return this.needcarriagesafe;
	}

	public void setNeedcarriagesafe(String needcarriagesafe) {
		this.needcarriagesafe = needcarriagesafe;
	}

	public Timestamp getHavestartdate() {
		return this.havestartdate;
	}

	public void setHavestartdate(Timestamp havestartdate) {
		this.havestartdate = havestartdate;
	}

	public Timestamp getRequestfinishdate() {
		return this.requestfinishdate;
	}

	public void setRequestfinishdate(Timestamp requestfinishdate) {
		this.requestfinishdate = requestfinishdate;
	}

	public String getInvoicerequest() {
		return this.invoicerequest;
	}

	public void setInvoicerequest(String invoicerequest) {
		this.invoicerequest = invoicerequest;
	}

	public String getBusinesstype() {
		return this.businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getConsigner() {
		return this.consigner;
	}

	public void setConsigner(String consigner) {
		this.consigner = consigner;
	}

	public String getConsignermobilephone() {
		return this.consignermobilephone;
	}

	public void setConsignermobilephone(String consignermobilephone) {
		this.consignermobilephone = consignermobilephone;
	}

	public String getConsignertelephone() {
		return this.consignertelephone;
	}

	public void setConsignertelephone(String consignertelephone) {
		this.consignertelephone = consignertelephone;
	}

	public String getConsignerfax() {
		return this.consignerfax;
	}

	public void setConsignerfax(String consignerfax) {
		this.consignerfax = consignerfax;
	}

	public String getConsigneremail() {
		return this.consigneremail;
	}

	public void setConsigneremail(String consigneremail) {
		this.consigneremail = consigneremail;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceivermobilephone() {
		return this.receivermobilephone;
	}

	public void setReceivermobilephone(String receivermobilephone) {
		this.receivermobilephone = receivermobilephone;
	}

	public String getReceivertelephone() {
		return this.receivertelephone;
	}

	public void setReceivertelephone(String receivertelephone) {
		this.receivertelephone = receivertelephone;
	}

	public String getReceiverfax() {
		return this.receiverfax;
	}

	public void setReceiverfax(String receiverfax) {
		this.receiverfax = receiverfax;
	}

	public String getReceiveremail() {
		return this.receiveremail;
	}

	public void setReceiveremail(String receiveremail) {
		this.receiveremail = receiveremail;
	}

	public Timestamp getLosedate() {
		return this.losedate;
	}

	public void setLosedate(Timestamp losedate) {
		this.losedate = losedate;
	}

	public String getOrderstatus() {
		return this.orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public Double getOrderamount() {
		return this.orderamount;
	}

	public void setOrderamount(Double orderamount) {
		this.orderamount = orderamount;
	}

	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getRealamount() {
		return this.realamount;
	}

	public void setRealamount(Double realamount) {
		this.realamount = realamount;
	}

	public Double getPayamount() {
		return this.payamount;
	}

	public void setPayamount(Double payamount) {
		this.payamount = payamount;
	}

	public Integer getRelatedorderid() {
		return this.relatedorderid;
	}

	public void setRelatedorderid(Integer relatedorderid) {
		this.relatedorderid = relatedorderid;
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

	public String getMembersignstatus() {
		return this.membersignstatus;
	}

	public void setMembersignstatus(String membersignstatus) {
		this.membersignstatus = membersignstatus;
	}

	public String getCompanysignstatus() {
		return this.companysignstatus;
	}

	public void setCompanysignstatus(String companysignstatus) {
		this.companysignstatus = companysignstatus;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSafeprice() {
		return this.safeprice;
	}

	public void setSafeprice(Double safeprice) {
		this.safeprice = safeprice;
	}

	public Double getPublishdeposit() {
		return this.publishdeposit;
	}

	public void setPublishdeposit(Double publishdeposit) {
		this.publishdeposit = publishdeposit;
	}

	public Double getCompanybidbond() {
		return this.companybidbond;
	}

	public void setCompanybidbond(Double companybidbond) {
		this.companybidbond = companybidbond;
	}

	public Double getMemberbidbond() {
		return this.memberbidbond;
	}

	public void setMemberbidbond(Double memberbidbond) {
		this.memberbidbond = memberbidbond;
	}

	public Double getMemberpoundage() {
		return this.memberpoundage;
	}

	public void setMemberpoundage(Double memberpoundage) {
		this.memberpoundage = memberpoundage;
	}

	public Double getCompanypoundage() {
		return this.companypoundage;
	}

	public void setCompanypoundage(Double companypoundage) {
		this.companypoundage = companypoundage;
	}

	public Double getMemberinsurance() {
		return this.memberinsurance;
	}

	public void setMemberinsurance(Double memberinsurance) {
		this.memberinsurance = memberinsurance;
	}

	public Double getCompanyinsurance() {
		return this.companyinsurance;
	}

	public void setCompanyinsurance(Double companyinsurance) {
		this.companyinsurance = companyinsurance;
	}

	public String getIfspottrans() {
		return this.ifspottrans;
	}

	public void setIfspottrans(String ifspottrans) {
		this.ifspottrans = ifspottrans;
	}

	public String getSafeorder() {
		return this.safeorder;
	}

	public void setSafeorder(String safeorder) {
		this.safeorder = safeorder;
	}

	public Timestamp getAskpublishdate() {
		return this.askpublishdate;
	}

	public void setAskpublishdate(Timestamp askpublishdate) {
		this.askpublishdate = askpublishdate;
	}

	public Integer getAgreeqty() {
		return this.agreeqty;
	}

	public void setAgreeqty(Integer agreeqty) {
		this.agreeqty = agreeqty;
	}

	public Double getAgreeprice() {
		return this.agreeprice;
	}

	public void setAgreeprice(Double agreeprice) {
		this.agreeprice = agreeprice;
	}

	public Double getAgreediscount() {
		return this.agreediscount;
	}

	public void setAgreediscount(Double agreediscount) {
		this.agreediscount = agreediscount;
	}

	public Double getAgreeamount() {
		return this.agreeamount;
	}

	public void setAgreeamount(Double agreeamount) {
		this.agreeamount = agreeamount;
	}

	public String getExtorderid() {
		return this.extorderid;
	}

	public void setExtorderid(String extorderid) {
		this.extorderid = extorderid;
	}

	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSourcename() {
		return this.sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

	public String getProductlevel() {
		return this.productlevel;
	}

	public void setProductlevel(String productlevel) {
		this.productlevel = productlevel;
	}

	public String getYearnum() {
		return this.yearnum;
	}

	public void setYearnum(String yearnum) {
		this.yearnum = yearnum;
	}

	public String getRetriveshop() {
		return this.retriveshop;
	}

	public void setRetriveshop(String retriveshop) {
		this.retriveshop = retriveshop;
	}

	public Timestamp getRetrievesdate() {
		return this.retrievesdate;
	}

	public void setRetrievesdate(Timestamp retrievesdate) {
		this.retrievesdate = retrievesdate;
	}

	public Timestamp getRetrieveedate() {
		return this.retrieveedate;
	}

	public void setRetrieveedate(Timestamp retrieveedate) {
		this.retrieveedate = retrieveedate;
	}

	public String getConveyance() {
		return this.conveyance;
	}

	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	public String getInvoicetitle() {
		return this.invoicetitle;
	}

	public void setInvoicetitle(String invoicetitle) {
		this.invoicetitle = invoicetitle;
	}

	public String getInvoiceno() {
		return this.invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
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

	public Double getOutqty() {
		return this.outqty;
	}

	public void setOutqty(Double outqty) {
		this.outqty = outqty;
	}

	public String getOrdersource() {
		return this.ordersource;
	}

	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}

	public String getSendcompany() {
		return this.sendcompany;
	}

	public void setSendcompany(String sendcompany) {
		this.sendcompany = sendcompany;
	}

	public String getRetrievecompany() {
		return this.retrievecompany;
	}

	public void setRetrievecompany(String retrievecompany) {
		this.retrievecompany = retrievecompany;
	}

	public String getSendcontact() {
		return this.sendcontact;
	}

	public void setSendcontact(String sendcontact) {
		this.sendcontact = sendcontact;
	}

	public String getRetrievecontact() {
		return this.retrievecontact;
	}

	public void setRetrievecontact(String retrievecontact) {
		this.retrievecontact = retrievecontact;
	}

	public Double getProductprice() {
		return this.productprice;
	}

	public void setProductprice(Double productprice) {
		this.productprice = productprice;
	}

	public String getIsplan() {
		return isplan;
	}

	public void setIsplan(String isplan) {
		this.isplan = isplan;
	}
	
	

}