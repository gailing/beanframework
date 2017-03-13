package com.beanframework.user.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.beanframework.platform.core.base.BaseDomain;
import com.beanframework.user.UserConstants;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = UserConstants.TABLE_GROUP)
public class Group extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2491169055264535136L;
	public static final String MODEL = "Group";
	public static final String NAME = "name";
	public static final String JOIN_ID = "group_id";
	public static final String USERS = "users";
	public static final String ROLES = "roles";
	public static final String DESCRIPTION = "description";

	@NotNull
	@Column(unique = true)
	private String name;

	@Column
	private String description;

	@ManyToMany(mappedBy = User.GROUPS, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<User> users;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = UserConstants.TABLE_GROUP_ROLE, joinColumns = @JoinColumn(name = JOIN_ID, referencedColumnName = UUID), inverseJoinColumns = @JoinColumn(name = Role.JOIN_ID, referencedColumnName = Role.UUID))
	private List<Role> roles;

	@Transient
	private UUID[] selectedRoleIds;

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public UUID[] getSelectedRoleIds() {
		return selectedRoleIds;
	}

	public void setSelectedRoleIds(UUID[] selectedRoleIds) {
		this.selectedRoleIds = selectedRoleIds;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Group group = (Group) o;

		return this.getUuid().equals(group.getUuid());
	}

	@Override
	public int hashCode() {
		return this.getUuid().hashCode();
	}

}
