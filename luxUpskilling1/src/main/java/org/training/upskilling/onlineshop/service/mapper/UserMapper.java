package org.training.upskilling.onlineshop.service.mapper;

import org.training.upskilling.onlineshop.model.User;
import org.training.upskilling.onlineshop.service.dto.UserDto;

public class UserMapper {

	public UserDto toDto(User user) {
		return new UserDto(user.getId(), user.getName(), user.getEncodedPassword());
	}

}
