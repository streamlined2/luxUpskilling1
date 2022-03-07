package org.training.upskilling.onlineshop.security.token;

public class TokenConversionException extends RuntimeException {

	public TokenConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenConversionException(String message) {
		super(message);
	}

}
