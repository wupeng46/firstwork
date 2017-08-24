package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Msgtemplate entity. @author MyEclipse Persistence Tools
 */

public class Msgtemplate implements java.io.Serializable {

	// Fields

	private Integer msgtemplateid;
	private String msgtemplatename;
	private String templatecontent;
	private String isvalid;
	private String memo;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Msgtemplate() {
	}

	/** minimal constructor */
	public Msgtemplate(Integer msgtemplateid) {
		this.msgtemplateid = msgtemplateid;
	}

	/** full constructor */
	public Msgtemplate(Integer msgtemplateid, String msgtemplatename,
			String templatecontent, String isvalid, String memo,
			Integer createdby, Integer createdorg, Timestamp createddate,
			Integer modifyby, Integer modifyorg, Timestamp modifydate) {
		this.msgtemplateid = msgtemplateid;
		this.msgtemplatename = msgtemplatename;
		this.templatecontent = templatecontent;
		this.isvalid = isvalid;
		this.memo = memo;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
	}

	// Property accessors

	public Integer getMsgtemplateid() {
		return this.msgtemplateid;
	}

	public void setMsgtemplateid(Integer msgtemplateid) {
		this.msgtemplateid = msgtemplateid;
	}

	public String getMsgtemplatename() {
		return this.msgtemplatename;
	}

	public void setMsgtemplatename(String msgtemplatename) {
		this.msgtemplatename = msgtemplatename;
	}

	public String getTemplatecontent() {
		return this.templatecontent;
	}

	public void setTemplatecontent(String templatecontent) {
		this.templatecontent = templatecontent;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
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

}