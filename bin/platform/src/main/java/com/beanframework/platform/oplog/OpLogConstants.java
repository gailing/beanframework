package com.beanframework.platform.oplog;

public final class OpLogConstants {

	// ===============================
	// = CONFIGURATION
	// ===============================
	public static final int DEFAULT_LIMIT_LOG = 1000;

	// ===============================
	// = TABLE
	// ===============================
	public static final String TABLE_LOG = "oplog";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private OpLogConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
