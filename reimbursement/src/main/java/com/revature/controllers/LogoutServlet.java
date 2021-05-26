package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name="logout", urlPatterns="/main/logout")
public class LogoutServlet extends HttpServlet{
	private static Logger log = Logger.getLogger(LogoutServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		PrintWriter writer = response.getWriter();
		
		Cookie[] cookies = request.getCookies();
		
		for (Cookie ck : cookies) {
			System.out.println("In logout-> cookie name: " + ck.getName() + " cookie value: " + ck.getValue());
		}
		
		response.setContentType("text/html");
		
		//empty cookies
		Cookie userId = new Cookie("userId", "");
		Cookie userType = new Cookie("userType", "");
		Cookie uName = new Cookie("username", "");
		
		//force expiration
		userId.setMaxAge(0);
		userType.setMaxAge(0);
		uName.setMaxAge(0);
		
		//send cookie with no associated user
		response.addCookie(userId);
		response.addCookie(userType);
		response.addCookie(uName);
		
		writer.print("User logged out");
		log.info("Logged out user");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		log.info("Post attempt in LogoutServlet");
		System.out.println("In doPost of LogoutServlet, you shouldn't be here!");
	}
}
