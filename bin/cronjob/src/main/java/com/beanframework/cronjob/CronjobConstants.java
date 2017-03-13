package com.beanframework.cronjob;

public final class CronjobConstants {
	
	public static final String QUARTZ_PROPERTIES_CLASSPATH = "${quartz.properties.classpath}";

	// ===============================
	// = TABLE
	// ===============================
	public static final String TABLE_CRONJOB = "cronjob";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private CronjobConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
