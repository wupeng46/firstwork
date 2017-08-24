package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Noticemsg entity. @author MyEclipse Persistence Tools
 */

public class Noticemsg implements java.io.Serializable {

	// Fields

	private Integer noticemsgid;
	private String noticetype;
	private Integer receiverid;
	private String msgcontent;
	private String isread;
	private Timestamp readtime;
	private Integer createdby;
	private Timestamp createddate;

	// Constructors

	/** default constructor */
	public Noticemsg() {
	}

	/** full constructor */
	public Noticemsg(String noticetype, Integer receiverid, String msgcontent,
			String isread, Timestamp readtime, Integer createdby,
			Timestamp createddate) {
		this.noticetype = noticetype;
		this.receiverid = receiverid;
		this.msgcontent = msgcontent;
		this.isread = isread;
		this.readtime = readtime;
		this.createdby = createdby;
		this.createddate = createddate;
	}

	// Property accessors

	public Integer getNoticemsgid() {
		return this.noticemsgid;
	}

	public void setNoticemsgid(Integer noticemsgid) {
		this.noticemsgid = noticemsgid;
	}

	public String getNoticetype() {
		return this.noticetype;
	}

	public void setNoticetype(String noticetype) {
		this.noticetype = noticetype;
	}

	public Integer getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(Integer receiverid) {
		this.receiverid = receiverid;
	}

	public String getMsgcontent() {
		return this.msgcontent;
	}

	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}

	public String getIsread() {
		return this.isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public Timestamp getReadtime() {
		return this.readtime;
	}

	public void setReadtime(Timestamp readtime) {
		this.readtime = readtime;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreateddate() {
		return this.createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

}