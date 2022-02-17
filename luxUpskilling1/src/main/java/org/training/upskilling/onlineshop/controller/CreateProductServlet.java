package org.training.upskilling.onlineshop.controller;

import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class CreateProductServlet extends AbstractServlet {

	public CreateProductServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator);
	}

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		setTemplateVariable(CREATE_PRODUCT_FLAG_ATTRIBUTE, Boolean.TRUE);
	}

	@Override
	public String getDestination() {
		return "new-product.ftl";
	}

}
