package org.training.upskilling.onlineshop.controller.product;

import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteProductFromOrderServlet extends ProductServlet {

	private final OrderService orderService;

	public DeleteProductFromOrderServlet(SecurityService securityService, OrderService orderService,
			ProductService productService, ViewGenerator viewGenerator) {
		super(securityService, productService, viewGenerator, true);
		this.orderService = orderService;
	}

	@Override
	public boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		long productId = getProductIdFromPath(req);
		orderService.declineProduct(productId, 1);
		return true;
	}

	@Override
	public String getDestination(HttpServletRequest req, boolean success) {
		return HOME_URL;
	}

}
