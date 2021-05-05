package com.revature.daos;

import java.util.List;
import com.revature.models.Payment;

public interface PaymentDao extends GenericDao<Payment>{
	
	public List<Payment> getByValue(Integer value);

}
