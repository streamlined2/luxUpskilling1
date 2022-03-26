package org.training.upskilling.onlineshop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.training.upskilling.onlineshop.ServiceLocator;
import org.training.upskilling.onlineshop.dao.UserDao;
import org.training.upskilling.onlineshop.dao.jdbc.user.UserJdbcDao;
import org.training.upskilling.onlineshop.service.dto.UserDto;
import org.training.upskilling.onlineshop.service.mapper.UserMapper;

@Service
public class DefaultUserService implements UserService {
	
	private final UserDao userDao = ServiceLocator.getInstance(UserJdbcDao.class);
	private final UserMapper userMapper = ServiceLocator.getInstance(UserMapper.class);

	@Override
	public Optional<UserDto> findUserByName(String name) {
		return userDao.findByName(name).map(userMapper::toDto);
	}

}
