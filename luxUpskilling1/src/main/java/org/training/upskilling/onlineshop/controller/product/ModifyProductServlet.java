package org.training.upskilling.onlineshop.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ModifyProductServlet extends ProductServlet {
	
	@Override
	public boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		setTemplateVariable(CREATE_PRODUCT_FLAG_ATTRIBUTE, Boolean.FALSE);
		setTemplateVariable(PRODUCT_ATTRIBUTE, productService.findById(getProductIdFromPath(req)).orElseThrow());
		return true;
	}

	@Override
	public String getDestination(HttpServletRequest req, boolean success) {
		return "modify-product.ftl";
	}

}
