package com.beanframework.menu;

public final class MenuConstants {

	// ===============================
	// = CONFIGURATION
	// ===============================
	public static final String PERMISSIONS_SPLIT = ",";

	// ===============================
	// = CACHE
	// ===============================
	public static final String CACHE_MENUS = "menus";

	// ===============================
	// = TABLE
	// ===============================
	public static final String TABLE_MENU = "menu";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private MenuConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
