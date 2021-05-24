package com.revature.services;

import java.util.List;

import com.revature.daos.ReimbursementItemPostgres;
import com.revature.models.ReimbursementItem;

public class ReimbursementService {

	static ReimbursementItemPostgres rip = new ReimbursementItemPostgres();
	
	//returns all reimbursements
	public List<ReimbursementItem> getAllReimbursements(){
		List<ReimbursementItem> reimbs = rip.getAll();
		return reimbs;
	}
	
	//resolves reimbursement by status id. 2 = approved, 3 = denied
	public boolean resolveReimbursement(ReimbursementItem r, int choice, int resolver) {
		return rip.resolveReimbursement(r.getId(), choice, resolver);
	}
	
	//returns all pending reimbursements
	public List<ReimbursementItem> getAllPending(){
		return rip.getAllPending();
	}
	
	//returns all resolved reimbursements
	public List<ReimbursementItem> getAllResolved(){
		return rip.getAllResolved();
	}

	//returns all reimbursements from a specific user id
	public List<ReimbursementItem> getByUser(int id){
		return rip.getAllRequestsByEmployee(id);
	}
	
	//adds reimbursement and returns ID
	public Integer addReimbursement(ReimbursementItem r) {
		ReimbursementItem reimbItem = rip.add(r);
		return reimbItem.getId();
	}
}
