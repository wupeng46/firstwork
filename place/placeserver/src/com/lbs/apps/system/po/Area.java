package com.lbs.apps.system.po;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */

public class Area implements java.io.Serializable {

	// Fields

	private String areaid;
	private String areafullname;
	private String arealevel;
	private String statecode;
	private String statename;
	private String citycode;
	private String cityname;
	private String areacode;
	private String areaname;
	private String towncode;
	private String townname;
	private String isvalid;

	// Constructors

	/** default constructor */
	public Area() {
	}

	/** minimal constructor */
	public Area(String areaid) {
		this.areaid = areaid;
	}

	/** full constructor */
	public Area(String areaid, String areafullname, String arealevel,
			String statecode, String statename, String citycode,
			String cityname, String areacode, String areaname, String towncode,
			String townname, String isvalid) {
		this.areaid = areaid;
		this.areafullname = areafullname;
		this.arealevel = arealevel;
		this.statecode = statecode;
		this.statename = statename;
		this.citycode = citycode;
		this.cityname = cityname;
		this.areacode = areacode;
		this.areaname = areaname;
		this.towncode = towncode;
		this.townname = townname;
		this.isvalid = isvalid;
	}

	// Property accessors

	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getAreafullname() {
		return this.areafullname;
	}

	public void setAreafullname(String areafullname) {
		this.areafullname = areafullname;
	}

	public String getArealevel() {
		return this.arealevel;
	}

	public void setArealevel(String arealevel) {
		this.arealevel = arealevel;
	}

	public String getStatecode() {
		return this.statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getStatename() {
		return this.statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCityname() {
		return this.cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getAreaname() {
		return this.areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getTowncode() {
		return this.towncode;
	}

	public void setTowncode(String towncode) {
		this.towncode = towncode;
	}

	public String getTownname() {
		return this.townname;
	}

	public void setTownname(String townname) {
		this.townname = townname;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

}