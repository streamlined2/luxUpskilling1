package org.training.upskilling.onlineshop.controller.security;

import org.training.upskilling.onlineshop.controller.AbstractServlet;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFormServlet extends AbstractServlet {
	
	private static final String HOME_URL = "/products";

	public LoginFormServlet(ViewGenerator viewGenerator) {
		super(viewGenerator, false);
	}

	@Override
	protected boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		Object targetUrl = req.getAttribute(TARGET_URL_ATTRIBUTE);
		setTemplateVariable(TARGET_URL_ATTRIBUTE, targetUrl == null ? HOME_URL: targetUrl);
		return true;
	}

	@Override
	protected String getDestination(HttpServletRequest req, boolean success) throws ServletException {
		return "login.ftl";
	}

}
