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

import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.util.JsonConverter;

@WebServlet(name="reimbursements", urlPatterns="/main/reimbursements")
public class ReimbListServlet extends HttpServlet{
	private static Logger log = Logger.getLogger(ReimbListServlet.class);
	
	private ReimbursementService rs = new ReimbursementService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//returns reimbursements, all if from manager, if employee returns only theirs
		System.out.println(("*****ReimbListServlet- doGet start*****"));
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			System.out.println("*****cookies were not null*****");
			//breaks cookie up for parsing
			List<Cookie> cookieList = Arrays.asList(cookies);
			//get userRole
			Cookie userRole = cookieList.stream().filter(cookie -> cookie.getName().equals("userRole")).findAny().orElse(null);
			//gets userId
			Cookie userId = cookieList.stream().filter(cookie -> cookie.getName().equals("userId")).findAny().orElse(null);
			
			//if user is manager, gets all
			if(userRole.getValue().equals("2")) {
				System.out.println("*****Manager, listing all*****");
				List<Reimbursement> reimbursements = rs.getAllReimbursements();
				
				response.setContentType("application/json;charset=UTF-8");
				ServletOutputStream json = response.getOutputStream();
				JsonConverter converter = new JsonConverter();
				String output = converter.convertToJson(reimbursements);
				json.print(output);
			}
			//if user is employee, gets based on id
			else if(userRole.getValue().equals("1")){
				System.out.println("*****Employee, listing all*****");
				System.out.println("userId= " + userId.getValue());
				List<Reimbursement> userReimbs = rs.getByUser(Integer.parseInt(userId.getValue()));
				
				response.setContentType("application/json;charset=UTF-8");
				ServletOutputStream json = response.getOutputStream();
				JsonConverter converter = new JsonConverter();
				String output = converter.convertToJson(userReimbs);
				json.print(output);
			}
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		log.info("Post attempt in ReimbListServlet");
		System.out.println("In doPost of ReimbListServlet, you shouldn't be here!");
	}
}
