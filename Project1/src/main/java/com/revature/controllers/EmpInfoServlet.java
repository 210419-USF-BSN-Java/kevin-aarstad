package com.revature.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

@WebServlet(name="employee-info", urlPatterns="/main/employee-info")
public class EmpInfoServlet extends HttpServlet{
	private static Logger log = Logger.getLogger(EmpInfoServlet.class);
	
	UserService us = new UserService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//getting cookies;
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			//breaking up cookies for parsing
			List<Cookie> cookieList = Arrays.asList(cookies);
			
			Cookie userId = cookieList.stream().filter(cookie -> cookie.getName().equals("userId")).findAny().orElse(null);
			System.out.println("*****doGet UserId = " + userId.getValue());
			User user = us.getById(Integer.parseInt(userId.getValue()));
			
			response.setContentType("application/json;charset=UTF-8");
			ServletOutputStream json = response.getOutputStream();
			JsonConverter converter = new JsonConverter();
			String output = converter.convertToJson(user);
			json.print(output);
			System.out.println("EmpInfoServlet - Retrieved user info by Id");
			System.out.println(output);
			log.info("EmpInfoServlet - Retrieved user info by Id");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("EmpInfoServlet - doPost - update info");
		
		Cookie[] cookies = request.getCookies();
		List<Cookie> cookieList = Arrays.asList(cookies);
		Cookie userId = cookieList.stream().filter(cookie -> cookie.getName().equals("userId")).findAny().orElse(null);
		
		User user = us.getById(Integer.parseInt(userId.getValue()));
		
		System.out.println("*****doPost UserId = " + user.getId());
		
		String password = request.getParameter("password");
		System.out.println("password in =" + password);
		String firstName = request.getParameter("firstName");
		System.out.println("first name in =" + firstName);
		String lastName = request.getParameter("lastName");
		System.out.println("last name in =" + lastName);
		String email = request.getParameter("email");
		System.out.println("email in=" + email);
		
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		
		user = us.update(user);
		
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream json = response.getOutputStream();
		JsonConverter converter = new JsonConverter();
		String output = converter.convertToJson(user);
		json.print(output);
		
		System.out.println("Updated user info");
		log.info("Updated user info");
		
	}
}
