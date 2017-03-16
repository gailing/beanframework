package com.beanframework.accountpolicy.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.accountpolicy.AccountPolicyConstants;
import com.beanframework.platform.core.base.BaseDomain;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = AccountPolicyConstants.TABLE_ACCOUNTPOLICY)
public class AccountPolicy extends BaseDomain{

	private String name;
	private String description;
}
