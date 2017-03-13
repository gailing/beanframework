package com.beanframework.user.interceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beanframework.common.AdminBaseInterceptor;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuFacade;
import com.beanframework.user.security.UserAuthorityService;
import com.beanframework.user.utils.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AdminMenuInterceptor extends AdminBaseInterceptor {

	@Autowired
	private UserManager userManager;

	@Autowired
	private MenuFacade menuFacade;
	
	@Autowired
	private UserAuthorityService userAuthorityService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		String path = urlPathHelper.getLookupPathForRequest(request);

		if (userManager.isLoggedIn() && modelAndView != null) {

			Menu menu;

			if (userAuthorityService.isSuperAdmin(userManager.getCurrentUser())) {
				menu = menuFacade.findAllMenu(true);
			} else {

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

				List<String> permissions = new ArrayList<String>();

				for (GrantedAuthority grantedAuthority : authorities) {
					permissions.add(grantedAuthority.getAuthority());
				}

				menu = menuFacade.findAllEnabledVisibleMenu(permissions);
			}

			modelAndView.getModelMap().addAttribute("rootMenu", menu);

			Menu currentMenu = menuFacade.getMenuByPath(path);
			modelAndView.getModelMap().addAttribute("currentMenu", currentMenu);

			if (currentMenu != null) {
				List<Menu> parentMenus = menuFacade.getBreadcrumb(currentMenu);
				modelAndView.getModelMap().addAttribute("parentMenus", parentMenus);
			}
		}

	}

}
