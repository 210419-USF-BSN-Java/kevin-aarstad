package com.revature.daos;

import java.util.List;
import com.revature.models.Item;


public interface ItemDao extends GenericDao<Item>{
	
	public List<Item> getByName(String name);

}
