package org.training.upskilling.onlineshop.dao.jdbc.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.training.upskilling.onlineshop.dao.DataAccessException;
import org.training.upskilling.onlineshop.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserJdbcHelper {

	public User toUser(ResultSet resultSet) {
		try {
			return User.builder().id(resultSet.getLong("id")).name(resultSet.getString("name"))
					.encodedPassword(resultSet.getString("password")).salt(resultSet.getString("salt")).build();
		} catch (SQLException e) {
			log.error("can't convert query result to user entity");
			throw new DataAccessException("can't convert query result to user entity", e);
		}
	}

}
