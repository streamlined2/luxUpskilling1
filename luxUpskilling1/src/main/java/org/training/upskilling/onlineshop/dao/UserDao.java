package org.training.upskilling.onlineshop.dao;

import java.util.Optional;

import org.training.upskilling.onlineshop.model.User;

public interface UserDao extends Dao<User, Long> {
	
	Optional<User> findByName(String name);

}
