package com.beanframework.user.interceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import com.beanframework.common.AdminBaseInterceptor;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuFacade;
import com.beanframework.user.config.AdminSecurityConfig;
import com.beanframework.user.domain.Group;
import com.beanframework.user.domain.Role;
import com.beanframework.user.security.UserAuthorityService;
import com.beanframework.user.utils.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AdminMenuSecurityInterceptor extends AdminBaseInterceptor {

	@Autowired
	private MenuFacade menuFacade;

	@Autowired
	private UserManager userManager;
	
	@Autowired
	private UserAuthorityService userAuthorityService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String path = urlPathHelper.getLookupPathForRequest(request);

		if (userManager.isLoggedIn()) {

			if (userAuthorityService.isSuperAdmin(userManager.getCurrentUser())) {
				return true;
			}

//			boolean isAdmin = false;
//			for (Group group : userManager.getCurrentUser().getGroups()) {
//				for (Role role : group.getRoles()) {
//					if (role.getName().equals(AdminSecurityConfig.ADMIN_ROLE)) {
//						isAdmin = true;
//					}
//				}
//			}
//			for (Role role : userManager.getCurrentUser().getRoles()) {
//				if (role.getName().equals(AdminSecurityConfig.ADMIN_ROLE)) {
//					isAdmin = true;
//				}
//			}
//			if (isAdmin == false) {
//				userManager.expireAllSessionsByUsername(userManager.getCurrentUser().getUsername());
//				return true;
//			}

			Menu rootMenu = menuFacade.findAllMenu(false);

			if (rootMenu != null) {

				List<String> permissions = getMenuPermissionByPath(rootMenu.getChilds(), path);

				if (!permissions.isEmpty()) {

					Collection<? extends GrantedAuthority> authorities = userManager.getAuthorities();

					boolean hasAuthority = false;
					for (String permission : permissions) {
						boolean authorized = authorities.contains(new SimpleGrantedAuthority(permission));
						if (authorized) {
							hasAuthority = true;
						}
					}

					if (hasAuthority == false) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied/Forbidden");
						return false;
					}
				}
			}
		}

		return true;

	}

	public List<String> getMenuPermissionByPath(List<Menu> childs, String path) {

		List<String> permissions = new ArrayList<String>();

		for (Menu menu : childs) {
			if (StringUtils.isNotEmpty(menu.getPath()) && menu.getPath().equalsIgnoreCase(path)) {
				if (StringUtils.isNotEmpty(menu.getPermissions())) {
					permissions.add(menu.getPermissions());
				}
			}

			if (!menu.getChilds().isEmpty()) {
				for (String permission : getMenuPermissionByPath(menu.getChilds(), path)) {
					permissions.add(permission);
				}
			}
		}

		return permissions;
	}
}
