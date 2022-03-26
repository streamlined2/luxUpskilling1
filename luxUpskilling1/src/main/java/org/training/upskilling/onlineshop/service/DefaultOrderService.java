package org.training.upskilling.onlineshop.service;

import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Service;
import org.training.upskilling.onlineshop.controller.order.Order;
import org.training.upskilling.onlineshop.service.dto.ProductDto;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultOrderService implements OrderService {

	private static final String EMPTY_ORDER_MESSAGE = "Empty cart";

	private final ProductService productService;

	private final Deque<Order> orders = new ConcurrentLinkedDeque<>();

	@Override
	public void createOrder(UserDto user) {
		orders.addLast(new Order(user));
	}

	@Override
	public void orderProduct(UserDto user, long productId, int count) {
		Optional<ProductDto> product = productService.findById(productId);
		Optional<Order> order = getActiveOrder(user);
		if (product.isPresent() && order.isPresent()) {
			order.get().add(product.get(), count);
		}
	}

	@Override
	public void declineProduct(UserDto user, long productId, int count) {
		Optional<ProductDto> product = productService.findById(productId);
		Optional<Order> order = getActiveOrder(user);
		if (product.isPresent() && order.isPresent()) {
			order.get().subtract(product.get(), count);
		}
	}

	@Override
	public Optional<Order> getActiveOrder(UserDto user) {
		var i = orders.descendingIterator();
		while(i.hasNext()) {
			var order = i.next();
			if(order.getUser().equals(user)) {
				return Optional.of(order);
			}
		}
		return Optional.empty();
	}

	@Override
	public String getActiveOrderRepresentation(UserDto user) {
		return getActiveOrder(user).map(Order::toString).orElse(EMPTY_ORDER_MESSAGE);
	}

}
