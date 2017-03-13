package com.beanframework.user.web;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

public final class UserWebConstants extends AdminBaseConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT = PATH_ADMIN + "/user";
	public static final String PATH_LIST = "/list";
	public static final String PATH_ADD = "/add";
	public static final String PATH_EDIT = "/edit";
	public static final String PATH_SAVE = "/save";
	public static final String PATH_DELETE = "/delete";

	public static final String PATH_LOGOUT_USER = "/logoutuser";
	public static final String PATH_MENU = PATH_ROOT + PATH_LIST;

	public static final String PATH_ROOT_PROFILE = PATH_ADMIN + "/profile";
	public static final String PATH_PROFILE_SAVE = "/save";

	// ===============================
	// = API
	// ===============================
	public static final String PATH_API_ROOT = PATH_ADMIN_API + "/user";
	public static final String PATH_API_CHECK_USERNAME = "/checkusername";
	public static final String PATH_API_CHECK_EMAIL = "/checkemail";

	// ===============================
	// = PAGE
	// ===============================
	public static final int PAGE_LIST_SIZE = 10;
	public static final String PAGE_LIST = ThemeManager.getInstance().getAdminThemePath() + "/user/userList";
	public static final String PAGE_FORM = ThemeManager.getInstance().getAdminThemePath() + "/user/userForm";
	public static final String PAGE_PROFILE = ThemeManager.getInstance().getAdminThemePath() + "/user/profile";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private UserWebConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
