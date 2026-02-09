package com.revpay.exception;

/*
 * Custom business exception
 * Thrown when a user tries to register with an email or phone
 * that already exits.
 */

public class DuplicateUserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DuplicateUserException(String message) {
		super(message);
	}
	

}
