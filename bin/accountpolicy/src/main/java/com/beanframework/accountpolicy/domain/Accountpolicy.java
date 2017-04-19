package com.beanframework.accountpolicy.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.accountpolicy.AccountpolicyConstants;
import com.beanframework.platform.core.base.BaseDomain;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = AccountpolicyConstants.TABLE_ACCOUNTPOLICY)
public class Accountpolicy extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7915444450335776497L;
	private String name;
	private String description;
	private String value;
	
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
