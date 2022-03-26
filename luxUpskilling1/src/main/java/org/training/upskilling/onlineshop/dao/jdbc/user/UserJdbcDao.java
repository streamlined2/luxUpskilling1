package org.training.upskilling.onlineshop.dao.jdbc.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.training.upskilling.onlineshop.dao.DataAccessException;
import org.training.upskilling.onlineshop.dao.UserDao;
import org.training.upskilling.onlineshop.dao.jdbc.JdbcConnectionFactory;
import org.training.upskilling.onlineshop.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserJdbcDao implements UserDao {

	private static final String SCHEMA = "yaos";
	private static final String TABLE_NAME = "user";

	private static final String FETCH_ENTITY_BY_NAME_STATEMENT = String
			.format("SELECT id, name, password, salt, role FROM %s.%s WHERE name=?", SCHEMA, TABLE_NAME);

	private final JdbcConnectionFactory connectionFactory;
	private final UserJdbcHelper userJdbcHelper;

	@Override
	public Optional<User> findByName(String name) {
		try (Connection conn = connectionFactory.getConnection();
				PreparedStatement statement = conn.prepareStatement(FETCH_ENTITY_BY_NAME_STATEMENT)) {
			statement.setString(1, name);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(userJdbcHelper.toUser(resultSet));
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			log.error("can't fetch product for name {}", name);
			throw new DataAccessException(String.format("can't fetch product for name %s", name), e);
		}
	}

}
