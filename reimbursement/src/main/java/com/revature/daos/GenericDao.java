package com.revature.daos;

import java.util.List;

public interface GenericDao<T> {
	
	// CRUD operations for all Daos
	public T add(T t);
	public T getById(Integer id);
	public List<T> getAll();
	public T update(T t);
	public Boolean delete(T t);
}
