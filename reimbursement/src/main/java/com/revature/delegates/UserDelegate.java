package com.revature.delegates;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserDelegate {
	private static Logger log = Logger.getLogger(UserDelegate.class);
	
	private UserService us = new UserService();
	
	public void getEmployees(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		String path = request.getServletPath();
		List<User> users = null;
		
		if (path.substring(9) == "/all") {
			try {
				users = us.getAllEmployees();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setStatus(200);
			ObjectMapper mapper = new ObjectMapper();
			response.setHeader("allEmp", mapper.writeValueAsString(users));
		}
		log.info("Viewed all employees");
	}
}
