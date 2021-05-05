package com.revature.daos;

import java.util.List;
import com.revature.models.Offer;

public interface OfferDao extends GenericDao<Offer>{
	
	public List<Offer> getByValue(Integer value);

}
