package org.training.upskilling.onlineshop.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractServlet extends HttpServlet {

	public static final String TARGET_URL_ATTRIBUTE = "targetUrl";
	public static final String USER_TOKEN_COOKIE_NAME = "user-token";
	protected static final String CONTEXT_PATH_ATTRIBUTE = "context";
	private static final String TEMPLATE_VARIABLES_ATTRIBUTE = "parameters";

	protected final ViewGenerator viewGenerator;
	protected final boolean makeNewRequest;

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean success = doWork(req, resp);
		if (makeNewRequest) {
			resp.sendRedirect(req.getContextPath() + getDestination(req, success));
		} else {
			setTemplateVariable(CONTEXT_PATH_ATTRIBUTE, getServletContext().getContextPath());
			resp.setContentType("text/html;charset=UTF-8");
			resp.setStatus(HttpServletResponse.SC_OK);
			viewGenerator.writeView(getDestination(req, success), getTemplateVariables(), resp.getWriter());
		}
	}

	protected abstract boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException;

	protected abstract String getDestination(HttpServletRequest req, boolean success) throws ServletException;

	protected String getRequestParameter(HttpServletRequest req, String paramName, String exceptionMessage)
			throws ServletException {
		String parameter = req.getParameter(paramName);
		if (parameter == null) {
			throw new ServletException(exceptionMessage);
		}
		return parameter;
	}

	protected Map<String, Object> getTemplateVariables() {
		Map<String, Object> attributes = (Map<String, Object>) getServletContext()
				.getAttribute(TEMPLATE_VARIABLES_ATTRIBUTE);
		if (attributes == null) {
			attributes = new HashMap<>();
			getServletContext().setAttribute(TEMPLATE_VARIABLES_ATTRIBUTE, attributes);
		}
		return attributes;
	}

	protected Object getTemplateVariable(String name) {
		return getTemplateVariables().get(name);
	}

	protected void setTemplateVariable(String name, Object value) {
		getTemplateVariables().put(name, value);
	}

	protected Object getTemplateVariable(String attrName, String exceptionMessage) throws ServletException {
		Object variable = getTemplateVariable(attrName);
		if (variable == null) {
			throw new ServletException(exceptionMessage);
		}
		return variable;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

}
