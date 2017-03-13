package com.beanframework.user;

public final class UserConstants {

	// ===============================
	// = CACHE
	// ===============================
	public static final String CACHE_USERS = "users";
	public static final String CACHE_GROUPS = "groups";
	public static final String CACHE_ROLES = "roles";

	// ===============================
	// = SECURITY
	// ===============================
	public static final int LIMIT_LOGIN_ATTEMPTS = 10;

	// ===============================
	// = TABLE
	// ===============================
	public static final String TABLE_USER = "user";
	public static final String TABLE_ROLE = "role";
	public static final String TABLE_PERMISSION = "permission";
	public static final String TABLE_GROUP = "usergroup";
	public static final String TABLE_USER_ROLE = "user_role";
	public static final String TABLE_USER_GROUP = "user_usergroup";
	public static final String TABLE_GROUP_ROLE = "usergroup_role";
	public static final String TABLE_ROLE_PERMISSION = "role_permission";
	public static final String TABLE_USER_POLICY = "user_policy";
	public static final String TABLE_PASSWORD_TOKEN = "password_token";

	// ===============================
	// = MESSAGE
	// ===============================
	public static final String SYSTEM_SECURITY_CHANNEL = "User Security";
	public static final String SYSTEM_SECURITY_OPERATE_LOGIN = "Login";
	public static final String SYSTEM_SECURITY_USERNAME_NOT_FOUND = "User [{0}] login failed, username not found";
	public static final String SYSTEM_SECURITY_PASSWORD_WRONG = "User [{0}] login failed, wrong password";
	public static final String SYSTEM_SECURITY_LOGIN_SUCCESS = "User [{0}] logon successful";
	public static final String SYSTEM_SECURITY_ACCOUNT_DISABLED = "User [{0}] login failed, account is disabled";
	public static final String SYSTEM_SECURITY_ACCOUNT_NON_EXPIRED = "User [{0}] login failed, account is expired";
	public static final String SYSTEM_SECURITY_ACCOUNT_NON_LOCKED = "User [{0}] login failed, account is locked";
	public static final String SYSTEM_SECURITY_ACCOUNT_CREDENTIALS_NON_EXPIRED = "User [{0}] login failed, password is expired";
	public static final String MESSAGE_WRONG_USERNAME = "Username not found";
	public static final String MESSAGE_WRONG_PASSWORD = "Wrong password";
	public static final String MESSAGE_ACCOUNT_DISABLED = "Your account has been disabled";
	public static final String MESSAGE_ACCOUNT_NON_EXPIRED = "Your account has been expired";
	public static final String MESSAGE_ACCOUNT_NON_LOCKED = "Your account has been locked";
	public static final String MESSAGE_ACCOUNT_CREDENTIALS_NON_EXPIRED = "Your password has been expired";
	
	
	// ===============================
	// = SESSION
	// ===============================
	public static final String MAX_SESSION_USER = "${max.session.user}";
	public static final String MAX_SESSION_PREVENTS_LOGIN = "${max.session.prevents.login}";

	// PRIVATE //

	/**
	 * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
	 * and so on. Thus, the caller should be prevented from constructing objects
	 * of this class, by declaring this private constructor.
	 */
	private UserConstants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}
}
