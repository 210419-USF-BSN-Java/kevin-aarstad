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

@WebServlet(name="employees", urlPatterns= {"/main/employees"})
public class EmployeeListServlet extends HttpServlet{
	private static Logger log = Logger.getLogger(EmployeeListServlet.class);
	private UserService us = new UserService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//getting cookies
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null) {
			//breaking up into list for parsing
			List<Cookie> cookieList = Arrays.asList(cookies);
			
			//finds userRole
			Cookie userRole = cookieList.stream().filter(cookie -> cookie.getName().equals("userRole")).findAny().orElse(null);
			
			//if user is a manager, list employees
			if(userRole.getValue().equals("2")) {
				
				List<User> employees = us.getAllEmployees();
				response.setContentType("application/json;charset=UTF-8");
				ServletOutputStream json = response.getOutputStream();
				JsonConverter converter = new JsonConverter();
				
				String output = converter.convertUsersToJson(employees);
				json.print(output);
				log.info("List of employees accessed by manager");
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("In doPost of EmployeeServlet, you shouldn't be here!");
		log.info("Post request sent to EmployeeServlet");
	}
}
