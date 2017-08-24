package com.lbs.apps.system.dao;

import java.io.Serializable;

public class SysuserDTO implements Serializable {
	private Integer userid;
    private String groupid;
    private String username;
    private String loginid;
    private String password;
    private String usertype;
    private Integer memberid;
	private Integer companyid;
    private String isvalid;
    private String userstatus;
    private String telephone;
    private String phonearea;
    private String phoneno;
    private String qq;
    private String email;
    private String wxopenid;
    private String address;
    private String birthday;
    private String cardtype;
    private String identifyid;
    private String sex;
    private String userlevel;
    private Integer integral;
    private String headimg;
    private String memo;
    private Integer createdby;
    private Integer createdorg;
    private String createddate;
    private Integer modifyby;
    private Integer modifyorg;
    private String modifydate;
    private String roleid;
    private String menuid;
    private String sysname;
    private String starttime;
    private String endtime;    
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
	private String regdate;
	private String enddate;
	private String orgcode;
	private String legalperson;
	private String legalcardtype;
	private String legalcardno;
	private String taxno;
	private String areaid;
	private String dutyperson;
	private String mobilephone;
	private String fax;
	private String zipcode;
	private String checkstatus;
	private Integer checkerid;
	private String checkdate;
	private String checkdesc;
	private String companylevel;
	private String imgtype;
	private String statecode;
	private String citycode;
	private String areacode;
	private String imgurl;
	private String imgcontent;
	private String oldpassword;
	private String regusertype;
	private String qualificationstatus;
	private String allowpublish;

	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
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
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPhonearea() {
		return phonearea;
	}
	public void setPhonearea(String phonearea) {
		this.phonearea = phonearea;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWxopenid() {
		return wxopenid;
	}
	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getIdentifyid() {
		return identifyid;
	}
	public void setIdentifyid(String identifyid) {
		this.identifyid = identifyid;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
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
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
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
	public String getMembertype() {
		return membertype;
	}
	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}
	public String getMemberlevel() {
		return memberlevel;
	}
	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel;
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
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanytype() {
		return companytype;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
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
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getDutyperson() {
		return dutyperson;
	}
	public void setDutyperson(String dutyperson) {
		this.dutyperson = dutyperson;
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
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getCheckdesc() {
		return checkdesc;
	}
	public void setCheckdesc(String checkdesc) {
		this.checkdesc = checkdesc;
	}
	public String getCompanylevel() {
		return companylevel;
	}
	public void setCompanylevel(String companylevel) {
		this.companylevel = companylevel;
	}
	public String getImgtype() {
		return imgtype;
	}
	public void setImgtype(String imgtype) {
		this.imgtype = imgtype;
	}
	public String getStatecode() {
		return statecode;
	}
	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getImgcontent() {
		return imgcontent;
	}
	public void setImgcontent(String imgcontent) {
		this.imgcontent = imgcontent;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getRegusertype() {
		return regusertype;
	}
	public void setRegusertype(String regusertype) {
		this.regusertype = regusertype;
	}
	public String getQualificationstatus() {
		return qualificationstatus;
	}
	public void setQualificationstatus(String qualificationstatus) {
		this.qualificationstatus = qualificationstatus;
	}
	public String getAllowpublish() {
		return allowpublish;
	}
	public void setAllowpublish(String allowpublish) {
		this.allowpublish = allowpublish;
	}
	
	

}
