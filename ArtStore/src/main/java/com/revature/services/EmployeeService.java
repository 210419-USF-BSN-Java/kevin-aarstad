package com.revature.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.daos.EmployeePostgresql;
import com.revature.daos.ItemPostgresql;
import com.revature.daos.OfferPostgresql;
import com.revature.daos.PaymentPostgresql;
import com.revature.models.Employee;
import com.revature.models.Item;

public abstract class EmployeeService {

	public static Scanner in;
	private static int employeeSkip = 0;
	private static Employee employee = new Employee();
	private static Item item = new Item();
	private static EmployeePostgresql empsql = new EmployeePostgresql();
	private static ItemPostgresql itemsql = new ItemPostgresql();
	private static OfferPostgresql offersql = new OfferPostgresql();
	private static PaymentPostgresql paysql = new PaymentPostgresql();
	
	public static Employee employeeMenu() throws IOException, SQLException {
		//compare name entered to list. If found select that employee, if DNE create new
		if (employeeSkip == 0) {
			//in if statement to bypass if employee username is already selected
			System.out.println("\nPlease enter your employee username:");
			in = new Scanner(System.in);
			String username = in.nextLine();
		
			employee = empsql.getByName(username);
			//compares employee name to list, if found select that employee, if DNE create new		
			if (employee.getName() == null) {
				employee.setName(username);
				empsql.add(employee);
				employee = empsql.getByName(username);
			}
		employeeSkip++;
		}
		System.out.println("\nWelcome " + employee.getName() + ", please select an option\n"
							+ "0: Exit\n"
							+ "1: View Items\n"
							+ "2: View total monthly payments");
		in = new Scanner(System.in);
		int choice = 0;

		do {
			choice = in.nextInt();
			switch (choice) {
			case 0:
				System.exit(0);
				break;
			case 1:
				employeeItemView();
				break;
			case 2:
				employeeViewPayments();
				break;
			default:
				System.out.println("\nPlease enter a valid selection.");
				break;
			} 
		} while (choice < 0 || choice > 2);
		return employee;
	}
	
	public static Item employeeItemView() throws IOException, SQLException {
		int choice = 0;	
		do {
		System.out.println("\nItems currently for sale.\n"
						+ "0: Return to Employee Menu\n"
						+ "-1: Add new item\n"
						+ "Select item number to view item\n"
						+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		itemsql.getAvailableItems();
		in = new Scanner(System.in);
		choice = in.nextInt();
		if (choice == -1) {	
			in.nextLine();
			System.out.println("\nAdding a new item, please enter the name of the Artwork: ");
			String newItem = in.nextLine();
			item = new Item(newItem);
			itemsql.add(item);
		} else if (choice == 0) {
		//empty to bypass adding or selecting an item if choice == 0
		} else {
			//look up item by number, select item, if item DNE, state and show item view
			item = itemsql.getById(choice);
			if (item.getId() == null) {
				System.out.println("\nItem ID does not exist, returning to Items currently for sale\n");				
			} else {
				//item exists, pass to employeeItemSelect()
				employeeItemSelect(choice);
			}
		}
		}while (choice != 0);
		employeeMenu();
		return item;
	}
	
	public static void employeeItemSelect(Integer choice) throws IOException, SQLException {
		item = itemsql.getById(choice);
		int offerChoice = 0;
		do {
			System.out.println("\nYou have selected item: " + item.getName() + " and are viewing all current offers\n"
								+ "0: Return to list of items\n"
								+ "-1: Delete selected item\n"
								+ "Enter and select offer ID to accept offer\n");
			//offers for item with id == choice
			offersql.getOfferListByItem(choice);
			in = new Scanner(System.in);
			offerChoice = in.nextInt();
			if (offerChoice == -1) {
				itemsql.deleteItem(item.getId());
				System.out.println(item.getName() + " deleted, returning to list of items");
				employeeItemView();
			} else if (offerChoice == 0) {
				//empty to bypass offer selection if return to list of items is selected
			} else {
				//accepts offer with ID == offerChoice
				offersql.acceptOffer(offerChoice);
				System.out.println("\nReturning to list of items");
				employeeItemView();
			}
		} while(offerChoice != 0);
		System.out.println("\nReturning to list of items");
		employeeItemView();
	}
	
	public static void employeeViewPayments() throws IOException, SQLException {
		paysql.getWeeklyPayments();
		in.nextLine();
		System.out.println("Press enter to return to employee menu");
		in.nextLine();
		employeeMenu();
	}
}
