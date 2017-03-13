package com.beanframework.platform.sysconfig.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import com.beanframework.platform.core.base.BaseDomain;
import com.beanframework.platform.sysconfig.SysConfigConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = SysConfigConstants.TABLE_CONFIG)
public class SysConfig extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2500661400995007868L;

	@Column(unique = true)
	private String name;
	private String value;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
