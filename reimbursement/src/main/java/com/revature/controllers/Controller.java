package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.servlets.DefaultServlet;

public class Controller extends DefaultServlet{
	
	private static final long serialVersionUID = 1L;
	private RequestHelper requestHelper = new RequestHelper();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in Controller doGet");
		String path = request.getRequestURI().substring(request.getContextPath().length());
		if (path.startsWith("/static/") || path.equals("/") || path.equals("/index.html")) {
			System.out.println("Controller super constructor");
			super.doGet(request, response);
		} else {
			System.out.println("sending to request helper");
			requestHelper.processGet(request, response);
		}
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("~~~~~~~~~Controller doPost: " + request + response);
		try {
			requestHelper.processPost(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
