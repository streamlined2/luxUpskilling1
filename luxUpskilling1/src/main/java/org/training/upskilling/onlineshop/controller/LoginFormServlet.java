package org.training.upskilling.onlineshop.controller;

import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFormServlet extends AbstractServlet {

	public LoginFormServlet(ViewGenerator viewGenerator) {
		super(viewGenerator, false);
	}

	@Override
	protected boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		return true;
	}

	@Override
	protected String getDestination(HttpServletRequest req, boolean success) throws ServletException {
		return "login.ftl";
	}

}
