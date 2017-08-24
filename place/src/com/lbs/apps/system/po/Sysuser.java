package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Sysuser entity. @author MyEclipse Persistence Tools
 */

public class Sysuser implements java.io.Serializable {

	// Fields

	private Integer userid;
	private String username;
	private String loginid;
	private String password;
	private Integer companyid;
	private Integer memberid;
	private Integer groupid;
	private String usertype;
	private String isvalid;
	private String userstatus;
	private String telephone;
	private String phonearea;
	private String phoneno;
	private String qq;
	private String email;
	private String wxopenid;
	private String address;
	private Timestamp birthday;
	private String cardtype;
	private String identifyid;
	private String sex;
	private String userlevel;
	private String headimg;
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;
	private String qualificationstatus;
	private String regusertype;

	// Constructors

	/** default constructor */
	public Sysuser() {
	}

	/** minimal constructor */
	public Sysuser(Integer userid) {
		this.userid = userid;
	}

	/** full constructor */
	public Sysuser(Integer userid, String username, String loginid,
			String password, Integer companyid, Integer memberid,
			Integer groupid, String usertype, String isvalid,
			String userstatus, String telephone, String phonearea,
			String phoneno, String qq, String email, String wxopenid,
			String address, Timestamp birthday, String cardtype,
			String identifyid, String sex, String userlevel, String headimg,
			String memo, Integer createdby, Integer createdorg,
			Timestamp createddate, Integer modifyby, Integer modifyorg,
			Timestamp modifydate,String qualificationstatus,String regusertype) {
		this.userid = userid;
		this.username = username;
		this.loginid = loginid;
		this.password = password;
		this.companyid = companyid;
		this.memberid = memberid;
		this.groupid = groupid;
		this.usertype = usertype;
		this.isvalid = isvalid;
		this.userstatus = userstatus;
		this.telephone = telephone;
		this.phonearea = phonearea;
		this.phoneno = phoneno;
		this.qq = qq;
		this.email = email;
		this.wxopenid = wxopenid;
		this.address = address;
		this.birthday = birthday;
		this.cardtype = cardtype;
		this.identifyid = identifyid;
		this.sex = sex;
		this.userlevel = userlevel;
		this.headimg = headimg;
		this.memo = memo;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
		this.qualificationstatus = qualificationstatus;
		this.regusertype = regusertype;
	}

	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public Integer getMemberid() {
		return this.memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getUserstatus() {
		return this.userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPhonearea() {
		return this.phonearea;
	}

	public void setPhonearea(String phonearea) {
		this.phonearea = phonearea;
	}

	public String getPhoneno() {
		return this.phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWxopenid() {
		return this.wxopenid;
	}

	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getIdentifyid() {
		return this.identifyid;
	}

	public void setIdentifyid(String identifyid) {
		this.identifyid = identifyid;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserlevel() {
		return this.userlevel;
	}

	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}

	public String getHeadimg() {
		return this.headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
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

	public String getQualificationstatus() {
		return qualificationstatus;
	}

	public void setQualificationstatus(String qualificationstatus) {
		this.qualificationstatus = qualificationstatus;
	}

	public String getRegusertype() {
		return regusertype;
	}

	public void setRegusertype(String regusertype) {
		this.regusertype = regusertype;
	}	
	
	
	

}