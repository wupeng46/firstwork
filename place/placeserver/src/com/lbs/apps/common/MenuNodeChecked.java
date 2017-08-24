package com.lbs.apps.common;


import java.util.ArrayList;
import java.util.List;

public class MenuNodeChecked extends BasePojo{
	
	private String id;
	private boolean expanded;
	private String text;
	private boolean leaf;
	private String url;
	private boolean checked;
	
	private List children;
	
	public MenuNodeChecked(){
		expanded=true;
		leaf=false;
		children=new ArrayList();		
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}


	public boolean isChecked() {
		return checked;
	}


	public void setChecked(boolean checked) {
		this.checked = checked;
	}


	
	
}
