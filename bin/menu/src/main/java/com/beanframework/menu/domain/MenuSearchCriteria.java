package com.beanframework.menu.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class MenuSearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PARAM_ALL = "all";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_DESCRIPTION = "description";

	private String name;
	private String description;

	public MenuSearchCriteria() {
	}

	public MenuSearchCriteria(String query, String keyword) {
		if (StringUtils.isNotEmpty(query) && StringUtils.isNotEmpty(keyword)) {
			if (query.equals(PARAM_ALL) || query.equals(PARAM_NAME)) {
				this.name = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_NAME)) {
				this.name = keyword;
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}