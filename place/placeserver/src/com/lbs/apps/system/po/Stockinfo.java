package com.lbs.apps.system.po;

/**
 * Stockinfo entity. @author MyEclipse Persistence Tools
 */

public class Stockinfo implements java.io.Serializable {

	// Fields

	private String stockid;
	private String sourcename;
	private String origin;
	private String goodstype;
	private String brand;
	private String productlevel;
	private String yearnum;
	private String retriveshop;
	private Double instock;
	private Double outstock;
	private Double stock;

	// Constructors

	/** default constructor */
	public Stockinfo() {
	}

	/** minimal constructor */
	public Stockinfo(String stockid) {
		this.stockid = stockid;
	}

	/** full constructor */
	public Stockinfo(String stockid, String sourcename, String origin,
			String goodstype, String brand, String productlevel,
			String yearnum, String retriveshop, Double instock,
			Double outstock, Double stock) {
		this.stockid = stockid;
		this.sourcename = sourcename;
		this.origin = origin;
		this.goodstype = goodstype;
		this.brand = brand;
		this.productlevel = productlevel;
		this.yearnum = yearnum;
		this.retriveshop = retriveshop;
		this.instock = instock;
		this.outstock = outstock;
		this.stock = stock;
	}

	// Property accessors

	public String getStockid() {
		return this.stockid;
	}

	public void setStockid(String stockid) {
		this.stockid = stockid;
	}

	public String getSourcename() {
		return this.sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getGoodstype() {
		return this.goodstype;
	}

	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProductlevel() {
		return this.productlevel;
	}

	public void setProductlevel(String productlevel) {
		this.productlevel = productlevel;
	}

	public String getYearnum() {
		return this.yearnum;
	}

	public void setYearnum(String yearnum) {
		this.yearnum = yearnum;
	}

	public String getRetriveshop() {
		return this.retriveshop;
	}

	public void setRetriveshop(String retriveshop) {
		this.retriveshop = retriveshop;
	}

	public Double getInstock() {
		return this.instock;
	}

	public void setInstock(Double instock) {
		this.instock = instock;
	}

	public Double getOutstock() {
		return this.outstock;
	}

	public void setOutstock(Double outstock) {
		this.outstock = outstock;
	}

	public Double getStock() {
		return this.stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

}