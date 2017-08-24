package com.lbs.apps.system.po;

/**
 * Sysgroup entity. @author MyEclipse Persistence Tools
 */

public class Sysgroup implements java.io.Serializable {

	// Fields

	private Integer groupid;
	private String groupname;
	private Integer parentid;
	private String isvalid;
	private String contact;
	private String telephone;
	private String address;
	private String memo;

	// Constructors

	/** default constructor */
	public Sysgroup() {
	}

	/** minimal constructor */
	public Sysgroup(Integer groupid) {
		this.groupid = groupid;
	}

	/** full constructor */
	public Sysgroup(Integer groupid, String groupname, Integer parentid,
			String isvalid, String contact, String telephone, String address,
			String memo) {
		this.groupid = groupid;
		this.groupname = groupname;
		this.parentid = parentid;
		this.isvalid = isvalid;
		this.contact = contact;
		this.telephone = telephone;
		this.address = address;
		this.memo = memo;
	}

	// Property accessors

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Integer getParentid() {
		return this.parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}