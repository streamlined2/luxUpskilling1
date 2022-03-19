package org.training.upskilling.onlineshop.controller.product;

import java.util.Optional;

import org.training.upskilling.onlineshop.controller.Utilities;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.service.dto.UserDto;
import org.training.upskilling.onlineshop.view.ViewGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddProductToOrderServlet extends OrderServlet {

	private final OrderService orderService;

	public AddProductToOrderServlet(SecurityService securityService, OrderService orderService,
			ProductService productService, ViewGenerator viewGenerator) {
		super(securityService, productService, orderService, viewGenerator);
		this.orderService = orderService;
	}

	@Override
	public boolean doWork(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		Optional<UserDto> user = securityService.getUser(Utilities.getTokenCookieValue(req));
		if(user.isPresent()) {
			orderService.orderProduct(user.get(), getProductIdFromPath(req), 1);
			updateCartAttribute(user.get());
			return true;
		}
		return false;
	}

	@Override
	public String getDestination(HttpServletRequest req, boolean success) {
		return HOME_URL;
	}

}
