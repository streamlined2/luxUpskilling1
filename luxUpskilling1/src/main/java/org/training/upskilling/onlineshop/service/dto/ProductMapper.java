package org.training.upskilling.onlineshop.service.dto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.training.upskilling.onlineshop.dao.DataAccessException;
import org.training.upskilling.onlineshop.model.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductMapper {

	public ProductDto toDto(Product product) {
		return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getCreationDate());
	}

	public Product toProduct(ProductDto dto) {
		return Product.builder().id(dto.id()).name(dto.name()).price(dto.price()).creationDate(dto.creationDate())
				.build();
	}

	public Product toProduct(ResultSet resultSet) {
		try {
			return Product.builder().id(resultSet.getLong(1)).name(resultSet.getString(2))
					.price(resultSet.getBigDecimal(3)).creationDate(resultSet.getDate(4).toLocalDate()).build();
		} catch (SQLException e) {
			log.error("can't convert query result to product entity");
			throw new DataAccessException("can't convert query result to product entity", e);
		}
	}

	public void fillInInsertParameters(PreparedStatement statement, Product product) {
		try {
			statement.setString(1, product.getName());
			statement.setBigDecimal(2, product.getPrice());
			statement.setDate(3, Date.valueOf(product.getCreationDate()));
		} catch (SQLException e) {
			log.error("can't fill in INSERT statement's parameters for product {}", toDto(product));
			throw new DataAccessException(
					String.format("can't fill in INSERT statement's parameters for product %s", toDto(product)), e);
		}
	}

	public void fillInUpdateParameters(PreparedStatement statement, Product product) {
		try {
			statement.setString(1, product.getName());
			statement.setBigDecimal(2, product.getPrice());
			statement.setLong(3, product.getId());
		} catch (SQLException e) {
			log.error("can't fill in UPDATE statement's parameters for product {}", toDto(product));
			throw new DataAccessException(
					String.format("can't fill in UPDATE statement's parameters for product %s", toDto(product)), e);
		}
	}

}
