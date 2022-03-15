package org.training.upskilling.onlineshop.dao;

public class DataConversionException extends DataAccessException {

	public DataConversionException(String msg, Throwable e) {
		super(msg, e);
	}

	public DataConversionException(String msg) {
		super(msg);
	}

}
