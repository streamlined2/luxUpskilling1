package org.training.upskilling.onlineshop.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateProductServlet extends ProductServlet {

	@Override
	public boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		setTemplateVariable(CREATE_PRODUCT_FLAG_ATTRIBUTE, Boolean.TRUE);
		return true;
	}

	@Override
	public String getDestination(HttpServletRequest req, boolean success) {
		return "new-product.ftl";
	}

}
