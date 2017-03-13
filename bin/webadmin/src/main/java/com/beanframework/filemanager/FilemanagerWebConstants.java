package com.beanframework.filemanager;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

public class FilemanagerWebConstants extends AdminBaseConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT = PATH_ADMIN + "/filemanager";
	public static final String PATH_API_ROOT = PATH_ADMIN_API + "/filemanager";

	// ===============================
	// = PAGE
	// ===============================
	public static final String PAGE = ThemeManager.getInstance().getAdminThemePath() + "/filemanager/filemanager";

}
