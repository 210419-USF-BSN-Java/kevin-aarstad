package com.revature.services;

import java.util.List;

import com.revature.daos.UserPostgres;
import com.revature.models.User;

public class UserService {
	
	static UserPostgres up = new UserPostgres();
	
	public List<User> getAllEmployees(){
		return up.getAllEmployees();		
	}
	
	public User loginUser(String username, String password){
		System.out.println("*****UserService loginUser*****");
		return up.getByUsernameAndPassword(username, password);
	}
	
	public int registerUser(User u) {
		User user = up.add(u);
		return user.getId();
	}
	
	public User getById(int id) {
		return up.getById(id);
	}
	
	public User update(User u) {
		return up.update(u);
	}
}
