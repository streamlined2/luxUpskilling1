package org.training.upskilling.onlineshop.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<E, I> {
	
	List<E> getAll();
	void add(E entity);
	void update(E entity);
	void delete(I id);
	Optional<E> findById(I id);
	
}
