package com.lbs.apps.common;

import java.util.ArrayList;
import java.util.List;

public class PageBean extends BasePojo {
	private Integer total;//�ܼ�¼��
	private Integer limit;//ÿҳ�ܼ�¼��
	private Integer page;//��ǰҳ
	private List datas;
	
	public PageBean() {
		total=new Integer(0);
		limit=new Integer(10);
		page=new Integer(0);		
		datas=new ArrayList();
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}
	
	

}
