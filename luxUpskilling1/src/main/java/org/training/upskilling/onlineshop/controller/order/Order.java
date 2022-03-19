package org.training.upskilling.onlineshop.controller.order;

import java.time.LocalDateTime;
import static java.lang.Math.max;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map.Entry;

import org.training.upskilling.onlineshop.service.dto.ProductDto;
import org.training.upskilling.onlineshop.service.dto.UserDto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Order implements Iterable<Entry<ProductDto, Integer>> {

	private final Map<ProductDto, Integer> items;
	private final LocalDateTime creationTime;
	private final UserDto user;

	public Order(UserDto user) {
		this.user = user;
		items = new HashMap<>();
		creationTime = LocalDateTime.now();
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public UserDto getUser() {
		return user;
	}

	public void add(ProductDto product, int count) {
		items.merge(product, count, (a, b) -> a + b);
	}

	public void subtract(ProductDto product, int count) {
		items.merge(product, count, (a, b) -> max(a - b, 0));
	}

	@Override
	public Iterator<Entry<ProductDto, Integer>> iterator() {
		return items.entrySet().iterator();
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Order created by ").append(user.name()).append(" on ")
				.append(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(creationTime)).append(": ")
				.append(items.entrySet().stream().map(Order::orderItemToString).collect(Collectors.joining(",", "[", "]")))
				.toString();
	}
	
	private static String orderItemToString(Entry<ProductDto, Integer> entry) {
		return new StringBuilder().append(entry.getKey().name()).append(":").append(entry.getValue()).toString();
	}

}
