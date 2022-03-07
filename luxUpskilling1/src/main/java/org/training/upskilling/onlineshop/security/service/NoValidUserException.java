package org.training.upskilling.onlineshop.security.service;

public class NoValidUserException extends RuntimeException {

	public NoValidUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoValidUserException(String message) {
		super(message);
	}

	public NoValidUserException() {
		super("no valid user data present");
	}

}
