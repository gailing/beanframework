package com.beanframework.menu.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.beanframework.menu.MenuConstants;
import com.beanframework.platform.core.base.BaseDomain;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = MenuConstants.TABLE_MENU)
public class Menu extends BaseDomain implements Comparable<Menu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 911023512884736839L;
	public static final String TYPE = "Menu";
	public static final String MODEL = "menu";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String PARENT_ID = "parent_uuid";
	public static final String PARENT = "parent";
	public static final String SORT = "sort";

	private int sort;

	private String name;

	private String description;

	private String icon;

	private String path;

	private String permissions;

	private boolean enabled;

	private boolean visible;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = PARENT_ID)
	private Menu parent;

	@OneToMany(mappedBy = PARENT, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@OrderBy(SORT + " ASC")
	private List<Menu> childs;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChilds() {
		return childs;
	}

	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isActive(Menu loopMenu, Menu currentMenu){
		
		if(loopMenu == null || currentMenu == null){
			return false;
		}
		
		if(loopMenu.getUuid().equals(currentMenu.getUuid())){
			return true;
		}
		
		if(currentMenu.getParent() != null){
			if(currentMenu.getParent().getUuid().equals(loopMenu.getUuid())){
				return true;
			}
			else{
				return isActive(loopMenu, currentMenu.getParent());
			}
		}
		else {
			return false;
		}
	}

	@Override
	public int compareTo(Menu compareMenu) {
		int compareQuantity = ((Menu) compareMenu).getSort();

		// ascending order
		return this.sort - compareQuantity;
	}

}
