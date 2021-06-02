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

@WebServlet(name="emp-reimbursement", urlPatterns="/main/emp-reimbursements")
public class EmpReimbServlet extends HttpServlet{
	private static Logger log = Logger.getLogger(EmpReimbServlet.class);
	
	private ReimbursementService rs = new ReimbursementService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//returns all reimbursements by a single employee
		
		//holds the incoming id
		int id = 0;
		
		//see if request is coming from employee or manager
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			List<Cookie> cookieList = Arrays.asList(cookies);
			
			//get userRole cookie
			Cookie userRole = cookieList.stream().filter(cookie -> cookie.getName().equals("userRole")).findAny().orElse(null);
			
			//if employee, get id
			if(userRole.getValue().equals("1")) {
                Cookie userId = cookieList.stream().filter(cookie -> cookie.getName().equals("userId")).findAny().orElse(null);
                System.out.println("Listing all reimbursements by user ID= " + userId);
                id = Integer.parseInt(userId.getValue());              
			}
			else {
				//if manager the userId will come back as param
				id = Integer.parseInt(request.getParameter("empId"));
			}
		}
		System.out.println("*****sending to ReimbursementService*****");
		List<Reimbursement> reimbursements = rs.getByUser(id);		
//		for(Reimbursement r : reimbursements) {
//			System.out.println(r.getStatus());
//		}
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream json = response.getOutputStream();
		JsonConverter converter = new JsonConverter();
		String output = converter.convertToJson(reimbursements);
		
		json.print(output);
		log.info("listed all reimbursements by a single user");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("*****EmpReimbServlet - doPost*****");
		//get parameters
		String amount = request.getParameter("amount");
		String description = request.getParameter("description");
		String userId = request.getParameter("userId");
		String type = request.getParameter("type");
		
		System.out.println(("moving to add reimbursement"));
		//add reimbursement
		Reimbursement r = new Reimbursement();
		r.setAmount(Double.parseDouble(amount));
		r.setDescription(description);
		r.setAuthor(Integer.parseInt(userId));
		r.setType(Integer.parseInt(type));
		rs.addReimbursement(r);
		System.out.println("added reimbursement= " + r);
		
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream json = response.getOutputStream();
		JsonConverter converter = new JsonConverter();
		String output = converter.convertToJson(r);
		json.print(output);
		
		log.info("added reimbursement request");
	}
}
