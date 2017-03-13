package com.beanframework.user.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.beanframework.user.config.AdminSecurityConfig;
import com.beanframework.user.domain.Role;
import com.beanframework.user.service.GroupService;
import com.beanframework.user.service.RoleService;
import com.beanframework.user.service.UserService;

@Component
public class UserInit implements ApplicationListener<ApplicationReadyEvent> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;

	@Autowired
	private RoleService roleService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		createDefaultAdminRole();
	}

	public void createDefaultAdminRole() {
		Role role = roleService.findByName(AdminSecurityConfig.ADMIN_ROLE);

		if (role == null) {
			role = new Role();
			role.setName(AdminSecurityConfig.ADMIN_ROLE);
			role.setNonDelete(true);
			role.setDescription("Default Admin Role");
			roleService.save(role);
		}
	}
	
	public void init(){
		roleService.deleteAll();
		groupService.deleteAll();
		userService.deleteAll();
		
		createDefaultAdminRole();
	}
}
