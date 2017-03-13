package com.beanframework.user;

public class NewAndConfirmPasswordNotMatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewAndConfirmPasswordNotMatchException(String msg) {
		super(msg);
	}

	public NewAndConfirmPasswordNotMatchException(String msg, Throwable t) {
		super(msg, t);
	}
}