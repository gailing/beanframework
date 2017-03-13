package com.beanframework.menu.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;

@Component
public class MenuInit {
	
	@Autowired
	private MenuService menuService;
	
	@Value(AdminBaseConstants.PATH_ADMIN)
	private String PATH_ADMIN;

	public void init() {

		menuService.deleteAll();

		Menu menu = new Menu();

		///////////////////////////////////////////////
		// Root
		///////////////////////////////////////////////

		menu.setVisible(true);
		menu.setEnabled(true);
		menu.setSort(0);
		menu.setName("Root");
		menu.setParent(null);
		Menu root = menuService.save(menu);

		///////////////////////////////////////////////
		// Dashboard
		///////////////////////////////////////////////

		menu = new Menu();
		menu.setVisible(true);
		menu.setEnabled(true);
		menu.setSort(10);
		menu.setName("Dashboard");
		menu.setIcon("fa fa-dashboard");
		menu.setPath(PATH_ADMIN + "");
		menu.setParent(root);
		menuService.save(menu);

		///////////////////////////////////////////////
		// User Management
		///////////////////////////////////////////////

		menu = new Menu();
		menu.setVisible(true);
		menu.setEnabled(true);
		menu.setSort(20);
		menu.setName("User Management");
		menu.setIcon("fa fa-users");
		menu.setPath(null);
		menu.setParent(root);
		Menu userMaintenance = menuService.save(menu);

			///////////////////////////////////////////////
			// User Management - Users
			///////////////////////////////////////////////
	
			menu = new Menu();
			menu.setVisible(true);
			menu.setEnabled(true);
			menu.setSort(10);
			menu.setName("Users");
			menu.setIcon("fa fa-user");
			menu.setPath(PATH_ADMIN + "/user/list");
			menu.setParent(userMaintenance);
			Menu users = menuService.save(menu);
	
				///////////////////////////////////////////////
				// User Management - Users - Add User
				///////////////////////////////////////////////
		
				menu = new Menu();
				menu.setVisible(false);
				menu.setEnabled(true);
				menu.setSort(10);
				menu.setName("Add User");
				menu.setIcon("fa fa-user");
				menu.setPath(PATH_ADMIN + "/user/add");
				menu.setParent(users);
				menuService.save(menu);
		
				///////////////////////////////////////////////
				// User Management - Users - Edit User
				///////////////////////////////////////////////
		
				menu = new Menu();
				menu.setVisible(false);
				menu.setEnabled(true);
				menu.setSort(20);
				menu.setName("Edit User");
				menu.setIcon("fa fa-user");
				menu.setPath(PATH_ADMIN + "/user/edit");
				menu.setParent(users);
				menuService.save(menu);

			///////////////////////////////////////////////
			// User Management - Groups
			///////////////////////////////////////////////
	
			menu = new Menu();
			menu.setVisible(true);
			menu.setEnabled(true);
			menu.setSort(20);
			menu.setName("Groups");
			menu.setIcon("fa fa-group");
			menu.setPath(PATH_ADMIN + "/user/group/list");
			menu.setParent(userMaintenance);
			Menu group = menuService.save(menu);
			
				///////////////////////////////////////////////
				// User Management - Groups - Add Group
				///////////////////////////////////////////////
		
				menu = new Menu();
				menu.setVisible(false);
				menu.setEnabled(true);
				menu.setSort(10);
				menu.setName("Add Group");
				menu.setIcon("fa fa-group");
				menu.setPath(PATH_ADMIN + "/user/group/add");
				menu.setParent(group);
				menuService.save(menu);
		
				///////////////////////////////////////////////
				// User Management - Groups - Edit Group
				///////////////////////////////////////////////
		
				menu = new Menu();
				menu.setVisible(false);
				menu.setEnabled(true);
				menu.setSort(20);
				menu.setName("Edit Group");
				menu.setIcon("fa fa-group");
				menu.setPath(PATH_ADMIN + "/user/group/edit");
				menu.setParent(group);
				menuService.save(menu);

			///////////////////////////////////////////////
			// User Management - Roles
			///////////////////////////////////////////////
	
			menu = new Menu();
			menu.setVisible(true);
			menu.setEnabled(true);
			menu.setSort(30);
			menu.setName("Roles");
			menu.setIcon("fa fa-street-view");
			menu.setPath(PATH_ADMIN + "/user/role/list");
			menu.setParent(userMaintenance);
			Menu role = menuService.save(menu);
			
				///////////////////////////////////////////////
				// User Management - Roles - Add Role
				///////////////////////////////////////////////
		
				menu = new Menu();
				menu.setVisible(false);
				menu.setEnabled(true);
				menu.setSort(10);
				menu.setName("Add Role");
				menu.setIcon("fa fa-role");
				menu.setPath(PATH_ADMIN + "/user/role/add");
				menu.setParent(role);
				menuService.save(menu);
		
				///////////////////////////////////////////////
				// User Management - Roles - Edit Role
				///////////////////////////////////////////////
		
				menu = new Menu();
				menu.setVisible(false);
				menu.setEnabled(true);
				menu.setSort(20);
				menu.setName("Edit Role");
				menu.setIcon("fa fa-role");
				menu.setPath(PATH_ADMIN + "/user/role/edit");
				menu.setParent(role);
				menuService.save(menu);
			
		///////////////////////////////////////////////
		// File Management
		///////////////////////////////////////////////

		menu = new Menu();
		menu.setVisible(true);
		menu.setEnabled(true);
		menu.setSort(30);
		menu.setName("File Management");
		menu.setIcon("fa fa-folder");
		menu.setPath(PATH_ADMIN + "/filemanager");
		menu.setParent(root);
		menuService.save(menu);
		
		///////////////////////////////////////////////
		// System
		///////////////////////////////////////////////

		menu = new Menu();
		menu.setVisible(true);
		menu.setEnabled(true);
		menu.setSort(40);
		menu.setName("System");
		menu.setIcon("fa fa-wrench");
		menu.setPath(null);
		menu.setParent(root);
		Menu system = menuService.save(menu);

			///////////////////////////////////////////////
			// System - Operation Log
			///////////////////////////////////////////////
	
			menu = new Menu();
			menu.setVisible(true);
			menu.setEnabled(true);
			menu.setSort(10);
			menu.setName("Operation Log");
			menu.setIcon("fa fa-server");
			menu.setPath(PATH_ADMIN + "/oplog/list");
			menu.setParent(system);
			menuService.save(menu);
	}
}
