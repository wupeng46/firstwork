package com.lbs.apps.basic.dao;

import java.io.Serializable;
import java.sql.Timestamp;

public class CompanyDTO implements Serializable {
	private Integer companyid;
	private String companyname;
	private String companylevel;
	private String companytype;
	private Timestamp regdate;
	private Timestamp enddate;
	private String orgcode;
	private String legalperson;
	private String legalcardtype;
	private String legalcardno;
	private String taxno;
	private String bankid;
	private String bankname;
	private String accountname;
	private String bankaccount;
	private String mailaddress;
	private String mailreceiver;
	private String mailtelephone;
	private String areaid;
	private String address;
	private String dutyperson;
	private String telephone;
	private String mobilephone;
	private String fax;
	private String email;
	private String zipcode;
	private String checkstatus;
	private Integer checkerid;
	private Timestamp checkdate;
	private String checkdesc;
	private Double balance;
	private Double frozenamount;
	private String balanceaccount;
	private String balancephone;
	private String allowpublish;
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;
	private String isvalid;
	private Integer memberid;
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanylevel() {
		return companylevel;
	}
	public void setCompanylevel(String companylevel) {
		this.companylevel = companylevel;
	}
	public String getCompanytype() {
		return companytype;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public Timestamp getEnddate() {
		return enddate;
	}
	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getLegalperson() {
		return legalperson;
	}
	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}
	public String getLegalcardtype() {
		return legalcardtype;
	}
	public void setLegalcardtype(String legalcardtype) {
		this.legalcardtype = legalcardtype;
	}
	public String getLegalcardno() {
		return legalcardno;
	}
	public void setLegalcardno(String legalcardno) {
		this.legalcardno = legalcardno;
	}
	public String getTaxno() {
		return taxno;
	}
	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getBankaccount() {
		return bankaccount;
	}
	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}
	public String getMailaddress() {
		return mailaddress;
	}
	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}
	public String getMailreceiver() {
		return mailreceiver;
	}
	public void setMailreceiver(String mailreceiver) {
		this.mailreceiver = mailreceiver;
	}
	public String getMailtelephone() {
		return mailtelephone;
	}
	public void setMailtelephone(String mailtelephone) {
		this.mailtelephone = mailtelephone;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDutyperson() {
		return dutyperson;
	}
	public void setDutyperson(String dutyperson) {
		this.dutyperson = dutyperson;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	public Integer getCheckerid() {
		return checkerid;
	}
	public void setCheckerid(Integer checkerid) {
		this.checkerid = checkerid;
	}
	public Timestamp getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
	}
	public String getCheckdesc() {
		return checkdesc;
	}
	public void setCheckdesc(String checkdesc) {
		this.checkdesc = checkdesc;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getFrozenamount() {
		return frozenamount;
	}
	public void setFrozenamount(Double frozenamount) {
		this.frozenamount = frozenamount;
	}
	public String getBalanceaccount() {
		return balanceaccount;
	}
	public void setBalanceaccount(String balanceaccount) {
		this.balanceaccount = balanceaccount;
	}
	public String getBalancephone() {
		return balancephone;
	}
	public void setBalancephone(String balancephone) {
		this.balancephone = balancephone;
	}
	public String getAllowpublish() {
		return allowpublish;
	}
	public void setAllowpublish(String allowpublish) {
		this.allowpublish = allowpublish;
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
	
	


}
