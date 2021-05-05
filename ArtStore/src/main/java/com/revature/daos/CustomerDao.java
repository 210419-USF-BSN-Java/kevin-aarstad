package com.revature.daos;

import com.revature.models.Customer;

public interface CustomerDao extends GenericDao<Customer>{

	public Customer getByName(String name);
	
}
