package com.beanframework.user.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import com.beanframework.platform.core.base.BaseDomain;
import com.beanframework.user.UserConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = UserConstants.TABLE_USER_POLICY)
public class UserPolicy extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6222231804179329328L;

	private int maxLoginAttempts;

	private int passwordDayExpired;
	
	private int passwordTokenHourExpired;

	public int getMaxLoginAttempts() {
		return maxLoginAttempts;
	}

	public void setMaxLoginAttempts(int maxLoginAttempts) {
		this.maxLoginAttempts = maxLoginAttempts;
	}

	public int getPasswordDayExpired() {
		return passwordDayExpired;
	}

	public void setPasswordDayExpired(int passwordDayExpired) {
		this.passwordDayExpired = passwordDayExpired;
	}

}
