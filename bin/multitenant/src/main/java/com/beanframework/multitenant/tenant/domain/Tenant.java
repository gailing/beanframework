package com.beanframework.multitenant.tenant.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import com.beanframework.platform.core.base.BaseDomain;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tenant")
public class Tenant extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9084750635360920970L;
	private String driver;
	private String url;
	private String username;
	private String password;

	public Tenant() {
		super();
	}

	public Tenant(String id, String driver, String url, String username, String password) {
		super();
		this.setId(id);
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
