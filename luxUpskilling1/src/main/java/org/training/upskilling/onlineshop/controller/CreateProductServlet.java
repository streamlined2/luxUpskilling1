package org.training.upskilling.onlineshop.controller;

import java.io.IOException;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CreateProductServlet extends ProductServlet {

	public CreateProductServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.GET);
	}

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
