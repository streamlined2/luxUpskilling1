package org.training.upskilling.onlineshop.controller;

import java.io.IOException;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListAllProductsServlet extends AbstractServlet {
	
	public ListAllProductsServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.GET);
	}

	@Override
	public void doWork(HttpServletRequest req) {
		fetchAllProducts();
	}

	@Override
	public String getDestination() {
		return "product-list.ftl";
	}

}
