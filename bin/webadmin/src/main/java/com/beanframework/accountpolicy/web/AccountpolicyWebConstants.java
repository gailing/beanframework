package com.beanframework.accountpolicy.web;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

public final class AccountpolicyWebConstants extends AdminBaseConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT = PATH_ADMIN + "/accountpolicy";
	public static final String PATH_FORM = "/form";
	public static final String PATH_SAVE = "/save";
	public static final String PATH_MENU = PATH_ROOT;

	// ===============================
	// = PAGE
	// ===============================
	public static final String PAGE = ThemeManager.getInstance().getAdminThemePath() + "/accountpolicy/accountpolicy";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private AccountpolicyWebConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
