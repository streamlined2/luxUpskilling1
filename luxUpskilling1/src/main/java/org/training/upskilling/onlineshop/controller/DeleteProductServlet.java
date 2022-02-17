package org.training.upskilling.onlineshop.controller;

import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.service.dto.ProductDto;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteProductServlet extends AbstractServlet {
	
	public DeleteProductServlet(ProductService productService, ViewGenerator viewGenerator) {
		super(productService, viewGenerator, true);
	}

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		deleteProduct(req);
		fetchAllProducts();
	}

	private void deleteProduct(HttpServletRequest req) throws ServletException {
		ProductDto toDelete = getProductFromList(req);
		productService.delete(toDelete.id());
	}

	@Override
	public String getDestination() {
		return "/products";
	}

}
