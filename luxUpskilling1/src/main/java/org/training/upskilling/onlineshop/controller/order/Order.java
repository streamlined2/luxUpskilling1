package org.training.upskilling.onlineshop.controller.order;

import java.time.LocalDateTime;
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
		items =  new HashMap<>();
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
		items.merge(product, count, (a, b) -> a - b);
	}

	@Override
	public Iterator<Entry<ProductDto, Integer>> iterator() {
		return items.entrySet().iterator();
	}
	
	@Override
	public String toString() {
		return items.entrySet().stream().map(Entry::toString).collect(Collectors.joining(",", "[", "]"));
	}

}
