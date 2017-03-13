package com.beanframework.user.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class UserSearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PARAM_ALL = "all";
	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_FIRST_NAME = "firstName";
	public static final String PARAM_LAST_NAME = "lastName";
	public static final String PARAM_EMAIL = "email";

	private String username;
	private String firstName;
	private String lastName;
	private String email;

	public UserSearchCriteria() {
	}

	public UserSearchCriteria(String query, String keyword) {
		if (StringUtils.isNotEmpty(query) && StringUtils.isNotEmpty(keyword)) {
			if (query.equals(PARAM_ALL) || query.equals(PARAM_USERNAME)) {
				this.username = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_FIRST_NAME)) {
				this.firstName = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_LAST_NAME)) {
				this.lastName = keyword;
			}
			if (query.equals(PARAM_ALL) || query.equals(PARAM_EMAIL)) {
				this.email = keyword;
			}
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}