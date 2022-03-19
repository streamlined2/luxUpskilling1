package org.training.upskilling.onlineshop.controller.security;

import org.training.upskilling.onlineshop.controller.AbstractServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFormServlet extends AbstractServlet {
	
	public LoginFormServlet() {
		super(false);
	}

	@Override
	protected boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		setTargetVariable(req);
		return true;
	}

	@Override
	protected String getDestination(HttpServletRequest req, boolean success) throws ServletException {
		return "login.ftl";
	}

}
