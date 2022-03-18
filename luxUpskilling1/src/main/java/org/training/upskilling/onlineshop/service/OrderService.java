package org.training.upskilling.onlineshop.service;

import org.training.upskilling.onlineshop.service.dto.UserDto;

public interface OrderService {
	
	void createOrder(UserDto user);
	void orderProduct(long productId, int count);
	void declineProduct(long productId, int count);
	
}
