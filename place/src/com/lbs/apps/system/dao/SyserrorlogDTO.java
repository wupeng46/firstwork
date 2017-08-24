package com.lbs.apps.system.dao;

import java.io.Serializable;
import java.sql.Timestamp;

public class SyserrorlogDTO implements Serializable {
	private Integer syserrorlogid;
    private String modmethod;
    private String logdesc;
    private String errorreason;
    private Integer createdby;
    private String createddate;
    private String starttime;
    private String endtime;
	public Integer getSyserrorlogid() {
		return syserrorlogid;
	}
	public void setSyserrorlogid(Integer syserrorlogid) {
		this.syserrorlogid = syserrorlogid;
	}
	public String getModmethod() {
		return modmethod;
	}
	public void setModmethod(String modmethod) {
		this.modmethod = modmethod;
	}
	public String getLogdesc() {
		return logdesc;
	}
	public void setLogdesc(String logdesc) {
		this.logdesc = logdesc;
	}
	public String getErrorreason() {
		return errorreason;
	}
	public void setErrorreason(String errorreason) {
		this.errorreason = errorreason;
	}
	public Integer getCreatedby() {
		return createdby;
	}
	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
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
    
    

}
