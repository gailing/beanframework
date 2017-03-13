package com.beanframework.theme;

public class ThemeManager {

	public static final String NAME = "ThemeManager";

	private static ThemeManager instance;

	private String adminThemePath = "adminlte";

	private ThemeManager() {
	}

	public static synchronized ThemeManager getInstance() {
		if (instance == null) {
			instance = new ThemeManager();
		}

		return instance;
	}

	public String getAdminThemePath() {
		return adminThemePath;
	}
}
