package com.beanframework.filemanager.web;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

public class FilemanagerWebConstants extends AdminBaseConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT = PATH_ADMIN + "/filemanager";
	public static final String PATH_TEMPLATES = "/angularfilemanager/templates/{page}";
	public static final String PATH_ANGULARFILEMANAGER = "/angularfilemanager";
	
	public static final String PATH_API_ROOT = PATH_ADMIN_API + "/filemanager";
	public static final String PATH_API_ANGULARFILEMANAGER = "/angularfilemanager";

	// ===============================
	// = PAGE
	// ===============================
	public static final String PAGE_CONTAINER = ThemeManager.getInstance().getAdminThemePath() + "/filemanager/filemanagercontainer";
	public static final String PAGE_TEMPLATES = ThemeManager.getInstance().getAdminThemePath() + "/filemanager/templates";
	public static final String PAGE_ANGULARFILEMANAGER = ThemeManager.getInstance().getAdminThemePath() + "/filemanager/angularfilemanager";

}
