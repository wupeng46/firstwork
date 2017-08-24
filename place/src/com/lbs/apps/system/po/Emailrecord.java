package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Emailrecord entity. @author MyEclipse Persistence Tools
 */

public class Emailrecord implements java.io.Serializable {

	// Fields

	private Integer emailid;
	private Integer senderid;
	private Timestamp senddate;
	private String emailtitle;
	private String emailcontent;
	private Integer receiverid;
	private Timestamp receivedate;
	private String emailstatus;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Emailrecord() {
	}

	/** minimal constructor */
	public Emailrecord(Integer emailid) {
		this.emailid = emailid;
	}

	/** full constructor */
	public Emailrecord(Integer emailid, Integer senderid, Timestamp senddate,
			String emailtitle, String emailcontent, Integer receiverid,
			Timestamp receivedate, String emailstatus, Integer createdby,
			Integer createdorg, Timestamp createddate, Integer modifyby,
			Integer modifyorg, Timestamp modifydate) {
		this.emailid = emailid;
		this.senderid = senderid;
		this.senddate = senddate;
		this.emailtitle = emailtitle;
		this.emailcontent = emailcontent;
		this.receiverid = receiverid;
		this.receivedate = receivedate;
		this.emailstatus = emailstatus;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
	}

	// Property accessors

	public Integer getEmailid() {
		return this.emailid;
	}

	public void setEmailid(Integer emailid) {
		this.emailid = emailid;
	}

	public Integer getSenderid() {
		return this.senderid;
	}

	public void setSenderid(Integer senderid) {
		this.senderid = senderid;
	}

	public Timestamp getSenddate() {
		return this.senddate;
	}

	public void setSenddate(Timestamp senddate) {
		this.senddate = senddate;
	}

	public String getEmailtitle() {
		return this.emailtitle;
	}

	public void setEmailtitle(String emailtitle) {
		this.emailtitle = emailtitle;
	}

	public String getEmailcontent() {
		return this.emailcontent;
	}

	public void setEmailcontent(String emailcontent) {
		this.emailcontent = emailcontent;
	}

	public Integer getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(Integer receiverid) {
		this.receiverid = receiverid;
	}

	public Timestamp getReceivedate() {
		return this.receivedate;
	}

	public void setReceivedate(Timestamp receivedate) {
		this.receivedate = receivedate;
	}

	public String getEmailstatus() {
		return this.emailstatus;
	}

	public void setEmailstatus(String emailstatus) {
		this.emailstatus = emailstatus;
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

}