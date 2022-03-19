package org.training.upskilling.onlineshop.controller.product;

import org.training.upskilling.onlineshop.ServiceLocator;
import org.training.upskilling.onlineshop.controller.order.Order;
import org.training.upskilling.onlineshop.service.OrderService;
import org.training.upskilling.onlineshop.service.dto.UserDto;

public abstract class OrderServlet extends ProductServlet {

	protected final OrderService orderService = ServiceLocator.getInstance(OrderService.class);

	protected OrderServlet() {
		super(true);
	}
	
	protected void updateCartAttribute(UserDto user) {
		setTemplateVariable(CART_ATTRIBUTE, orderService.getActiveOrder(user).map(Order::toString).orElse(EMPTY_ORDER_MESSAGE));
	}

}
