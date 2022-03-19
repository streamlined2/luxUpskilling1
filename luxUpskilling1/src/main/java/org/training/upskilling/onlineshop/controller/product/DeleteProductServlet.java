package org.training.upskilling.onlineshop.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteProductServlet extends ProductServlet {
	
	public DeleteProductServlet() {
		super(true);
	}

	@Override
	public boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		deleteProduct(req);
		fetchAllProducts();
		return true;
	}

	private void deleteProduct(HttpServletRequest req) throws ServletException {
		Long id = getProductIdFromPath(req);
		productService.delete(id);
	}

	@Override
	public String getDestination(HttpServletRequest req, boolean success) {
		return "/products";
	}

}
