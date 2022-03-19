package org.training.upskilling.onlineshop.controller.product;

import org.training.upskilling.onlineshop.controller.order.Order;
import org.training.upskilling.onlineshop.security.service.SecurityService;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.ProductService;
import org.training.upskilling.onlineshop.service.dto.UserDto;
import org.training.upskilling.onlineshop.view.ViewGenerator;

public abstract class OrderServlet extends ProductServlet {

	protected final OrderService orderService;

	protected OrderServlet(SecurityService securityService, ProductService productService, OrderService orderService, ViewGenerator viewGenerator) {
		super(securityService, productService, viewGenerator, true);
		this.orderService = orderService;
	}
	
	protected void updateCartAttribute(UserDto user) {
		setTemplateVariable(CART_ATTRIBUTE, orderService.getActiveOrder(user).map(Order::toString).orElse(EMPTY_ORDER_MESSAGE));
	}

}
