package com.beanframework.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;
import com.beanframework.user.interceptor.AdminMenuInterceptor;
import com.beanframework.user.interceptor.AdminMenuSecurityInterceptor;

@Configuration
@Order(2)
public class AdminMvcConfig extends WebMvcConfigurerAdapter {
	
	@Value(AdminBaseConstants.PATH_ADMIN)
	private String PATH_ADMIN;
	
	@Value(AdminBaseConstants.PATH_CONSOLE)
	private String PATH_ADMIN_CONSOLE;
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	
    	String adminThemePath = ThemeManager.getInstance().getAdminThemePath();
    	
    	registry.addViewController("").setViewName(adminThemePath+"/admin/login");
    	registry.addViewController("/").setViewName(adminThemePath+"/admin/login");
        registry.addViewController(PATH_ADMIN).setViewName(adminThemePath+"/dashboard");
        registry.addViewController(PATH_ADMIN+"/login").setViewName(adminThemePath+"/admin/login");
        registry.addViewController(PATH_ADMIN+"/register").setViewName(adminThemePath+"/admin/register");
        registry.addViewController(PATH_ADMIN_CONSOLE+"/login").setViewName(adminThemePath+"/console/login");
        
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminMenuInterceptor()).addPathPatterns(PATH_ADMIN+"/**").excludePathPatterns(PATH_ADMIN_CONSOLE+"/**");
		registry.addInterceptor(adminMenuSecurityInterceptor()).addPathPatterns(PATH_ADMIN+"/**").excludePathPatterns(PATH_ADMIN_CONSOLE+"/**");
	}

	@Bean
	public AdminMenuInterceptor adminMenuInterceptor() {
		return new AdminMenuInterceptor();
	}

	@Bean
	public AdminMenuSecurityInterceptor adminMenuSecurityInterceptor() {
		return new AdminMenuSecurityInterceptor();
	}
}
