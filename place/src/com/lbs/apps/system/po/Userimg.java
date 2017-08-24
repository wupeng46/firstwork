package com.lbs.apps.system.po;

/**
 * Userimg entity. @author MyEclipse Persistence Tools
 */

public class Userimg implements java.io.Serializable {

	// Fields

	private Integer userimgid;
	private Integer userid;
	private String imgtype;
	private Short orderno;
	private String imgurl;
	

	// Constructors

	/** default constructor */
	public Userimg() {
	}

	/** minimal constructor */
	public Userimg(Integer userimgid, Integer userid) {
		this.userimgid = userimgid;
		this.userid = userid;
	}

	/** full constructor */
	public Userimg(Integer userimgid, Integer userid, String imgtype,
			Short orderno, String imgurl) {
		this.userimgid = userimgid;
		this.userid = userid;
		this.imgtype = imgtype;
		this.orderno = orderno;
		this.imgurl = imgurl;
		
	}

	// Property accessors

	public Integer getUserimgid() {
		return this.userimgid;
	}

	public void setUserimgid(Integer userimgid) {
		this.userimgid = userimgid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getImgtype() {
		return this.imgtype;
	}

	public void setImgtype(String imgtype) {
		this.imgtype = imgtype;
	}

	public Short getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Short orderno) {
		this.orderno = orderno;
	}

	public String getImgurl() {
		return this.imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	

}