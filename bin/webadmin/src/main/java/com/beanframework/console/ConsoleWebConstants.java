package com.beanframework.console;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;


public class ConsoleWebConstants extends AdminBaseConstants{

	public static final String PATH_ROOT = PATH_CONSOLE;
	public static final String PATH_DASHBOARD = "/dashboard";
	public static final String PATH_LICENSE = "/license";
	public static final String PATH_INITIALIZE = "/initialize";
	public static final String PATH_LOGGING = "/logging";
	public static final String PATH_LOGGING_TAIL = "/logging/tail";
	public static final String PATH_LOGGING_DOWNLOAD = "/logging/download";
	public static final String PATH_LOGGING_LEVEL = "/logging/level";
	public static final String PATH_CACHE = "/cache";
	public static final String PATH_CACHE_CLEAR = "/cache/clear";
	public static final String PATH_SESSION = "/session";
	public static final String PATH_EXPIRE_USER = "/session/expireuser";
	
	public static final String PATH_API_ROOT = PATH_CONSOLE_API;
	public static final String PATH_API_LOGGING_TAIL = "/logging/tail";
	public static final String PATH_API_LOGGING_LEVEL = "/logging/level";
	public static final String PATH_API_MONITOR = "/monitor/cpu";
	
	public static final String PAGE_DASHBOARD = ThemeManager.getInstance().getAdminThemePath() + "/console/dashboard";
	public static final String PAGE_LICENSE = ThemeManager.getInstance().getAdminThemePath() + "/console/license";
	public static final String PAGE_INITIALIZE = ThemeManager.getInstance().getAdminThemePath() + "/console/initialize";
	public static final String PAGE_LOGGING = ThemeManager.getInstance().getAdminThemePath() + "/console/logging";
	public static final String PAGE_LOGGING_TAIL = ThemeManager.getInstance().getAdminThemePath() + "/console/loggingTail";
	public static final String PAGE_LOGGING_LEVEL = ThemeManager.getInstance().getAdminThemePath() + "/console/loggingLevel";
	public static final String PAGE_CACHE = ThemeManager.getInstance().getAdminThemePath() + "/console/cache";
	public static final String PAGE_SESSION = ThemeManager.getInstance().getAdminThemePath() + "/console/session";
}
