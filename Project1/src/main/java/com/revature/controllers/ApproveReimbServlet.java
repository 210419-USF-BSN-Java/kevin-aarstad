package com.revature.controllers;

import java.io.IOException;
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

@WebServlet(name="approve-reimbursement", urlPatterns="/main/approve-reimbursement")
public class ApproveReimbServlet extends HttpServlet{
	private static Logger log = Logger.getLogger(ApproveReimbServlet.class);
	
	private ReimbursementService rs = new ReimbursementService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("in doGet of ApproveReimbServlet, you shouldn't be here!");
		log.info("get request attempted in ApproveReimbServlet");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int reimbId = Integer.parseInt(request.getParameter("id"));
		int resolverId = Integer.parseInt(request.getParameter("resolverId"));
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
		
			Reimbursement r = new Reimbursement();
			int resolution = 2;
			r = rs.resolveReimbursement(reimbId, resolution, resolverId);
			
			response.setContentType("application/json;charset=UTF-8");
			ServletOutputStream json = response.getOutputStream();
			JsonConverter converter = new JsonConverter();
			String output = converter.convertToJson(r);
			json.print(output);
			log.info("Reimbursement approved");
		}
	}
}
