package org.training.upskilling.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.training.upskilling.onlineshop.dao.Dao;
import org.training.upskilling.onlineshop.dao.DataAccessException;
import org.training.upskilling.onlineshop.model.Product;
import org.training.upskilling.onlineshop.service.dto.ProductMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductJdbcDao implements Dao<Product, Long> {

	public static final String URL = "url";
	public static final String USER = "user";
	public static final String PASSWORD = "password";

	public static final String SCHEME = "online-shop";
	public static final String TABLE_NAME = "product";

	private static final String FETCH_ALL_STATEMENT = String
			.format("SELECT id, name, price, creation_date FROM '%s'.%s", SCHEME, TABLE_NAME);
	private static final String FETCH_ENTITY_STATEMENT = String
			.format("SELECT id, name, price, creation_date FROM '%s'.%s WHERE id=?", SCHEME, TABLE_NAME);
	private static final String INSERT_ENTITY_STATEMENT = String
			.format("INSERT INTO '%s'.%s (id, name, price, creation_date) VALUES (?, ?, ?, ?)", SCHEME, TABLE_NAME);
	private static final String DELETE_ENTITY_STATEMENT = String.format("DELETE FROM '%s'.%s WHERE id=?", SCHEME,
			TABLE_NAME);
	private static final String UPDATE_ENTITY_STATEMENT = String.format("UPDATE '%s'.%s SET name=?,price=? WHERE id=?",
			SCHEME, TABLE_NAME);

	private final Properties props;
	private final ProductMapper mapper;

	@Override
	public List<Product> getAll() {
		try (Connection conn = getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(FETCH_ALL_STATEMENT)) {
			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				products.add(mapper.toProduct(resultSet));
			}
			return products;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("can't fetch all products", e);
		}
	}

	@Override
	public Optional<Product> findById(Long id) {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(FETCH_ENTITY_STATEMENT)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(mapper.toProduct(resultSet));
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(String.format("can't fetch any product for id %d", id), e);
		}
	}

	@Override
	public void add(Product entity) {
		try (Connection conn = getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement statement = conn.prepareStatement(INSERT_ENTITY_STATEMENT,
					Statement.RETURN_GENERATED_KEYS)) {
				mapper.fillInInsertParameters(statement, entity);
				int count = statement.executeUpdate(INSERT_ENTITY_STATEMENT);
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (count == 1 && resultSet.next()) {
						entity.setId(resultSet.getLong(1));
						conn.commit();
					}
				}
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("can't add new entity", e);
		}
	}

	@Override
	public void update(Product entity) {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(UPDATE_ENTITY_STATEMENT)) {
			mapper.fillInUpdateParameters(statement, entity);
			int count = statement.executeUpdate();
			if (count == 1) {
				return;
			}
			throw new DataAccessException("more than one row affected by update operation");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("can't update entity", e);
		}
	}

	@Override
	public void delete(Long id) {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(DELETE_ENTITY_STATEMENT)) {
			int count = statement.executeUpdate();
			if (count == 1) {
				return;
			}
			throw new DataAccessException("more than one row affected by deletion operation");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("can't delete entity", e);
		}
	}

	private Connection getConnection() {
		try {
			String url = props.getProperty(URL);
			return DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("can't connect to database", e);
		}
	}

}
