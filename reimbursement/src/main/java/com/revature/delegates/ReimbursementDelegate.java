package com.revature.delegates;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.ReimbursementItem;
import com.revature.models.User;
import com.revature.services.ReimbursementService;

public class ReimbursementDelegate {
	private static Logger log = Logger.getLogger(ReimbursementDelegate.class);
	
	private ReimbursementService rs = new ReimbursementService();
	
	public void addReimbursement(HttpServletRequest request, HttpServletResponse response) {
		ReimbursementItem ri = new ReimbursementItem();
		
		ri.setAmount(Double.parseDouble(request.getParameter("amount")));
		ri.setDescription(request.getParameter("description"));
		
		String token = request.getHeader("Authorization");
		if(token != null) {
			String[] info = token.split(":");
			ri.setAuthor(Integer.parseInt(info[0]));
			
			ri.setType(Integer.parseInt(request.getParameter("type")));
			
			ri.setSubmitted(new Date());
			
			rs.addReimbursement(ri);
			log.info("added reimbursement");			
		}
	}
	
	public void getReimbursements(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		String path = request.getServletPath();
		
		if(path.startsWith("/api/reimbursements/author/")) {
			String author = path.replace("/api/reimbursements/author/", "");
			System.out.println("2 : " + author);
			
			User user = new User();
			user.setId(Integer.parseInt(author));
			List<ReimbursementItem> items = rs.getByUser(user.getId());
			response.setStatus(200);
			ObjectMapper mapper = new ObjectMapper();
			response.setHeader("authorList", mapper.writeValueAsString(items));
		}
		System.out.println("4 : " + path.substring(11));
		
		List<ReimbursementItem> items = null;
		ObjectMapper mapper = new ObjectMapper();
		switch(path.substring(19)) {
		case "/all":
			items = rs.getAllReimbursements();
			response.setStatus(200);
			response.setHeader("allReimb", mapper.writeValueAsString(items));
			log.info("Viewed all reimbursements");
			break;
		case "/pending":
			items = rs.getAllPending();
			response.setStatus(200);
			response.setHeader("allPendingReimb", mapper.writeValueAsString(items));
			log.info("Viewed all pending reimbursements");
			break;
		case "/resolved":
			items = rs.getAllResolved();
			response.setStatus(200);
			response.setHeader("allResolvedReimb", mapper.writeValueAsString(items));
			log.info("Viewed all resolved reimbursements");
			break;
		}
		
		
	}
}
