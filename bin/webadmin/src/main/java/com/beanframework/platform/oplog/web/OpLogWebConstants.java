package com.beanframework.platform.oplog.web;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

public final class OpLogWebConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT = AdminBaseConstants.PATH_ADMIN+"/oplog";
	public static final String PATH_LIST = "/list";
	public static final String PATH_MENU = PATH_ROOT + PATH_LIST;
	public static final int PAGE_LIST_SIZE = 20;

	// ===============================
	// = PAGE
	// ===============================
	public static final String PAGE_LIST = ThemeManager.getInstance().getAdminThemePath() + "/oplog/logList";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private OpLogWebConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
