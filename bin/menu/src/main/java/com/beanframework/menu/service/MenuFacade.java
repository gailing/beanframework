package com.beanframework.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import com.beanframework.menu.domain.Menu;
import com.beanframework.platform.core.base.BaseFacade;
import com.beanframework.platform.core.domain.TreeJson;
import com.beanframework.platform.core.domain.TreeJsonState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuFacade extends BaseFacade {

	@Autowired
	private MenuService menuService;

	public Menu getMenuById(String uuid) {
		return menuService.findOne(UUID.fromString(uuid));
	}
	
	public Menu findAllMenu(boolean filterWithVisible){
		return menuService.findAllMenu(filterWithVisible);
	}
	
	public Menu findAllEnabledVisibleMenu(List<String> permissions){
		return menuService.findPermissionedMenu(permissions);
	}

	public Menu getMenuByPath(String path) {
		return menuService.findByPath(path);
	}

	public List<Menu> getBreadcrumb(Menu menu) {
		List<Menu> list = menuService.getAllMenuParent(menu);
		Collections.reverse(list);
		return list;
	}

	public Menu saveMenu(String parentId, Menu menu) throws Exception {

		if (StringUtils.isEmpty(menu.getName())) {
			throw new Exception("Name required.");
		}
		
		Menu existsMenu = getMenuByPath(menu.getPath());
		
		if(existsMenu != null){
			if(menu.getUuid() == null){
				throw new Exception("Path exits in Menu ["+existsMenu.getName()+"]");
			}
			else if(!menu.getUuid().equals(existsMenu.getUuid())){
				throw new Exception("Path exits in Menu ["+existsMenu.getName()+"]");
			}
		}

		if (parentId != null) {
			Menu parent = getMenuById(parentId);
			menu.setParent(parent);
			menu.setSort(999);

			menu = menuService.save(menu);
			menuService.savePosition(UUID.fromString(parentId), menu.getUuid(), 0);
		} else {
			menu = menuService.save(menu);
		}

		return menu;
	}

	public void changePosition(String fromId, String toId, String toIndex) throws Exception {

		if (StringUtils.isEmpty(fromId)) {
			throw new Exception("From Id required.");
		}

		if (StringUtils.isEmpty(toId)) {
			throw new Exception("To Id required.");
		}

		if (StringUtils.isEmpty(fromId)) {
			throw new Exception("To Index required.");
		}

		// Save parent
		Menu menu = menuService.findOne(UUID.fromString(fromId));

		Menu parent = menuService.findOne(UUID.fromString(toId));
		menu.setParent(parent);
		menu.setSort(999);
		menu = menuService.save(menu);

		menuService.savePosition(UUID.fromString(toId), UUID.fromString(fromId), Integer.valueOf(toIndex));
	}

	public void deleteMenuById(String uuid) {
		menuService.delete(UUID.fromString(uuid));
	}

	public List<TreeJson> getTreeMenu(String selectedUuid) {
		Menu rootMenu = findAllMenu(false);
		List<TreeJson> data = new ArrayList<TreeJson>();

		if (rootMenu != null) {
			if (StringUtils.isEmpty(selectedUuid)) {
				data.add(convertToJson(rootMenu, null));
			} else {
				data.add(convertToJson(rootMenu, UUID.fromString(selectedUuid)));
			}
		}

		return data;
	}

	public TreeJson convertToJson(Menu menu, UUID menuUuid) {

		TreeJson parent = new TreeJson();

		parent.setId(menu.getUuid().toString());
		parent.setText(convertName(menu));
		parent.setIcon(menu.getIcon());

		if (menuUuid != null && menuUuid.equals(menu.getUuid())) {
			parent.setState(new TreeJsonState(true));
		} else {
			parent.setState(new TreeJsonState(false));
		}

		List<TreeJson> children = new ArrayList<TreeJson>();
		if (!menu.getChilds().isEmpty()) {

			for (Menu child : menu.getChilds()) {
				children.add(convertToJson(child, menuUuid));
			}
		}
		parent.setChildren(children);

		return parent;
	}

	public String convertName(Menu menu) {

		String name = menu.getName();

		if (!menu.isEnabled()) {
			name = name + " [Disabled]";
		}
		if (!menu.isVisible()) {
			name = name + " [Hidden]";
		}

		return name;
	}
}
