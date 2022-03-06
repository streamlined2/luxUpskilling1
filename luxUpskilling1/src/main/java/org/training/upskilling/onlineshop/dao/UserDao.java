package org.training.upskilling.onlineshop.dao;

import java.util.Optional;

import org.training.upskilling.onlineshop.model.User;

public interface UserDao {
	
	Optional<User> findByName(String name);

}
