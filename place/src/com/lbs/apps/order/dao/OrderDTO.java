package com.lbs.apps.order.dao;

import java.io.Serializable;
import java.sql.Timestamp;


public class OrderDTO implements Serializable {
	private String orderid;
	private Integer memberid;
	private String companyid;
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
	private String havestartdate;
	private String requestfinishdate;
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
	private String losedate;
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
	private String createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private String modifydate;
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
	private String askpublishdate;
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
	private String retrievesdate;
	private String retrieveedate;
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
	private String starttime;
	private String endtime;
	private String sendcompany;
	private String retrievecompany;
	private String sendcontact;
	private String retrievecontact;
	private String boxarrivedate;
	private String packingsdate;
	private String packingedate;
	private String outdate;
	private String finishdate;
	private String finishdesc;
	private Double productprice;
	private String weighid;
	private String carnumber;
	private String closenumber;
	private String boxid;
	private String boxtype;
	private String storeagetime;
	private String boxaddr;
	private String outwarehouseid;
	private Double tare;
	private Double grossweight;
	private Double netweight;
	private String weighstatus;
	private String boxinplaceid;
	private String placeboxid;
	private Timestamp inplacetime;
	private String cancelreason;
	private Timestamp canceltime;
	private Integer cancelby;
	private String outweighid;
	private String boxoutplaceid;
	private String boxoutparkid;
	private String batchno;
	private String startdate;
	private String enddate;
	private Double weight;
	private String waybillid;
	private String expire;
	private String isplan;
	
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	public String getPackagetype() {
		return packagetype;
	}
	public void setPackagetype(String packagetype) {
		this.packagetype = packagetype;
	}
	public String getCarriagetype() {
		return carriagetype;
	}
	public void setCarriagetype(String carriagetype) {
		this.carriagetype = carriagetype;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getBoxqty() {
		return boxqty;
	}
	public void setBoxqty(Integer boxqty) {
		this.boxqty = boxqty;
	}
	public Integer getValuation() {
		return valuation;
	}
	public void setValuation(Integer valuation) {
		this.valuation = valuation;
	}
	public Double getExpectprice() {
		return expectprice;
	}
	public void setExpectprice(Double expectprice) {
		this.expectprice = expectprice;
	}
	public String getStartareaid() {
		return startareaid;
	}
	public void setStartareaid(String startareaid) {
		this.startareaid = startareaid;
	}
	public String getStartaddress() {
		return startaddress;
	}
	public void setStartaddress(String startaddress) {
		this.startaddress = startaddress;
	}
	public String getEndareaid() {
		return endareaid;
	}
	public void setEndareaid(String endareaid) {
		this.endareaid = endareaid;
	}
	public String getEndaddress() {
		return endaddress;
	}
	public void setEndaddress(String endaddress) {
		this.endaddress = endaddress;
	}
	public String getNeedcarriagesafe() {
		return needcarriagesafe;
	}
	public void setNeedcarriagesafe(String needcarriagesafe) {
		this.needcarriagesafe = needcarriagesafe;
	}
	public String getHavestartdate() {
		return havestartdate;
	}
	public void setHavestartdate(String havestartdate) {
		this.havestartdate = havestartdate;
	}
	public String getRequestfinishdate() {
		return requestfinishdate;
	}
	public void setRequestfinishdate(String requestfinishdate) {
		this.requestfinishdate = requestfinishdate;
	}
	public String getInvoicerequest() {
		return invoicerequest;
	}
	public void setInvoicerequest(String invoicerequest) {
		this.invoicerequest = invoicerequest;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getConsigner() {
		return consigner;
	}
	public void setConsigner(String consigner) {
		this.consigner = consigner;
	}
	public String getConsignermobilephone() {
		return consignermobilephone;
	}
	public void setConsignermobilephone(String consignermobilephone) {
		this.consignermobilephone = consignermobilephone;
	}
	public String getConsignertelephone() {
		return consignertelephone;
	}
	public void setConsignertelephone(String consignertelephone) {
		this.consignertelephone = consignertelephone;
	}
	public String getConsignerfax() {
		return consignerfax;
	}
	public void setConsignerfax(String consignerfax) {
		this.consignerfax = consignerfax;
	}
	public String getConsigneremail() {
		return consigneremail;
	}
	public void setConsigneremail(String consigneremail) {
		this.consigneremail = consigneremail;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceivermobilephone() {
		return receivermobilephone;
	}
	public void setReceivermobilephone(String receivermobilephone) {
		this.receivermobilephone = receivermobilephone;
	}
	public String getReceivertelephone() {
		return receivertelephone;
	}
	public void setReceivertelephone(String receivertelephone) {
		this.receivertelephone = receivertelephone;
	}
	public String getReceiverfax() {
		return receiverfax;
	}
	public void setReceiverfax(String receiverfax) {
		this.receiverfax = receiverfax;
	}
	public String getReceiveremail() {
		return receiveremail;
	}
	public void setReceiveremail(String receiveremail) {
		this.receiveremail = receiveremail;
	}
	public String getLosedate() {
		return losedate;
	}
	public void setLosedate(String losedate) {
		this.losedate = losedate;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public Double getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(Double orderamount) {
		this.orderamount = orderamount;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getRealamount() {
		return realamount;
	}
	public void setRealamount(Double realamount) {
		this.realamount = realamount;
	}
	public Double getPayamount() {
		return payamount;
	}
	public void setPayamount(Double payamount) {
		this.payamount = payamount;
	}
	public Integer getRelatedorderid() {
		return relatedorderid;
	}
	public void setRelatedorderid(Integer relatedorderid) {
		this.relatedorderid = relatedorderid;
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
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
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
	public String getModifydate() {
		return modifydate;
	}
	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}
	public String getMembersignstatus() {
		return membersignstatus;
	}
	public void setMembersignstatus(String membersignstatus) {
		this.membersignstatus = membersignstatus;
	}
	public String getCompanysignstatus() {
		return companysignstatus;
	}
	public void setCompanysignstatus(String companysignstatus) {
		this.companysignstatus = companysignstatus;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getSafeprice() {
		return safeprice;
	}
	public void setSafeprice(Double safeprice) {
		this.safeprice = safeprice;
	}
	public Double getPublishdeposit() {
		return publishdeposit;
	}
	public void setPublishdeposit(Double publishdeposit) {
		this.publishdeposit = publishdeposit;
	}
	public Double getCompanybidbond() {
		return companybidbond;
	}
	public void setCompanybidbond(Double companybidbond) {
		this.companybidbond = companybidbond;
	}
	public Double getMemberbidbond() {
		return memberbidbond;
	}
	public void setMemberbidbond(Double memberbidbond) {
		this.memberbidbond = memberbidbond;
	}
	public Double getMemberpoundage() {
		return memberpoundage;
	}
	public void setMemberpoundage(Double memberpoundage) {
		this.memberpoundage = memberpoundage;
	}
	public Double getCompanypoundage() {
		return companypoundage;
	}
	public void setCompanypoundage(Double companypoundage) {
		this.companypoundage = companypoundage;
	}
	public Double getMemberinsurance() {
		return memberinsurance;
	}
	public void setMemberinsurance(Double memberinsurance) {
		this.memberinsurance = memberinsurance;
	}
	public Double getCompanyinsurance() {
		return companyinsurance;
	}
	public void setCompanyinsurance(Double companyinsurance) {
		this.companyinsurance = companyinsurance;
	}
	public String getIfspottrans() {
		return ifspottrans;
	}
	public void setIfspottrans(String ifspottrans) {
		this.ifspottrans = ifspottrans;
	}
	public String getSafeorder() {
		return safeorder;
	}
	public void setSafeorder(String safeorder) {
		this.safeorder = safeorder;
	}
	public String getAskpublishdate() {
		return askpublishdate;
	}
	public void setAskpublishdate(String askpublishdate) {
		this.askpublishdate = askpublishdate;
	}
	public Integer getAgreeqty() {
		return agreeqty;
	}
	public void setAgreeqty(Integer agreeqty) {
		this.agreeqty = agreeqty;
	}
	public Double getAgreeprice() {
		return agreeprice;
	}
	public void setAgreeprice(Double agreeprice) {
		this.agreeprice = agreeprice;
	}
	public Double getAgreediscount() {
		return agreediscount;
	}
	public void setAgreediscount(Double agreediscount) {
		this.agreediscount = agreediscount;
	}
	public Double getAgreeamount() {
		return agreeamount;
	}
	public void setAgreeamount(Double agreeamount) {
		this.agreeamount = agreeamount;
	}
	public String getExtorderid() {
		return extorderid;
	}
	public void setExtorderid(String extorderid) {
		this.extorderid = extorderid;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSourcename() {
		return sourcename;
	}
	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}
	public String getProductlevel() {
		return productlevel;
	}
	public void setProductlevel(String productlevel) {
		this.productlevel = productlevel;
	}
	public String getYearnum() {
		return yearnum;
	}
	public void setYearnum(String yearnum) {
		this.yearnum = yearnum;
	}
	public String getRetriveshop() {
		return retriveshop;
	}
	public void setRetriveshop(String retriveshop) {
		this.retriveshop = retriveshop;
	}
	public String getRetrievesdate() {
		return retrievesdate;
	}
	public void setRetrievesdate(String retrievesdate) {
		this.retrievesdate = retrievesdate;
	}
	public String getRetrieveedate() {
		return retrieveedate;
	}
	public void setRetrieveedate(String retrieveedate) {
		this.retrieveedate = retrieveedate;
	}
	public String getConveyance() {
		return conveyance;
	}
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}
	public String getInvoicetitle() {
		return invoicetitle;
	}
	public void setInvoicetitle(String invoicetitle) {
		this.invoicetitle = invoicetitle;
	}
	public String getInvoiceno() {
		return invoiceno;
	}
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	public Double getWater() {
		return water;
	}
	public void setWater(Double water) {
		this.water = water;
	}
	public Double getImpurity() {
		return impurity;
	}
	public void setImpurity(Double impurity) {
		this.impurity = impurity;
	}
	public Double getCapacity() {
		return capacity;
	}
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	public String getSmell() {
		return smell;
	}
	public void setSmell(String smell) {
		this.smell = smell;
	}
	public Double getImperfecttotal() {
		return imperfecttotal;
	}
	public void setImperfecttotal(Double imperfecttotal) {
		this.imperfecttotal = imperfecttotal;
	}
	public Double getImperfectnum() {
		return imperfectnum;
	}
	public void setImperfectnum(Double imperfectnum) {
		this.imperfectnum = imperfectnum;
	}
	public Double getImperfectnum2() {
		return imperfectnum2;
	}
	public void setImperfectnum2(Double imperfectnum2) {
		this.imperfectnum2 = imperfectnum2;
	}
	public Double getOutqty() {
		return outqty;
	}
	public void setOutqty(Double outqty) {
		this.outqty = outqty;
	}
	public String getOrdersource() {
		return ordersource;
	}
	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
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
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getSendcompany() {
		return sendcompany;
	}
	public void setSendcompany(String sendcompany) {
		this.sendcompany = sendcompany;
	}
	public String getRetrievecompany() {
		return retrievecompany;
	}
	public void setRetrievecompany(String retrievecompany) {
		this.retrievecompany = retrievecompany;
	}
	public String getSendcontact() {
		return sendcontact;
	}
	public void setSendcontact(String sendcontact) {
		this.sendcontact = sendcontact;
	}
	public String getRetrievecontact() {
		return retrievecontact;
	}
	public void setRetrievecontact(String retrievecontact) {
		this.retrievecontact = retrievecontact;
	}
	public String getBoxarrivedate() {
		return boxarrivedate;
	}
	public void setBoxarrivedate(String boxarrivedate) {
		this.boxarrivedate = boxarrivedate;
	}
	public String getPackingsdate() {
		return packingsdate;
	}
	public void setPackingsdate(String packingsdate) {
		this.packingsdate = packingsdate;
	}
	public String getPackingedate() {
		return packingedate;
	}
	public void setPackingedate(String packingedate) {
		this.packingedate = packingedate;
	}
	public String getOutdate() {
		return outdate;
	}
	public void setOutdate(String outdate) {
		this.outdate = outdate;
	}
	public String getFinishdate() {
		return finishdate;
	}
	public void setFinishdate(String finishdate) {
		this.finishdate = finishdate;
	}
	public String getFinishdesc() {
		return finishdesc;
	}
	public void setFinishdesc(String finishdesc) {
		this.finishdesc = finishdesc;
	}
	public Double getProductprice() {
		return productprice;
	}
	public void setProductprice(Double productprice) {
		this.productprice = productprice;
	}
	public String getWeighid() {
		return weighid;
	}
	public void setWeighid(String weighid) {
		this.weighid = weighid;
	}
	public String getCarnumber() {
		return carnumber;
	}
	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}
	public String getClosenumber() {
		return closenumber;
	}
	public void setClosenumber(String closenumber) {
		this.closenumber = closenumber;
	}
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
	public String getStoreagetime() {
		return storeagetime;
	}
	public void setStoreagetime(String storeagetime) {
		this.storeagetime = storeagetime;
	}
	public String getBoxaddr() {
		return boxaddr;
	}
	public void setBoxaddr(String boxaddr) {
		this.boxaddr = boxaddr;
	}
	public String getOutwarehouseid() {
		return outwarehouseid;
	}
	public void setOutwarehouseid(String outwarehouseid) {
		this.outwarehouseid = outwarehouseid;
	}
	public Double getTare() {
		return tare;
	}
	public void setTare(Double tare) {
		this.tare = tare;
	}
	public Double getGrossweight() {
		return grossweight;
	}
	public void setGrossweight(Double grossweight) {
		this.grossweight = grossweight;
	}
	public Double getNetweight() {
		return netweight;
	}
	public void setNetweight(Double netweight) {
		this.netweight = netweight;
	}
	public String getWeighstatus() {
		return weighstatus;
	}
	public void setWeighstatus(String weighstatus) {
		this.weighstatus = weighstatus;
	}
	public String getBoxinplaceid() {
		return boxinplaceid;
	}
	public void setBoxinplaceid(String boxinplaceid) {
		this.boxinplaceid = boxinplaceid;
	}
	public String getPlaceboxid() {
		return placeboxid;
	}
	public void setPlaceboxid(String placeboxid) {
		this.placeboxid = placeboxid;
	}
	public Timestamp getInplacetime() {
		return inplacetime;
	}
	public void setInplacetime(Timestamp inplacetime) {
		this.inplacetime = inplacetime;
	}
	public String getCancelreason() {
		return cancelreason;
	}
	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}
	public Timestamp getCanceltime() {
		return canceltime;
	}
	public void setCanceltime(Timestamp canceltime) {
		this.canceltime = canceltime;
	}
	public Integer getCancelby() {
		return cancelby;
	}
	public void setCancelby(Integer cancelby) {
		this.cancelby = cancelby;
	}
	public String getOutweighid() {
		return outweighid;
	}
	public void setOutweighid(String outweighid) {
		this.outweighid = outweighid;
	}
	public String getBoxoutplaceid() {
		return boxoutplaceid;
	}
	public void setBoxoutplaceid(String boxoutplaceid) {
		this.boxoutplaceid = boxoutplaceid;
	}
	public String getBoxoutparkid() {
		return boxoutparkid;
	}
	public void setBoxoutparkid(String boxoutparkid) {
		this.boxoutparkid = boxoutparkid;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getWaybillid() {
		return waybillid;
	}
	public void setWaybillid(String waybillid) {
		this.waybillid = waybillid;
	}
	public String getExpire() {
		return expire;
	}
	public void setExpire(String expire) {
		this.expire = expire;
	}
	public String getIsplan() {
		return isplan;
	}
	public void setIsplan(String isplan) {
		this.isplan = isplan;
	}
	
	
	

}
