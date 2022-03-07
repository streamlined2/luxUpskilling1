package org.training.upskilling.onlineshop.controller.product;

import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListAllProductsServlet extends ProductServlet {
	
	public ListAllProductsServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator);
	}

	@Override
	public boolean doWork(HttpServletRequest req, HttpServletResponse resp) {
		fetchAllProducts();
		return true;
	}

	@Override
	public String getDestination(HttpServletRequest req, boolean success) {
		return "product-list.ftl";
	}

}
