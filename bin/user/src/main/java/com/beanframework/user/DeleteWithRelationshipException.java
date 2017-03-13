package com.beanframework.user;

public class DeleteWithRelationshipException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeleteWithRelationshipException(String msg) {
		super(msg);
	}

	public DeleteWithRelationshipException(String msg, Throwable t) {
		super(msg, t);
	}
}