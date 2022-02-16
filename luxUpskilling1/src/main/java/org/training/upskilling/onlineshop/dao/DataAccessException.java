package org.training.upskilling.onlineshop.dao;

public class DataAccessException extends RuntimeException {

	public DataAccessException(String msg) {
		super(msg);
	}

	public DataAccessException(String msg, Throwable e) {
		super(msg, e);
	}

}
