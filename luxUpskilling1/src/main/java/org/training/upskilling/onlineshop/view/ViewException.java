package org.training.upskilling.onlineshop.view;

public class ViewException extends RuntimeException {

	public ViewException(String msg) {
		super(msg);
	}

	public ViewException(Exception e) {
		super(e);
	}

}
