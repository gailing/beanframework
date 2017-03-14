package com.beanframework.theme.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

@Configuration
public class ThemeConfig {
	
	@Value(AdminBaseConstants.THEME_ADMIN)
	private String THEME_ADMIN;

	@Bean
	public ThemeManager themeManager(){
		ThemeManager.getInstance().setAdminThemePath(THEME_ADMIN);
		return ThemeManager.getInstance();
	}
}
