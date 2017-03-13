package com.beanframework.user.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.platform.core.base.BaseDomain;
import com.beanframework.user.UserConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = UserConstants.TABLE_PERMISSION)
public class Permission extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8483317186745405381L;
	public static final String MODEL = "Permission";
	public static final String NAME = "name";
	public static final String JOIN_ID = "permission_id";
	public static final String PARENT_ID = "parent_id";
	public static final String PARENT = "parent";
	public static final String SORT = "sort";

	@NotNull
	private String name;

	private String description;

	private int sort;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = PARENT_ID)
	private Permission parent;

	@OneToMany(mappedBy = PARENT, cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@OrderBy(SORT + " ASC")
	private List<Permission> childs;

	@ManyToMany(mappedBy = Role.PERMISSIONS, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Role> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Permission getParent() {
		return parent;
	}

	public void setParent(Permission parent) {
		this.parent = parent;
	}

	public List<Permission> getChilds() {
		return childs;
	}

	public void setChilds(List<Permission> childs) {
		this.childs = childs;
	}

}