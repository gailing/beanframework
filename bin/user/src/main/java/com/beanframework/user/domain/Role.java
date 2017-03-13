package com.beanframework.user.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.beanframework.platform.core.base.BaseDomain;
import com.beanframework.user.UserConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = UserConstants.TABLE_ROLE)
public class Role extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5353826868062762221L;
	public static final String MODEL = "Role";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String PERMISSION = "permission";
	public static final String JOIN_ID = "role_id";
	public static final String USERS = "users";
	public static final String PERMISSIONS = "permissions";

	@NotNull
	@Column(unique = true)
	private String name;

	@Column
	private String description;
	
	@ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinTable(name = UserConstants.TABLE_ROLE_PERMISSION, joinColumns = @JoinColumn(name = JOIN_ID, referencedColumnName = UUID), inverseJoinColumns = @JoinColumn(name = Permission.UUID, referencedColumnName = "uuid"))
	private List<Permission> permissions;

	@ManyToMany(mappedBy = User.ROLES, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<User> users;

	@ManyToMany(mappedBy = Group.ROLES, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Group> groups;
	
	// Default role
	private boolean nonDelete;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Role role = (Role) o;

		return this.getUuid().equals(role.getUuid());
	}

	@Override
	public int hashCode() {
		return this.getUuid().hashCode();
	}

	public boolean isNonDelete() {
		return nonDelete;
	}

	public void setNonDelete(boolean nonDelete) {
		this.nonDelete = nonDelete;
	}

}