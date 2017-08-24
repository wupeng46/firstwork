package com.lbs.apps.system.dao;

import java.io.Serializable;
import java.sql.Timestamp;

public class SystemlogDTO implements Serializable {
	private Integer systemlogid;
    private String modmethod;
    private String logdesc;
    private Integer createdby;
    private String createddate;
    private String starttime;
    private String endtime;
    
	public Integer getSystemlogid() {
		return systemlogid;
	}
	public void setSystemlogid(Integer systemlogid) {
		this.systemlogid = systemlogid;
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
