package com.revature.delegates;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.services.UserService;

public class LoginDelegate {
	private static Logger log = Logger.getLogger(LoginDelegate.class);
	
	private UserService us = new UserService();
	
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
		System.out.println("~~~~~~~~~in Authenticate~~~~~~~~~");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("~~~~~~~~~logging in " + username + "~~~~~~~~~");	
		
		User user = us.loginUser(username, password);

		if(user == null) {
			log.info("Incorrect login");
			response.sendError(401);
		}
		else {
			String token = user.getId() + ":" + user.getRole();
			response.setStatus(200);
			response.setHeader("Authorization", token);
			System.out.println("~~~~~~~~~Successful login by user ID: " + user.getId() + "~~~~~~~~~");
			log.info("Successful login by user ID: " + user.getId());
		}
	}

	public boolean isAuthorized(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if(auth != null) {
			String[] tokenArray = auth.split(":");
			int id = Integer.parseInt(tokenArray[0]);
			int role = Integer.parseInt(tokenArray[1]);
			User user = us.getById(id);
			if(user != null && user.getRole() == role) {
				log.info("Authorized user ID: " + id);
				return true;
			}
			
		}
		log.info("Unauthorized user");
		return false;
	}
}
