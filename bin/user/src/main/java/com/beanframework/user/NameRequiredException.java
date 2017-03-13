package com.beanframework.user;

public class NameRequiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameRequiredException(String msg) {
		super(msg);
	}

	public NameRequiredException(String msg, Throwable t) {
		super(msg, t);
	}
}