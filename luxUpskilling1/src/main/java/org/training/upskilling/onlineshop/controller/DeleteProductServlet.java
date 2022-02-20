package org.training.upskilling.onlineshop.controller;

import java.io.IOException;

import org.eclipse.jetty.http.HttpMethod;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteProductServlet extends AbstractServlet {
	
	public DeleteProductServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator, true);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp, HttpMethod.GET);
	}

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		deleteProduct(req);
		fetchAllProducts();
	}

	private void deleteProduct(HttpServletRequest req) throws ServletException {
		Long id = getProductIdFromPath(req);
		productService.delete(id);
	}

	@Override
	public String getDestination() {
		return "/products";
	}

}
