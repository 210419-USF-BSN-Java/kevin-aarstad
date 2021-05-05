package com.revature.controllers;

import java.io.IOException;
import java.sql.SQLException;
import com.revature.services.UserSelectService;

public class MainDriver {

	  public static void main(String[] args) throws IOException, SQLException {

		UserSelectService.mainMenu();
		
	  }
}
