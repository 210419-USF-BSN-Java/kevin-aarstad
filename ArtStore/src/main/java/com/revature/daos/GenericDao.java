package com.revature.daos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
	
	// CRUD operations for all DAOs
	public T add(T t) throws SQLException, IOException;
	public T getById(Integer id);
	public List<T> getAll();
	public Integer update(T t);
	public Integer delete(T t);
}