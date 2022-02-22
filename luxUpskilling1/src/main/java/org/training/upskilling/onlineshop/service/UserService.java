package org.training.upskilling.onlineshop.service;

import java.util.Optional;

import org.training.upskilling.onlineshop.service.dto.UserDto;

public interface UserService {
	
	Optional<UserDto> findUserByName(String name);

}
