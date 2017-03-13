package com.beanframework.user.web;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

public final class RoleWebConstants extends AdminBaseConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT = PATH_ADMIN + "/user/role";
	public static final String PATH_LIST = "/list";
	public static final String PATH_ADD = "/add";
	public static final String PATH_EDIT = "/edit";
	public static final String PATH_SAVE = "/save";
	public static final String PATH_DELETE = "/delete";
	public static final String PATH_REMOVE_USER = "/removeuser";
	public static final String PATH_REMOVE_GROUP = "/removegroup";
	public static final String PATH_MENU = PATH_ROOT + PATH_LIST;

	// ===============================
	// = API
	// ===============================
	public static final String PATH_API_ROOT = PATH_ADMIN_API + "/user/role";
	public static final String PATH_PERMISSION = "/permission";
	public static final String PATH_API_CHECK_NAME = "/checkname";

	// ===============================
	// = PAGE
	// ===============================
	public static final int PAGE_LIST_SIZE = 10;
	public static final String PAGE_LIST = ThemeManager.getInstance().getAdminThemePath() + "/user/roleList";
	public static final String PAGE_FORM = ThemeManager.getInstance().getAdminThemePath() + "/user/roleForm";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private RoleWebConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
