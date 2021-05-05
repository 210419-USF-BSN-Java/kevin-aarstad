package com.revature.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import util.ConnectionUtil;
import com.revature.models.Customer;

public class CustomerPostgresql implements CustomerDao{
	private static Logger log = (Logger) LogManager.getRootLogger();
	
	@Override
	public Customer add(Customer t) throws SQLException, IOException {
		Customer customer = null;
		String sql = "insert into customers (cust_name) values (?) returning cust_id;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				customer = t;
				customer.setId(rs.getInt(1));
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		
		return null;
	}

	@Override
	public Customer getById(Integer id) {
		Customer customer = new Customer();
		String sql = "select * from customers where cust_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				customer.setId(id);
				customer.setName(rs.getString("cust_name"));				
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return customer;
	}

	@Override
	public List<Customer> getAll() {
		List<Customer> customers = new ArrayList<>();
		String sql = "select * from customers;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int cust_id = rs.getInt("cust_id");
				String cust_name = rs.getString("cust_name");
				
				customers.add(new Customer(cust_id, cust_name));
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return null;
	}

	@Override
	public Integer update(Customer t) {
		String sql = "update customers set cust_name = ? where cust_id = ?;";
		//will return -1 if didn't execute, 0 if tried and failed to update, 1 if successful.
		int a = -1;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setInt(2, t.getId());
			
			a = ps.executeUpdate();
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return a;
	}

	@Override
	public Integer delete(Customer t) {
		String sql = "delete from customers where cust_id = ?;";
		//will return -1 if failed to execute, 0 if didn't delete, 1 if delete occurred
		int a = -1;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getId());
			
			a = ps.executeUpdate();	
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}	
		
		return a;
	}

	@Override
	public Customer getByName(String name) {
		Customer customer = new Customer();
		String sql = "select * from customers where cust_name = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				customer.setId(rs.getInt("cust_id"));
				customer.setName(name);				
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return customer;
	}
	
	public static void errorLog(String s) { // error outputs string 's'
		((org.apache.logging.log4j.Logger) log).error(s);
		log.info("");
	}	
}
