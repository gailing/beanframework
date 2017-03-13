package com.beanframework.email;

import com.beanframework.common.AdminBaseConstants;

public final class EmailWebConstant extends AdminBaseConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT = PATH_ADMIN + "/email";
	public static final String PATH_LIST = "/list";

	// ===============================
	// = API
	// ===============================
	public static final String PATH_API_ROOT = PATH_ADMIN_API + "/email";
	public static final String PATH_API_LIST = "/list";
	public static final String PATH_API_SAVE = "/save";
	public static final int API_LIST_SIZE = 100;

	// ===============================
	// = PAGE
	// ===============================

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private EmailWebConstant() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
