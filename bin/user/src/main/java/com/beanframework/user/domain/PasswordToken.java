package com.beanframework.user.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.beanframework.platform.core.base.BaseDomain;
import com.beanframework.user.UserConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = UserConstants.TABLE_PASSWORD_TOKEN)
public class PasswordToken extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3793880280523953652L;
	public static final String USER_JOIN_ID = "user_id";

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = USER_JOIN_ID)
	private User user;

	@Column(columnDefinition = "BINARY(16)")
	private UUID token;

	private Date expiredDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

}
