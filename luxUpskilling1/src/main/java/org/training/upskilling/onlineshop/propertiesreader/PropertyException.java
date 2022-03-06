package org.training.upskilling.onlineshop.propertiesreader;

public class PropertyException extends RuntimeException {

	public PropertyException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyException(String message) {
		super(message);
	}

}
