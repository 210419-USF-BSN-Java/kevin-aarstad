package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.JsonConverter;

@WebServlet(name="login", urlPatterns= {"/login"})
public class LoginServlet extends HttpServlet{
	private static Logger log = Logger.getLogger(LoginServlet.class);
	private UserService us = new UserService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("In doGet of LoginServlet, you shouldn't be here!");
		log.info("Get request sent to LoginServlet");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		//username/password via request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		
		User user = us.loginUser(username, password);

		
		if (user != null) {
			//create cookie for user
			System.out.println("****LoginServlet- create cookies*****");
			Cookie userId = new Cookie("userId", String.valueOf(user.getId()));
			Cookie userRole = new Cookie("userRole", String.valueOf(user.getRole()));
			Cookie uName = new Cookie("username", String.valueOf(user.getUsername()));
			System.out.println("userId= " + userId + " userRole= " + userRole + " uName= " + uName);
			
			response.addCookie(userId);
			response.addCookie(userRole);
			response.addCookie(uName);
			
			response.setContentType("application/json;charset=UTF-8");
			ServletOutputStream json = response.getOutputStream();
			JsonConverter converter = new JsonConverter();
			String output = converter.convertToJson(user);
			json.print(output);
			log.info("Successful login by user ID= " + userId);
		}
		else {
			System.out.println("Login error");
			log.info("login error");
		}
	}
}
