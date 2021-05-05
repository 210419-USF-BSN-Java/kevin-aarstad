package com.revature.daos;

public class DAOFactory {
	
	private static DAOFactory df;

	private DAOFactory() {}
	
	public static DAOFactory getDAOFactory() {
		
		if (df == null) {
			df = new DAOFactory();
		}
		return df;
	}
}
