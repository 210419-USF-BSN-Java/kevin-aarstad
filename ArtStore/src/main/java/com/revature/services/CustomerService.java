package com.revature.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.daos.CustomerPostgresql;
import com.revature.daos.ItemPostgresql;
import com.revature.daos.OfferPostgresql;
import com.revature.daos.PaymentPostgresql;
import com.revature.models.Customer;
import com.revature.models.Item;
import com.revature.models.Offer;

public abstract class CustomerService {

	public static Scanner in;
	private static Customer customer = new Customer();
	private static Item item = new Item();
	private static Offer offer = new Offer();
	private static int customerSkip = 0;
	private static CustomerPostgresql custsql = new CustomerPostgresql();
	private static ItemPostgresql itemsql = new ItemPostgresql();
	private static OfferPostgresql offersql = new OfferPostgresql();
	private static PaymentPostgresql paysql = new PaymentPostgresql();
	
	public static Customer customerMenu () throws IOException, SQLException {
		//skip variable enables bypassing asking for username if already entered in a session
		if (customerSkip == 0) {
			System.out.println("\nPlease enter your customer username:");
			in = new Scanner(System.in);
			String username = in.nextLine();		
			customer = custsql.getByName(username);
			//compares customer name to list, if found select that customer, if DNE create new		
			if (customer.getName() == null) {
				customer.setName(username);
				custsql.add(customer);
				customer = custsql.getByName(username);
				System.out.println("New customer created! Username: " + customer.getName());
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
				customerItemListView();
		 		break;
		 	case 2:
		 		customerOwnedItems();
		 		break;
		 	case 3:
		 		customerPayments();
		 		break;
		 	default:
		 		System.out.println("\nPlease enter a valid selection."); 
			}
			
		} while (choice < 0 || choice > 2);
		return customer;
	}
	
	public static void customerItemListView() throws IOException, SQLException {
		int choice = 0;
		in = new Scanner(System.in);
		do {
			System.out.println("\nItems for sale, please select an option. \n"
							+ "0: Return to Customer Menu.\n"
							+ "Select item number to view item.\n"
							+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
						offersql.add(offer);					
					}
				}				
			}
			
		} while (choice != 0);
		customerMenu();
	}
	
	public static void customerOwnedItems() throws IOException, SQLException {
		in = new Scanner(System.in);
		System.out.println("\nYour owned items. Press Enter to return to Customer Menu.\n"
						+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		itemsql.getByCustomerId(customer.getId());
		in.nextLine();
		customerMenu();
	}
	
	public static void customerPayments() throws IOException, SQLException {
		in = new Scanner(System.in);
		System.out.println("\nYour owed payments. Press Enter to return to Customer Menu");
		paysql.getByCustomerId(customer.getId());
		in.nextLine();
		customerMenu();
	}
}
