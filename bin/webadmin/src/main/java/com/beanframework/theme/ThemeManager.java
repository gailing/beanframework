package com.beanframework.theme;

public class ThemeManager {

	public static final String NAME = "ThemeManager";

	private static ThemeManager instance = new ThemeManager();

	private String adminThemePath = "";

	private ThemeManager() {
	}

	public static ThemeManager getInstance() {
       return instance;
	}

	public String getAdminThemePath() {		
		return adminThemePath;
	}

	public void setAdminThemePath(String adminThemePath) {
		this.adminThemePath = adminThemePath;
	}

}
