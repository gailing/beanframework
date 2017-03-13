package com.beanframework.platform.sysconfig;

public final class SysConfigConstants {

	// ===============================
	// = CACHE
	// ===============================
	public static final String CACHE_SYSCONFIGS = "sysconfigs";

	// ===============================
	// = TABLE
	// ===============================
	public static final String TABLE_CONFIG = "config";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private SysConfigConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
