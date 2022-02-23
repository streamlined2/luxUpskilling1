package org.training.upskilling.onlineshop.service.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.training.upskilling.onlineshop.dao.DataAccessException;
import org.training.upskilling.onlineshop.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserMapper {

	public UserDto toDto(User user) {
		return new UserDto(user.getId(), user.getName(), user.getEncodedPassword());
	}

	public User toUser(ResultSet resultSet) {
		try {
			return User.builder().id(resultSet.getLong(1)).name(resultSet.getString(2))
					.encodedPassword(resultSet.getString(3)).build();
		} catch (SQLException e) {
			log.error("can't convert query result to user entity");
			throw new DataAccessException("can't convert query result to user entity", e);
		}
	}

}