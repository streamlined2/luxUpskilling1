package org.training.upskilling.onlineshop.controller;

import java.io.IOException;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFormServlet extends AbstractServlet {

	public LoginFormServlet(ViewGenerator viewGenerator) {
		super(viewGenerator, false);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.GET);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.POST);
	}

	@Override
	protected boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		setTemplateVariable(TARGET_URL_ATTRIBUTE, req.getAttribute(TARGET_URL_ATTRIBUTE));
		return true;
	}

	@Override
	protected String getDestination(HttpServletRequest req, boolean success) throws ServletException {
		return "login.ftl";
	}

}
