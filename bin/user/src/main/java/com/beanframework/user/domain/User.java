package com.beanframework.user.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.beanframework.platform.core.base.BaseDomain;
import com.beanframework.user.UserConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = UserConstants.TABLE_USER)
public class User extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6946719506403022709L;
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String GROUPS = "groups";
	public static final String JOIN_ID = "user_id";
	public static final String ROLES = "roles";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String EMAIL = "email";
	public static final String LAST_LOGON_DATE = "lastLogonDate";
	public static final String MODEL = "user";

	@Transient
	private List<GrantedAuthority> authorities;

	@NotNull
	private String username;

	@NotNull
	private String password;

	private boolean enabled;

	private boolean accountNonExpired;

	private boolean accountNonLocked;

	private boolean credentialsNonExpired;

	private int attempts;

	private String firstName;

	private String lastName;

	private String email;

	private Date lastLogonDate;

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = UserConstants.TABLE_USER_GROUP, joinColumns = @JoinColumn(name = JOIN_ID, referencedColumnName = UUID), inverseJoinColumns = @JoinColumn(name = Group.JOIN_ID, referencedColumnName = Group.UUID))
	@OrderBy(CREATED_DATE + " ASC")
	private List<Group> groups;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = UserConstants.TABLE_USER_ROLE, joinColumns = @JoinColumn(name = JOIN_ID, referencedColumnName = UUID), inverseJoinColumns = @JoinColumn(name = Role.JOIN_ID, referencedColumnName = Role.UUID))
	@OrderBy(CREATED_DATE + " ASC")
	private List<Role> roles;

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
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

	public Date getLastLogonDate() {
		return lastLogonDate;
	}

	public void setLastLogonDate(Date lastLogonDate) {
		this.lastLogonDate = lastLogonDate;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;

		return getUuid().equals(user.getUuid());
	}

	@Override
	public int hashCode() {
		if (getUuid() != null) {
			return getUuid().hashCode();
		} else {
			return super.hashCode();
		}
	}

}