package org.training.upskilling.onlineshop.controller.product;

import org.training.upskilling.onlineshop.controller.AbstractServlet;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public abstract class ProductServlet extends AbstractServlet {

	protected static final String PRODUCTS_ATTRIBUTE = "products";
	protected static final String CREATE_PRODUCT_FLAG_ATTRIBUTE = "createProduct";
	protected static final String PRODUCT_ATTRIBUTE = "product";
	protected static final String PRODUCT_ID_PARAMETER = "id";
	protected static final String PRODUCT_NAME_PARAMETER = "name";
	protected static final String PRODUCT_PRICE_PARAMETER = "price";
	protected static final String PRODUCT_CREATION_DATE_PARAMETER = "creationDate";
	protected static final String EMPTY_ORDER_MESSAGE = "Void order";

	protected final ProductService productService;

	protected ProductServlet(SecurityService securityService, ProductService productService, ViewGenerator viewGenerator) {
		super(securityService, viewGenerator, false);
		this.productService = productService;
	}

	protected ProductServlet(SecurityService securityService, ProductService productService, ViewGenerator viewGenerator, boolean modifying) {
		super(securityService, viewGenerator, modifying);
		this.productService = productService;
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
	
}
