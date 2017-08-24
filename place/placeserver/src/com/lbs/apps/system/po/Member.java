package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */

public class Member implements java.io.Serializable {

	// Fields

	private Integer memberid;
	private String membertype;
	private String memberlevel;
	private String bankid;
	private String bankname;
	private String accountname;
	private String bankaccount;
	private String mailaddress;
	private String mailreceiver;
	private String mailtelephone;
	private String companyname;
	private String companytype;
	private Timestamp regdate;
	private Timestamp enddate;
	private String orgcode;
	private String legalperson;
	private String legalcardtype;
	private String legalcardno;
	private String taxno;
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
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;
	private Double balance;
	private Double frozenamount;
	private String balanceaccount;
	private String balancephone;

	// Constructors

	/** default constructor */
	public Member() {
	}

	/** minimal constructor */
	public Member(Integer memberid) {
		this.memberid = memberid;
	}

	/** full constructor */
	public Member(Integer memberid, String membertype, String memberlevel,
			String bankid, String bankname, String accountname,
			String bankaccount, String mailaddress, String mailreceiver,
			String mailtelephone, String companyname, String companytype,
			Timestamp regdate, Timestamp enddate, String orgcode,
			String legalperson, String legalcardtype, String legalcardno,
			String taxno, String areaid, String address, String dutyperson,
			String telephone, String mobilephone, String fax, String email,
			String zipcode, String checkstatus, Integer checkerid,
			Timestamp checkdate, String checkdesc, String memo,
			Integer createdby, Integer createdorg, Timestamp createddate,
			Integer modifyby, Integer modifyorg, Timestamp modifydate,
			Double balance, Double frozenamount, String balanceaccount,
			String balancephone) {
		this.memberid = memberid;
		this.membertype = membertype;
		this.memberlevel = memberlevel;
		this.bankid = bankid;
		this.bankname = bankname;
		this.accountname = accountname;
		this.bankaccount = bankaccount;
		this.mailaddress = mailaddress;
		this.mailreceiver = mailreceiver;
		this.mailtelephone = mailtelephone;
		this.companyname = companyname;
		this.companytype = companytype;
		this.regdate = regdate;
		this.enddate = enddate;
		this.orgcode = orgcode;
		this.legalperson = legalperson;
		this.legalcardtype = legalcardtype;
		this.legalcardno = legalcardno;
		this.taxno = taxno;
		this.areaid = areaid;
		this.address = address;
		this.dutyperson = dutyperson;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.fax = fax;
		this.email = email;
		this.zipcode = zipcode;
		this.checkstatus = checkstatus;
		this.checkerid = checkerid;
		this.checkdate = checkdate;
		this.checkdesc = checkdesc;
		this.memo = memo;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
		this.balance = balance;
		this.frozenamount = frozenamount;
		this.balanceaccount = balanceaccount;
		this.balancephone = balancephone;
	}

	// Property accessors

	public Integer getMemberid() {
		return this.memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public String getMembertype() {
		return this.membertype;
	}

	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}

	public String getMemberlevel() {
		return this.memberlevel;
	}

	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel;
	}

	public String getBankid() {
		return this.bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAccountname() {
		return this.accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getBankaccount() {
		return this.bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getMailaddress() {
		return this.mailaddress;
	}

	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}

	public String getMailreceiver() {
		return this.mailreceiver;
	}

	public void setMailreceiver(String mailreceiver) {
		this.mailreceiver = mailreceiver;
	}

	public String getMailtelephone() {
		return this.mailtelephone;
	}

	public void setMailtelephone(String mailtelephone) {
		this.mailtelephone = mailtelephone;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanytype() {
		return this.companytype;
	}

	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

	public Timestamp getRegdate() {
		return this.regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	public Timestamp getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public String getOrgcode() {
		return this.orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getLegalperson() {
		return this.legalperson;
	}

	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}

	public String getLegalcardtype() {
		return this.legalcardtype;
	}

	public void setLegalcardtype(String legalcardtype) {
		this.legalcardtype = legalcardtype;
	}

	public String getLegalcardno() {
		return this.legalcardno;
	}

	public void setLegalcardno(String legalcardno) {
		this.legalcardno = legalcardno;
	}

	public String getTaxno() {
		return this.taxno;
	}

	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}

	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDutyperson() {
		return this.dutyperson;
	}

	public void setDutyperson(String dutyperson) {
		this.dutyperson = dutyperson;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCheckstatus() {
		return this.checkstatus;
	}

	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}

	public Integer getCheckerid() {
		return this.checkerid;
	}

	public void setCheckerid(Integer checkerid) {
		this.checkerid = checkerid;
	}

	public Timestamp getCheckdate() {
		return this.checkdate;
	}

	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
	}

	public String getCheckdesc() {
		return this.checkdesc;
	}

	public void setCheckdesc(String checkdesc) {
		this.checkdesc = checkdesc;
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

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getFrozenamount() {
		return this.frozenamount;
	}

	public void setFrozenamount(Double frozenamount) {
		this.frozenamount = frozenamount;
	}

	public String getBalanceaccount() {
		return this.balanceaccount;
	}

	public void setBalanceaccount(String balanceaccount) {
		this.balanceaccount = balanceaccount;
	}

	public String getBalancephone() {
		return this.balancephone;
	}

	public void setBalancephone(String balancephone) {
		this.balancephone = balancephone;
	}

}