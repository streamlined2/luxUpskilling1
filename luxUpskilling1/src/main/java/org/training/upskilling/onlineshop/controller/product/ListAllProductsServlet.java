package org.training.upskilling.onlineshop.controller.product;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListAllProductsServlet extends ProductServlet {
	
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
