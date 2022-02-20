package org.training.upskilling.onlineshop.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AbstractServlet extends HttpServlet {

	protected static final String CONTEXT_PATH_ATTRIBUTE = "context";
	protected static final String TEMPLATE_VARIABLES_ATTRIBUTE = "parameters";
	protected static final String PRODUCTS_ATTRIBUTE = "products";
	protected static final String CREATE_PRODUCT_FLAG_ATTRIBUTE = "createProduct";
	protected static final String PRODUCT_ATTRIBUTE = "product";
	protected static final String PRODUCT_ID_PARAMETER = "id";
	protected static final String PRODUCT_NAME_PARAMETER = "name";
	protected static final String PRODUCT_PRICE_PARAMETER = "price";
	protected static final String PRODUCT_CREATION_DATE_PARAMETER = "creationDate";

	protected final boolean modifying;
	protected final ProductService productService;
	protected final ViewGenerator viewGenerator;

	protected AbstractServlet(ProductService productService, ViewGenerator viewGenerator) {
		this(productService, viewGenerator, false);
	}

	protected AbstractServlet(ProductService productService, ViewGenerator viewGenerator, boolean modifying) {
		this.productService = productService;
		this.viewGenerator = viewGenerator;
		this.modifying = modifying;
	}

	protected abstract void doWork(HttpServletRequest req) throws ServletException;

	protected abstract String getDestination();

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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format("HTTP method %s not allowed for servlet mapping %s",
				HttpMethod.GET.name(), req.getServletPath()));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format("HTTP method %s not allowed for servlet mapping %s",
				HttpMethod.POST.name(), req.getServletPath()));
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format("HTTP method %s not allowed for servlet mapping %s",
				HttpMethod.PUT.name(), req.getServletPath()));
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException(String.format("HTTP method %s not allowed for servlet mapping %s",
				HttpMethod.DELETE.name(), req.getServletPath()));
	}

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp, HttpMethod httpMethod)
			throws ServletException, IOException {
		setTemplateVariable(CONTEXT_PATH_ATTRIBUTE, getServletContext().getContextPath());
		if (modifying) {
			doWork(req);
			resp.sendRedirect(req.getContextPath() + getDestination());
		} else {
			resp.setContentType("text/html;charset=UTF-8");
			resp.setStatus(HttpServletResponse.SC_OK);
			doWork(req);
			resp.getWriter().append(viewGenerator.getView(getDestination(), getTemplateVariables()));
		}
	}

	protected void fetchAllProducts() {
		setTemplateVariable(PRODUCTS_ATTRIBUTE, productService.getAll());
	}

	protected Long getProductIdFromPath(HttpServletRequest req) throws ServletException {
		String pathInfo = req.getPathInfo();
		int pos = pathInfo.lastIndexOf("/");
		if (pos == -1 || pos == pathInfo.length() - 1) {
			throw new ServletException("product id not present in path");
		}
		return Long.valueOf(pathInfo.substring(pos + 1));
	}

	protected Long getProductIdFromRequest(HttpServletRequest req) throws ServletException {
		return Long.valueOf(getRequestParameter(req, PRODUCT_ID_PARAMETER, "missing entity id parameter"));
	}

	protected String getRequestParameter(HttpServletRequest req, String paramName, String exceptionMessage)
			throws ServletException {
		String parameter = req.getParameter(paramName);
		if (parameter == null) {
			throw new ServletException(exceptionMessage);
		}
		return parameter;
	}

	protected Object getTemplateVariable(String attrName, String exceptionMessage) throws ServletException {
		Object variable = getTemplateVariable(attrName);
		if (variable == null) {
			throw new ServletException(exceptionMessage);
		}
		return variable;
	}

}
