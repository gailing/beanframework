package com.beanframework.user;

public class ForceLogoutUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForceLogoutUserException(String msg) {
		super(msg);
	}

	public ForceLogoutUserException(String msg, Throwable t) {
		super(msg, t);
	}
}