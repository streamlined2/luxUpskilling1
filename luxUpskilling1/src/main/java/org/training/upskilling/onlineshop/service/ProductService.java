package org.training.upskilling.onlineshop.service;

import java.util.List;
import java.util.Optional;

import org.training.upskilling.onlineshop.service.dto.ProductDto;

public interface ProductService {
	
	List<ProductDto> getAll();
	void add(ProductDto dto);
	void update(ProductDto dto);
	void delete(Long id);
	Optional<ProductDto> findById(Long id);

}
