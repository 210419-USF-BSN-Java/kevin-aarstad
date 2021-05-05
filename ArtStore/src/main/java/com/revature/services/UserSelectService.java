package com.revature.services;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class UserSelectService {

	public static Scanner in;
	
	public static void mainMenu() throws IOException, SQLException {
		//prints menu from file
		File file = new File("C:\\Users\\Kevin\\Documents\\revature\\project0\\ArtStore\\src\\main\\resources\\MainMenu.txt");
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\\Z");
		System.out.println(sc.next());
		sc.close();
		

		in = new Scanner(System.in);
		int choice = 0;
		do  {
 			choice = in.nextInt();
			switch (choice) {
				case 0:
				System.exit(0);
		 		break;
			case 1:
		 		CustomerService.customerMenu();
		 		break;
		 	case 2:
		 		EmployeeService.employeeMenu();
		 		break;
		 	default:
		 		System.out.println("Please enter a valid selection.");
			}
			
		} while (choice < 0 || choice > 2);

	}
}
