package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.delegates.LoginDelegate;
import com.revature.delegates.ReimbursementDelegate;
import com.revature.delegates.UserDelegate;

public class RequestHelper {
	private LoginDelegate ld = new LoginDelegate();
	private UserDelegate ud = new UserDelegate();
	private ReimbursementDelegate rd = new ReimbursementDelegate();

	public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println("1 : " + path);
		
		if(path.startsWith("/api/")) {
			if(!ld.isAuthorized(request)) {
				response.sendError(401);
				return;
			}
			String record = path.substring(5);
			if (record.startsWith("employees")) {
				ud.getEmployees(request, response);
			}
			if (record.startsWith("reimbursements")) {
				rd.getReimbursements(request, response);
			}
		}
	}
	
	public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
		String path = request.getServletPath();
		System.out.println("~~~~~~~~~ 3 : " + path + " " + request + response);
		switch(path) {
		case "/submit" :
			rd.addReimbursement(request, response);
			break;
		case "/static/login.html" :
			ld.authenticate(request, response);
			break;
		default:
			response.sendError(405);
		}
	}
	
}
