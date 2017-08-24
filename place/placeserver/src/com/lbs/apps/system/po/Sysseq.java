package com.lbs.apps.system.po;

/**
 * Sysseq entity. @author MyEclipse Persistence Tools
 */

public class Sysseq implements java.io.Serializable {

	// Fields

	private String seqname;
	private Integer seqvalue;
	private String isdaynum;
	private String currdate;

	// Constructors

	/** default constructor */
	public Sysseq() {
	}

	/** minimal constructor */
	public Sysseq(String seqname) {
		this.seqname = seqname;
	}

	/** full constructor */
	public Sysseq(String seqname, Integer seqvalue,String isdaynum,String currdate) {
		this.seqname = seqname;
		this.seqvalue = seqvalue;
		this.isdaynum = isdaynum;
		this.currdate = currdate;
	}

	// Property accessors

	public String getSeqname() {
		return this.seqname;
	}

	public void setSeqname(String seqname) {
		this.seqname = seqname;
	}

	public Integer getSeqvalue() {
		return this.seqvalue;
	}

	public void setSeqvalue(Integer seqvalue) {
		this.seqvalue = seqvalue;
	}

	public String getIsdaynum() {
		return isdaynum;
	}

	public void setIsdaynum(String isdaynum) {
		this.isdaynum = isdaynum;
	}

	public String getCurrdate() {
		return currdate;
	}

	public void setCurrdate(String currdate) {
		this.currdate = currdate;
	}
	
	

}