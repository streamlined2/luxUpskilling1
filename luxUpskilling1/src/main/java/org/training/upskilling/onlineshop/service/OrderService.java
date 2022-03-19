package org.training.upskilling.onlineshop.service;

import java.util.Optional;

import org.training.upskilling.onlineshop.controller.order.Order;
import org.training.upskilling.onlineshop.service.dto.UserDto;

public interface OrderService {
	
	void createOrder(UserDto user);
	Optional<Order> getActiveOrder(UserDto user);
	void orderProduct(UserDto user, long productId, int count);
	void declineProduct(UserDto user, long productId, int count);
	
}
