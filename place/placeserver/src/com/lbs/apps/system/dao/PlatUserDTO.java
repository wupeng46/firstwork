package com.lbs.apps.system.dao;

import java.io.Serializable;

public class PlatUserDTO implements Serializable {
	
	private String uid; //用户id，唯一的(int)
	private String user_name;  //登录用户名称，不是公司名称
	private String password; //密码,aes加密。
	private String user_type; //公司/个人(string)
	private String user_id; //登录使用的，唯一（调用资金接口使用该凭证）
	private String mobile; //手机号，唯一
	private String salt; //md5二次加密使用的
	private String card_type;  //如果是公司用户是统一社会信用代码证;如果是个人，则是身份证
	private String id; //如果是公司，是证号；个人是身份证号
	private String com_name;  //公司名称
	private String digest; //对前面数据按次序和secret的md5
	//认证资料
	private String legal_person;
	private String legal_person_id;
	private String company_name;
	private String company_type;
	private String bank;
	private String bank_user;
	private String bank_num;
	private String zip_code;
	private String tel;
	private String fax;
	private String email;	
	private String contact;
	private String post_addr;
	private String post_user;
	private String post_mobile;
	private String reside_province;
	private String reside_city;
	private String reside_dist;
	private String address;
	private String desc;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	
	
	public String getLegal_person() {
		return legal_person;
	}
	public void setLegal_person(String legal_person) {
		this.legal_person = legal_person;
	}
	public String getLegal_person_id() {
		return legal_person_id;
	}
	public void setLegal_person_id(String legal_person_id) {
		this.legal_person_id = legal_person_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_type() {
		return company_type;
	}
	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBank_user() {
		return bank_user;
	}
	public void setBank_user(String bank_user) {
		this.bank_user = bank_user;
	}
	public String getBank_num() {
		return bank_num;
	}
	public void setBank_num(String bank_num) {
		this.bank_num = bank_num;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPost_addr() {
		return post_addr;
	}
	public void setPost_addr(String post_addr) {
		this.post_addr = post_addr;
	}
	public String getPost_user() {
		return post_user;
	}
	public void setPost_user(String post_user) {
		this.post_user = post_user;
	}
	public String getPost_mobile() {
		return post_mobile;
	}
	public void setPost_mobile(String post_mobile) {
		this.post_mobile = post_mobile;
	}
	public String getReside_province() {
		return reside_province;
	}
	public void setReside_province(String reside_province) {
		this.reside_province = reside_province;
	}
	public String getReside_city() {
		return reside_city;
	}
	public void setReside_city(String reside_city) {
		this.reside_city = reside_city;
	}
	public String getReside_dist() {
		return reside_dist;
	}
	public void setReside_dist(String reside_dist) {
		this.reside_dist = reside_dist;
	}
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String toString(){
		StringBuilder sb=new StringBuilder()
		.append("uid=").append(uid).append("\r\n")
		.append("user_name=").append(user_name).append("\r\n")
		.append("password=").append(password).append("\r\n")
		.append("user_type=").append(user_type).append("\r\n")
		.append("user_id=").append(user_id).append("\r\n")
		.append("mobile=").append(mobile).append("\r\n")
		.append("salt=").append(salt).append("\r\n")
		.append("card_type=").append(card_type).append("\r\n")
		.append("id=").append(id).append("\r\n")
		.append("com_name=").append(com_name).append("\r\n")		
		.append("legal_person=").append(legal_person).append("\r\n")
		.append("legal_person_id=").append(legal_person_id).append("\r\n")
		.append("company_name=").append(company_name).append("\r\n")
		.append("company_type=").append(company_type).append("\r\n")
		.append("bank=").append(bank).append("\r\n")
		.append("bank_user=").append(bank_user).append("\r\n")
		.append("bank_num=").append(bank_num).append("\r\n")
		.append("zip_code=").append(zip_code).append("\r\n")
		.append("tel=").append(tel).append("\r\n")
		.append("fax=").append(fax).append("\r\n")
		.append("email=").append(email).append("\r\n")
		.append("mobile=").append(mobile).append("\r\n")
		.append("contact=").append(contact).append("\r\n")
		.append("post_addr=").append(post_addr).append("\r\n")
		.append("post_user=").append(post_user).append("\r\n")
		.append("post_mobile=").append(post_mobile).append("\r\n")
		.append("reside_province=").append(reside_province).append("\r\n")
		.append("reside_city=").append(reside_city).append("\r\n")
		.append("reside_dist=").append(reside_dist).append("\r\n")
		.append("digest=").append(digest).append("\r\n")
		.append("address=").append(address).append("\r\n");
		return sb.toString();
	}
	

}
