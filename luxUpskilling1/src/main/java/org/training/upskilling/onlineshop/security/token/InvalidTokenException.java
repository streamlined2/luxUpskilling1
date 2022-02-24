package org.training.upskilling.onlineshop.security.token;

public class InvalidTokenException extends RuntimeException {

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTokenException(String message) {
		super(message);
	}

}
