package org.training.upskilling.onlineshop.controller;

import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.http.HttpServletRequest;

public class ListAllProductsServlet extends AbstractServlet {
	
	public ListAllProductsServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator);
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
