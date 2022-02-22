package org.training.upskilling.onlineshop.service.dto;

import java.util.Optional;

import org.training.upskilling.onlineshop.dao.UserDao;
import org.training.upskilling.onlineshop.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultUserService implements UserService {
	
	private final UserDao userDao;
	private final UserMapper userMapper;

	@Override
	public Optional<UserDto> findUserByName(String name) {
		return userDao.findByName(name).map(userMapper::toDto);
	}

}
