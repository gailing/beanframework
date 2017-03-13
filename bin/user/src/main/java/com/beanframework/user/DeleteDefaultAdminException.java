package com.beanframework.user;

public class DeleteDefaultAdminException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeleteDefaultAdminException(String msg) {
		super(msg);
	}

	public DeleteDefaultAdminException(String msg, Throwable t) {
		super(msg, t);
	}
}