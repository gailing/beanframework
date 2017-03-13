package com.beanframework.user.web;

import com.beanframework.common.AdminBaseConstants;
import com.beanframework.theme.ThemeManager;

public final class ResetPasswordWebConstants {

	// ===============================
	// = PATH
	// ===============================
	public static final String PATH_ROOT_RESET_PASSWORD = AdminBaseConstants.PATH_ADMIN+"/resetpassword";
	public static final String PATH_SEND = "/send";
	public static final String PATH_RESET = "/reset";

	public static final String PATH_RESET_PASSWORD = "/resetpassword/reset";

	// ===============================
	// = PAGE
	// ===============================
	public static final String PAGE_RESET_PASSWORD = ThemeManager.getInstance().getAdminThemePath()
			+ "/user/resetPassword";

	public static final String PAGE_RESET_PASSWORD_TOKEN = ThemeManager.getInstance().getAdminThemePath()
			+ "/user/resetPasswordtoken";

	// ===============================
	// = RESET PASSWORD SYSTEM
	// ===============================
	public static final String SYSTEM_RESET_PASSWORD_CHANNEL = "Reset Password";
	public static final String SYSTEM_RESET_PASSWORD_OPERATE = "Send New Password Email";
	public static final String SYSTEM_RESET_PASSWORD_EMAIL_QUEUE = "Queue up to send a reset password email to [{0}]";
	public static final String SYSTEM_RESET_PASSWORD_EMAIL_NOT_EXISTS = "Email [{0}] not exists to send a reset password";
	// public static final String RESET_PASSWORD_EMAIL_SUBJECT = "Reset
	// Password";
	public static final String RESET_PASSWORD_TEMPLATE = ThemeManager.getInstance().getAdminThemePath()
			+ "/email/emailResetPassword.html";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private ResetPasswordWebConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
