package org.training.upskilling.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.training.upskilling.onlineshop.dao.DataAccessException;
import org.training.upskilling.onlineshop.dao.UserDao;
import org.training.upskilling.onlineshop.model.User;
import org.training.upskilling.onlineshop.service.dto.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class UserJdbcDao implements UserDao {

	private static final String SCHEMA = "yaos";
	private static final String TABLE_NAME = "user";
	private static final String NOT_YET_IMPLEMENTED_MESSAGE = "not yet implemented";

	private static final String FETCH_ENTITY_BY_NAME_STATEMENT = String
			.format("SELECT id, name, password FROM %s.%s WHERE name=?", SCHEMA, TABLE_NAME);

	private final JdbcConnectionFactory connectionFactory;
	private final UserMapper mapper;

	@Override
	public Optional<User> findByName(String name) {
		try (Connection conn = connectionFactory.getConnection();
				PreparedStatement statement = conn.prepareStatement(FETCH_ENTITY_BY_NAME_STATEMENT)) {
			statement.setString(1, name);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(mapper.toUser(resultSet));
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			log.error("can't fetch product for name {}", name);
			throw new DataAccessException(String.format("can't fetch product for name %s", name), e);
		}
	}

	@Override
	public List<User> getAll() {
		throw new UnsupportedOperationException(NOT_YET_IMPLEMENTED_MESSAGE);
	}

	@Override
	public void add(User entity) {
		throw new UnsupportedOperationException(NOT_YET_IMPLEMENTED_MESSAGE);
	}

	@Override
	public void update(User entity) {
		throw new UnsupportedOperationException(NOT_YET_IMPLEMENTED_MESSAGE);
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException(NOT_YET_IMPLEMENTED_MESSAGE);
	}

	@Override
	public Optional<User> findById(Long id) {
		throw new UnsupportedOperationException(NOT_YET_IMPLEMENTED_MESSAGE);
	}

}
