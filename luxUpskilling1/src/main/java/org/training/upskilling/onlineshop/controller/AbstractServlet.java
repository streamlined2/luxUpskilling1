package org.training.upskilling.onlineshop.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractServlet extends HttpServlet {

	protected static final String CONTEXT_PATH_ATTRIBUTE = "context";
	private static final String TEMPLATE_VARIABLES_ATTRIBUTE = "parameters";
	private static final String HTTP_METHOD_NOT_ALLOWED_FOR_SERVLET_MAPPING = "HTTP method %s not allowed for servlet mapping %s";

	protected final ViewGenerator viewGenerator;
	protected final boolean makeNewRequest;

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp, HttpMethod httpMethod)
			throws ServletException, IOException {
		setTemplateVariable(CONTEXT_PATH_ATTRIBUTE, getServletContext().getContextPath());
		boolean success = doWork(req, resp);
		if (makeNewRequest) {
			resp.sendRedirect(req.getContextPath() + getDestination(req, success));
		} else {
			resp.setContentType("text/html;charset=UTF-8");
			resp.setStatus(HttpServletResponse.SC_OK);
			viewGenerator.writeView(getDestination(req, success), getTemplateVariables(), resp.getWriter());
		}
	}

	protected abstract boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException;

	protected abstract String getDestination(HttpServletRequest req, boolean success) throws ServletException;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format(HTTP_METHOD_NOT_ALLOWED_FOR_SERVLET_MAPPING,
				HttpMethod.GET.name(), req.getServletPath()));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format(HTTP_METHOD_NOT_ALLOWED_FOR_SERVLET_MAPPING,
				HttpMethod.POST.name(), req.getServletPath()));
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format(HTTP_METHOD_NOT_ALLOWED_FOR_SERVLET_MAPPING,
				HttpMethod.PUT.name(), req.getServletPath()));
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format(HTTP_METHOD_NOT_ALLOWED_FOR_SERVLET_MAPPING,
				HttpMethod.DELETE.name(), req.getServletPath()));
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format(HTTP_METHOD_NOT_ALLOWED_FOR_SERVLET_MAPPING,
				HttpMethod.HEAD.name(), req.getServletPath()));
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format(HTTP_METHOD_NOT_ALLOWED_FOR_SERVLET_MAPPING,
				HttpMethod.OPTIONS.name(), req.getServletPath()));
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format(HTTP_METHOD_NOT_ALLOWED_FOR_SERVLET_MAPPING,
				HttpMethod.TRACE.name(), req.getServletPath()));
	}

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

}
