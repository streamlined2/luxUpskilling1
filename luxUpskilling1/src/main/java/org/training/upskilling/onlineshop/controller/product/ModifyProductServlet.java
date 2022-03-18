package org.training.upskilling.onlineshop.controller.product;

import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ModifyProductServlet extends ProductServlet {
	
	public ModifyProductServlet(SecurityService securityService, ProductService productService, ViewGenerator viewGenerator) {
		super(securityService, productService, viewGenerator);
	}

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
