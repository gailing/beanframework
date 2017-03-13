package com.beanframework.platform;

public class PlatformConstants {

	public static final String LOCALE_PARAM = "${locale.param}";

	public static final String SPRING_MESSAGE_CLASSPATH = "${spring.messages.classpath}";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private PlatformConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
