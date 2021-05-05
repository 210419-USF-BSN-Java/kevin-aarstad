package com.revature.shop;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Test;
import com.revature.daos.CustomerPostgresql;
import com.revature.daos.ItemPostgresql;
import com.revature.daos.OfferPostgresql;
import com.revature.daos.PaymentPostgresql;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.Item;
import com.revature.models.Offer;
import com.revature.models.Payment;
import com.revature.services.CustomerService;
import com.revature.services.EmployeeService;

public class StoreTest {

	private CustomerPostgresql custsql = new CustomerPostgresql();
	private ItemPostgresql itemsql = new ItemPostgresql();
	private OfferPostgresql offersql = new OfferPostgresql();
	private PaymentPostgresql paymentsql = new PaymentPostgresql();

	
	
	@Test
	public void employeeMenu() throws IOException, SQLException {
		assertTrue(EmployeeService.employeeMenu() instanceof Employee);
	}
	
	@Test
	public void addItem() throws IOException, SQLException {
		Item item = new Item();
		Scanner in = new Scanner(System.in);
		System.out.println("Item name");
		String name = in.nextLine();
		item.setName(name);
		
		assertTrue(itemsql.add(item) instanceof Item);
		in.close();
	}
	
	@Test
	public void getAvailableItems() throws IOException, SQLException {
		ArrayList<Item> items = (ArrayList<Item>) itemsql.getAvailableItems();

		assertNotNull(items);
	}
	
	@Test
	public void viewPaymentSum() {
		assertNotNull(paymentsql.getWeeklyPayments());
	}
	
	@Test
	public void customerMenu() throws IOException, SQLException {
		assertTrue(CustomerService.customerMenu() instanceof Customer);
	}
	
	@Test
	public void customerOwnedItems() {
		Scanner in = new Scanner(System.in);
		System.out.println("Customer id");
		int id = 0;
		id = in.nextInt();
		ArrayList<Item> items = (ArrayList<Item>) itemsql.getByCustomerId(id);
		
		assertNotNull(items);
		in.close();
	}
	
	@Test
	public void customerPayments() {
		Scanner in = new Scanner(System.in);
		System.out.println("Customer id");
		int id = 0;
		id = in.nextInt();
		ArrayList<Payment> payments = (ArrayList<Payment>) paymentsql.getByCustomerId(id);
		
		assertNotNull(payments);
		in.close();
	}
	
	@Test
	public void addOffer() throws SQLException, IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("Offer value");
		int offer_value = in.nextInt();
		System.out.println("Customer id");
		int cust_id = in.nextInt();
		System.out.println("Item id");
		int item_id = in.nextInt();
		Customer customer = new Customer();
		customer = custsql.getById(cust_id);
		Item item = new Item();
		item = itemsql.getById(item_id);
		Offer offer = new Offer();
		offer.setValue(offer_value);
		offer.setCustomer(customer);
		offer.setItem(item);
		
		assertTrue(offersql.add(offer) instanceof Offer);
		in.close();
	}
	
	@Test
	public void acceptOffer() {
		Scanner in = new Scanner(System.in);
		System.out.println("Offer id");
		int id = 0;
		id = in.nextInt();
		assertEquals(offersql.acceptOffer(id), 1);
		in.close();
	}
}
