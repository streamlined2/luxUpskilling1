package org.training.upskilling.onlineshop.controller;

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

	protected final ProductService productService;

	protected ProductServlet(ProductService productService, ViewGenerator viewGenerator) {
		this(productService, viewGenerator, false);
	}

	protected ProductServlet(ProductService productService, ViewGenerator viewGenerator, boolean modifying) {
		super(viewGenerator, modifying);
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
