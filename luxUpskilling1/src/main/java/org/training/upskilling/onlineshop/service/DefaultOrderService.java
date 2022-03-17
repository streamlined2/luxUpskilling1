package org.training.upskilling.onlineshop.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.training.upskilling.onlineshop.controller.order.Order;
import org.training.upskilling.onlineshop.service.dto.UserDto;

public class DefaultOrderService implements OrderService {
	
	private final Queue<Order> orders = new ConcurrentLinkedDeque<>();

	@Override
	public void addOrder(UserDto user) {
		orders.add(new Order(user));
	}

}
