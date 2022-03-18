package org.training.upskilling.onlineshop.service;

import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.training.upskilling.onlineshop.controller.order.Order;
import org.training.upskilling.onlineshop.service.dto.ProductDto;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {
	
	private final ProductService productService;
	
	private final Deque<Order> orders = new ConcurrentLinkedDeque<>();

	@Override
	public void createOrder(UserDto user) {
		orders.addLast(new Order(user));
	}

	@Override
	public void orderProduct(long productId, int count) {
		Order order = orders.removeLast();
		if(order != null) {
			Optional<ProductDto> product = productService.findById(productId);
			if(product.isPresent()) {
				order.add(product.get(), count);
				orders.addLast(order);
			}
		}
	}

	@Override
	public void declineProduct(long productId, int count) {
		Order order = orders.removeLast();
		if(order != null) {
			Optional<ProductDto> product = productService.findById(productId);
			if(product.isPresent()) {
				order.subtract(product.get(), count);
				orders.addLast(order);
			}
		}
	}

}
