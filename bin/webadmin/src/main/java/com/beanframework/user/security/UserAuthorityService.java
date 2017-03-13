package com.beanframework.user.security;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.user.config.AdminSecurityConfig;
import com.beanframework.user.domain.Group;
import com.beanframework.user.domain.Permission;
import com.beanframework.user.domain.Role;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.GroupService;
import com.beanframework.user.service.RoleService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAuthorityService {

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private RoleService roleService;
	
	public List<GrantedAuthority> getUserAuthorities(User user){
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);

		List<Role> roleCollection = new ArrayList<Role>();

		for (Group group : user.getGroups()) {
			authorities.add(new SimpleGrantedAuthority(group.getName()));

			for (Role role : group.getRoles()) {
				
				boolean insertCollection = true;
				for (Role roleCollect : roleCollection) {
					if(roleCollect.getUuid().equals(role.getUuid())){
						insertCollection = false;
					}
				}
				
				if (insertCollection) {
					roleCollection.add(role);
				}
			}
		}

		for (Role role : user.getRoles()) {
			boolean insertCollection = true;
			for (Role roleCollect : roleCollection) {
				if(roleCollect.getUuid().equals(role.getUuid())){
					insertCollection = false;
				}
			}
			
			if (insertCollection) {
				roleCollection.add(role);
			}
		}

		for (Role role : roleCollection) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			for (Permission permission : role.getPermissions()) {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}

		return authorities;
	}
	
	public boolean isSuperAdmin(User user){
		
		for (GrantedAuthority authority : user.getAuthorities()) {
			if(authority.getAuthority().equals(AdminSecurityConfig.SUPER_ADMIN_ROLE)){
				return true;
			}
		}
		
		return false;
	}

	public List<GrantedAuthority> getSuperAdminAuthorities() {

		List<Group> allGroups = groupService.findAllGroups();
		List<Role> allRoles = roleService.findAllRoles();

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);

		List<Role> roleCollection = new ArrayList<Role>();

		for (Group group : allGroups) {
			authorities.add(new SimpleGrantedAuthority(group.getName()));

			Hibernate.initialize(group.getRoles());
			for (Role role : group.getRoles()) {
				if (!roleCollection.contains(role)) {
					Hibernate.initialize(role.getPermissions());
					roleCollection.add(role);
				}
			}
		}

		for (Role role : allRoles) {
			if (!roleCollection.contains(role)) {
				Hibernate.initialize(role.getPermissions());
				roleCollection.add(role);
			}
		}

		for (Role role : roleCollection) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));

			Hibernate.initialize(role.getPermissions());
			for (Permission permission : role.getPermissions()) {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}

		return authorities;
	}
}