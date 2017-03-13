package com.beanframework.email;

public final class EmailConstant {

	// ===============================
	// = MESSAGE
	// ===============================
	public static final String SYSTEM_CHANNEL = "Email Service";
	public static final String SYSTEM_OPERATE_SEND_EMAIL = "Send Email";
	public static final String SYSTEM_OPERATE_SEND_EMAIL_RESULT = "{0} sent, {1} failed";

	// ===============================
	// = Email Process
	// ===============================
	public static final int numberOfEmailToProcess = 100;

	// ===============================
	// = PAGE
	// ===============================

	// ===============================
	// = TABLE
	// ===============================
	public static final String TABLE_EMAIL = "email";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private EmailConstant() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
