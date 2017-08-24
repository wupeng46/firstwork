package com.lbs.apps.system.po;

import java.sql.Timestamp;

/**
 * Stockdetail entity. @author MyEclipse Persistence Tools
 */

public class Stockdetail implements java.io.Serializable {

	// Fields

	private StockdetailId id;
	private String stocktype;
	private Double water;
	private Double impurity;
	private Double capacity;
	private String smell;
	private Double imperfecttotal;
	private Double imperfectnum;
	private Double imperfectnum2;
	private Double qty;
	private Double price;
	private Double amount;
	private Integer createdby;
	private Integer createdorg;
	private Timestamp createddate;
	private Integer modifyby;
	private Integer modifyorg;
	private Timestamp modifydate;

	// Constructors

	/** default constructor */
	public Stockdetail() {
	}

	/** minimal constructor */
	public Stockdetail(StockdetailId id) {
		this.id = id;
	}

	/** full constructor */
	public Stockdetail(StockdetailId id, String stocktype, Double water,
			Double impurity, Double capacity, String smell,
			Double imperfecttotal, Double imperfectnum, Double imperfectnum2,
			Double qty, Double price, Double amount, Integer createdby,
			Integer createdorg, Timestamp createddate, Integer modifyby,
			Integer modifyorg, Timestamp modifydate) {
		this.id = id;
		this.stocktype = stocktype;
		this.water = water;
		this.impurity = impurity;
		this.capacity = capacity;
		this.smell = smell;
		this.imperfecttotal = imperfecttotal;
		this.imperfectnum = imperfectnum;
		this.imperfectnum2 = imperfectnum2;
		this.qty = qty;
		this.price = price;
		this.amount = amount;
		this.createdby = createdby;
		this.createdorg = createdorg;
		this.createddate = createddate;
		this.modifyby = modifyby;
		this.modifyorg = modifyorg;
		this.modifydate = modifydate;
	}

	// Property accessors

	public StockdetailId getId() {
		return this.id;
	}

	public void setId(StockdetailId id) {
		this.id = id;
	}

	public String getStocktype() {
		return this.stocktype;
	}

	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}

	public Double getWater() {
		return this.water;
	}

	public void setWater(Double water) {
		this.water = water;
	}

	public Double getImpurity() {
		return this.impurity;
	}

	public void setImpurity(Double impurity) {
		this.impurity = impurity;
	}

	public Double getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public String getSmell() {
		return this.smell;
	}

	public void setSmell(String smell) {
		this.smell = smell;
	}

	public Double getImperfecttotal() {
		return this.imperfecttotal;
	}

	public void setImperfecttotal(Double imperfecttotal) {
		this.imperfecttotal = imperfecttotal;
	}

	public Double getImperfectnum() {
		return this.imperfectnum;
	}

	public void setImperfectnum(Double imperfectnum) {
		this.imperfectnum = imperfectnum;
	}

	public Double getImperfectnum2() {
		return this.imperfectnum2;
	}

	public void setImperfectnum2(Double imperfectnum2) {
		this.imperfectnum2 = imperfectnum2;
	}

	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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