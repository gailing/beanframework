package com.beanframework.menu;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

public final class MenuWebConstants extends AdminBaseConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT = PATH_ADMIN + "/menu";
	public static final String PATH_FORM = "/form";
	public static final String PATH_SAVE = "/save";
	public static final String PATH_MOVE = "/move";
	public static final String PATH_DELETE = "/delete";
	public static final String PATH_MENU = PATH_ROOT;

	// ===============================
	// = API
	// ===============================
	public static final String PATH_API_ROOT = PATH_ADMIN_API + "/menu";

	// ===============================
	// = PAGE
	// ===============================
	public static final String PAGE = ThemeManager.getInstance().getAdminThemePath() + "/menu/menu";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private MenuWebConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
