package com.lbs.apps.api.dao;

import com.lbs.apps.common.CommonDTO;

public class ApiDTO extends CommonDTO {
	private Integer companyid;
	private Integer memberid;
	private String batchno;
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}	
	
	

}
