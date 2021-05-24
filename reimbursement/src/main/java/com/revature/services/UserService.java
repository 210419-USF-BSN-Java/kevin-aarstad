package com.revature.services;

import java.util.List;

import com.revature.daos.UserPostgres;
import com.revature.models.User;

public class UserService {
	
	static UserPostgres up = new UserPostgres();
	
	public List<User> getAllEmployees() throws ClassNotFoundException{
		List<User> employees = up.getAllEmployees();
		return employees;		
	}
	
	public User loginUser(String username, String password) throws ClassNotFoundException {
		User user = up.getByUsernameAndPassword(username, password);
		return user;
	}
	
	public int registerUser(User u) {
		User user = up.add(u);
		return user.getId();
	}
	
	public User getById(int id) {
		return up.getById(id);
	}
}
