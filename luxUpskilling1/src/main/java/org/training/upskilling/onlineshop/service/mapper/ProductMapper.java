package org.training.upskilling.onlineshop.service.mapper;

import org.training.upskilling.onlineshop.model.Product;
import org.training.upskilling.onlineshop.service.dto.ProductDto;

public class ProductMapper {

	public ProductDto toDto(Product product) {
		return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getCreationDate());
	}

	public Product toProduct(ProductDto dto) {
		return Product.builder().id(dto.id()).name(dto.name()).price(dto.price()).creationDate(dto.creationDate())
				.build();
	}

}
