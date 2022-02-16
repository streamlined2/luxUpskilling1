package org.training.upskilling.onlineshop.service;

import java.util.List;
import java.util.Optional;

import org.training.upskilling.onlineshop.dao.Dao;
import org.training.upskilling.onlineshop.model.Product;
import org.training.upskilling.onlineshop.service.dto.ProductDto;
import org.training.upskilling.onlineshop.service.dto.ProductMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

	private final Dao<Product, Long> dao;
	private final ProductMapper mapper;

	@Override
	public void add(ProductDto dto) {
		dao.add(mapper.toProduct(dto));
	}

	@Override
	public void update(ProductDto dto) {
		dao.update(mapper.toProduct(dto));
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public Optional<ProductDto> findById(Long id) {
		return dao.findById(id).map(mapper::toDto);
	}

	@Override
	public List<ProductDto> getAll() {
		return dao.getAll().stream().map(mapper::toDto).toList();
	}

}
