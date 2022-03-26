package org.training.upskilling.onlineshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.training.upskilling.onlineshop.ServiceLocator;
import org.training.upskilling.onlineshop.dao.Dao;
import org.training.upskilling.onlineshop.dao.jdbc.product.ProductJdbcDao;
import org.training.upskilling.onlineshop.model.Product;
import org.training.upskilling.onlineshop.service.dto.ProductDto;
import org.training.upskilling.onlineshop.service.mapper.ProductMapper;

@Service
public class DefaultProductService implements ProductService {

	private final Dao<Product, Long> productDao = ServiceLocator.getInstance(ProductJdbcDao.class);
	private final ProductMapper productMapper = ServiceLocator.getInstance(ProductMapper.class);

	@Override
	public void add(ProductDto dto) {
		productDao.add(productMapper.toProduct(dto));
	}

	@Override
	public void update(ProductDto dto) {
		productDao.update(productMapper.toProduct(dto));
	}

	@Override
	public void delete(Long id) {
		productDao.delete(id);
	}

	@Override
	public Optional<ProductDto> findById(Long id) {
		return productDao.findById(id).map(productMapper::toDto);
	}

	@Override
	public List<ProductDto> getAll() {
		return productDao.getAll().stream().map(productMapper::toDto).toList();
	}

}
