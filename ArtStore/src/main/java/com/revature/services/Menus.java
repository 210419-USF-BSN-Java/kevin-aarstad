package com.revature.services;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import com.revature.daos.CustomerPostgresql;
import com.revature.daos.EmployeePostgresql;
import com.revature.daos.ItemPostgresql;
import com.revature.daos.OfferPostgresql;
import com.revature.daos.PaymentPostgresql;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.Item;
import com.revature.models.Offer;

public abstract class Menus{

	public static Scanner in;
	
	private static int customerSkip = 0;
	private static int employeeSkip = 0;
	private static Customer customer = new Customer();
	private static Employee employee = new Employee();
	private static Item item = new Item();
	private static Offer offer = new Offer();
	
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
		 		Menus.customerMenu();
		 		break;
		 	case 2:
		 		Menus.employeeMenu();
		 		break;
		 	default:
		 		System.out.println("Please enter a valid selection.");
			}
			
		} while (choice < 0 || choice > 2);

	}
	
	public static void customerMenu () throws IOException, SQLException {
		//skip variable enables bypassing asking for username if already entered in a session
		if (customerSkip == 0) {
			CustomerPostgresql custsql = new CustomerPostgresql();
			System.out.println("\nPlease enter your customer username:");
			in = new Scanner(System.in);
			String username = in.nextLine();		
			customer = custsql.getByName(username);
			//compares customer name to list, if found select that customer, if DNE create new		
			if (customer.getName() == null) {
				customer.setName(username);
				custsql.add(customer);
				customer = custsql.getByName(username);
			}
		customerSkip++;
		}
		//prints menu from file
		System.out.println("\nWelcome " + customer.getName() + ", please select an option.\n"
							+ "0: Exit\n"
							+ "1: View items for sale\n"
							+ "2: View owned items\n"
							+ "3: View payments owed");
		
		in = new Scanner(System.in);
		int choice = 0;
		do  {
			choice = in.nextInt();
			switch (choice) {
			case 0:
				System.exit(0);
		 		break;
			case 1:
				Menus.customerItemListView();
		 		break;
		 	case 2:
		 		Menus.customerOwnedItems();
		 		break;
		 	case 3:
		 		Menus.customerPayments();
		 		break;
		 	default:
		 		System.out.println("\nPlease enter a valid selection."); 
			}
			
		} while (choice < 0 || choice > 2);

	}
	
	public static void customerItemListView() throws IOException, SQLException {
		int choice = 0;
		in = new Scanner(System.in);
		do {
			System.out.println("\nItems for sale, please select an option. \n"
							+ "0: Return to Customer Menu.\n"
							+ "Select item number to view item.\n"
							+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			ItemPostgresql itemsql = new ItemPostgresql();
			itemsql.getAvailableItems();
			choice = in.nextInt();

			if (choice != 0) {			
				//use choice to look up index of item
			
					item = itemsql.getById(choice);
					if (item.getName() == null) {
						System.out.println("\nPlease enter a valid item ID");
					}else {	
					System.out.println("\nYou have selected: " + item.getName() + ".\n"
									+"Enter amount then press enter to make an offer (All accepted offers are paid via four weekly payments).\n"
									+"Enter 0 to return to Customer Menu.\n"
									+"Enter -1 to return to Item List.");
					choice = in.nextInt();
				
					if (choice != 0 && choice != -1) {
						//adds offer with the value of choice
						offer = new Offer(choice, customer, item);
						OfferPostgresql offersql = new OfferPostgresql();
						offersql.add(offer);					
					}
				}
				
			}
			
		} while (choice != 0);
		Menus.customerMenu();
	}
	
	public static void customerOwnedItems() throws IOException, SQLException {
		in = new Scanner(System.in);
		System.out.println("\nYour owned items. Press Enter to return to Customer Menu.\n"
						+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		ItemPostgresql itemsql = new ItemPostgresql();
		itemsql.getByCustomerId(customer.getId());
		in.nextLine();
		Menus.customerMenu();
	}
	
	public static void customerPayments() throws IOException, SQLException {
		in = new Scanner(System.in);
		System.out.println("\nYour owed payments. Press Enter to return to Customer Menu");
		PaymentPostgresql paysql = new PaymentPostgresql();
		paysql.getByCustomerId(customer.getId());
		in.nextLine();
		Menus.customerMenu();
	}
	
	public static void employeeMenu() throws IOException, SQLException {
		//compare name entered to list. If found select that employee, if DNE create new
		if (employeeSkip == 0) {
			EmployeePostgresql empsql = new EmployeePostgresql();
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
				Menus.employeeItemView();
				break;
			case 2:
				Menus.employeeViewPayments();
				break;
			default:
				System.out.println("\nPlease enter a valid selection.");
				break;
			} 
		} while (choice < 0 || choice > 2);	
	}
	
	public static void employeeItemView() throws IOException, SQLException {
		int choice = 0;	
		do {
		System.out.println("\nItems currently for sale.\n"
						+ "0: Return to Employee Menu\n"
						+ "-1: Add new item\n"
						+ "Select item number to view item\n"
						+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		ItemPostgresql itemsql = new ItemPostgresql();
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
				Menus.employeeItemSelect(choice);
			}
		}
		}while (choice != 0);
		Menus.employeeMenu();
	}
	
	public static void employeeItemSelect(Integer choice) throws IOException, SQLException {
		ItemPostgresql itemsql = new ItemPostgresql();
		item = itemsql.getById(choice);
		int offerChoice = 0;
		do {
			System.out.println("\nYou have selected item: " + item.getName() + " and are viewing all current offers\n"
								+ "0: Return to list of items\n"
								+ "-1: Delete selected item\n"
								+ "Enter and select offer ID to accept offer\n");
			//offers for item with id == choice
			OfferPostgresql offersql = new OfferPostgresql();
			offersql.getOfferListByItem(choice);
			in = new Scanner(System.in);
			offerChoice = in.nextInt();
			if (offerChoice == -1) {
				itemsql.deleteItem(item.getId());
				System.out.println(item.getName() + " deleted, returning to list of items");
				Menus.employeeItemView();
			} else if (offerChoice == 0) {
				//empty to bypass offer selection if return to list of items is selected
			} else {
				//accepts offer with ID == offerChoice
				offersql.acceptOffer(offerChoice);
				System.out.println("\nReturning to list of items");
				Menus.employeeItemView();
			}
		} while(offerChoice != 0);
		System.out.println("\nReturning to list of items");
		Menus.employeeItemView();
	}
	
	public static void employeeViewPayments() throws IOException, SQLException {
		PaymentPostgresql paysql = new PaymentPostgresql();
		paysql.getWeeklyPayments();
		System.out.println("Press enter to return to employee menu");
		in.nextLine();
		Menus.employeeMenu();
	}
	
}
