package com.beanframework.platform.core.domain;

import java.util.List;

public class TreeJson {

	private String id;
	private String text;
	private TreeJsonState state;
	private String icon;
	private List<TreeJson> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TreeJsonState getState() {
		return state;
	}

	public void setState(TreeJsonState state) {
		this.state = state;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<TreeJson> getChildren() {
		return children;
	}

	public void setChildren(List<TreeJson> children) {
		this.children = children;
	}
	
	public boolean hasChildren(){
		if(children == null || children.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}

}
