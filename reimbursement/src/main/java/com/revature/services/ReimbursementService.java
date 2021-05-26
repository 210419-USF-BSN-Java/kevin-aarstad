package com.revature.services;

import java.util.List;

import com.revature.daos.ReimbursementPostgres;
import com.revature.models.Reimbursement;

public class ReimbursementService {

	static ReimbursementPostgres rip = new ReimbursementPostgres();
	
	//returns all reimbursements
	public List<Reimbursement> getAllReimbursements(){
		List<Reimbursement> reimbs = rip.getAll();
		return reimbs;
	}
	
	//resolves reimbursement by status id. 2 = approved, 3 = denied
	public Reimbursement resolveReimbursement(int reimbId, int choice, int resolver) {
		rip.resolveReimbursement(reimbId, choice, resolver);
		Reimbursement r = new Reimbursement();
		r = rip.getById(reimbId);
		return r;
	}
	
	//returns all pending reimbursements
	public List<Reimbursement> getAllPending(){
		return rip.getAllPending();
	}
	
	//returns all resolved reimbursements
	public List<Reimbursement> getAllResolved(){
		return rip.getAllResolved();
	}

	//returns all reimbursements from a specific user id
	public List<Reimbursement> getByUser(int id){
		System.out.println("*****ReimbursementService- getByUser*****");
		return rip.getAllRequestsByEmployee(id);
	}
	
	//adds reimbursement and returns ID
	public Integer addReimbursement(Reimbursement r) {
		System.out.println("*****ReimbursementService - addReimbursement*****");
		Reimbursement reimbItem = rip.add(r);
		return reimbItem.getId();
	}
}
