package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Loginlog entity. @author MyEclipse Persistence Tools
 */

public class Loginlog implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userid;
	private Timestamp logintime;
	private Timestamp logouttime;
	private String ip;

	// Constructors

	/** default constructor */
	public Loginlog() {
	}

	/** minimal constructor */
	public Loginlog(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Loginlog(Integer id, Integer userid, Timestamp logintime,
			Timestamp logouttime, String ip) {
		this.id = id;
		this.userid = userid;
		this.logintime = logintime;
		this.logouttime = logouttime;
		this.ip = ip;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Timestamp getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}

	public Timestamp getLogouttime() {
		return this.logouttime;
	}

	public void setLogouttime(Timestamp logouttime) {
		this.logouttime = logouttime;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}