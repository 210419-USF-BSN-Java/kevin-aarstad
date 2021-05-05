package com.revature.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.revature.models.Customer;
import com.revature.models.Payment;
import util.ConnectionUtil;


public class PaymentPostgresql implements PaymentDao{
	private static Logger log = (Logger) LogManager.getRootLogger();
	CustomerPostgresql cust = new CustomerPostgresql();
	
	@Override
	public Payment add(Payment t) throws SQLException, IOException {
		Payment payment = null;
		String sql = "insert into payments (pay_value, pay_remaining, cust_id) values (?, ?, ?) returning pay_id;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getValue());
			ps.setInt(2, t.getId());
			ps.setInt(3, t.getCustomer().getId());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				payment = t;
				payment.setId(rs.getInt(1));
				
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		
		return null;
	}

	@Override
	public Payment getById(Integer id) {
		Payment payment = new Payment();
		String sql = "select * from payments where pay_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				payment.setId(id);
				payment.setValue(rs.getInt("pay_value"));
				payment.setRemaining(rs.getInt("pay_remaining"));
				payment.setCustomer(cust.getById(rs.getInt("cust_id")));
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}	
		return payment;
	}

	@Override
	public List<Payment> getAll() {
		List<Payment> payments = new ArrayList<>();
		String sql = "select * from payments;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				payments.add(new Payment(rs.getInt("pay_id"), 
						rs.getInt("pay_value"), 
						rs.getInt("pay_remaining"), 
						cust.getById(rs.getInt("cust_id"))));
			}
			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}	
		
		return payments;
	}

	@Override
	public Integer update(Payment t) {
		String sql = "update payments set pay_value = ?, pay_remaining = ?, cust_id = ? where pay_id =?;";
		//will return -1 if no execution, 0 if tried and failed, 1 if successful
		int a = -1;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getValue());
			ps.setInt(2, t.getRemaining());
			ps.setInt(3, t.getCustomer().getId());
			ps.setInt(4, t.getId());
			
			a = ps.executeUpdate();	
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		return a;
	}

	@Override
	public Integer delete(Payment t) {
		String sql = "delete from payments where pay_id = ?;";
		//returns -1 if no execution, 0 if tries and failed, 1 if successful
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
	public List<Payment> getByValue(Integer value) {
		List<Payment> payments = new ArrayList<>();
		String sql = "select * from payments where pay_value = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, value);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				payments.add(new Payment(rs.getInt("pay_id"), 
						rs.getInt("pay_value"), 
						rs.getInt("pay_remaining"), 
						cust.getById(rs.getInt("cust_id"))));
			}
			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return payments;
	}
	
	public List<Payment> getByCustomerId(Integer id){
		List<Payment> payments = new ArrayList<>();
		String sql = "select * from payments where cust_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int pay_id = rs.getInt("pay_id");
				int pay_value = rs.getInt("pay_value");
				int pay_remaining = rs.getInt("pay_remaining");
				Customer customer = cust.getById(rs.getInt("cust_id"));
				payments.add(new Payment(pay_id, pay_value, pay_remaining, customer));
				System.out.println("Amount: $" + pay_value + ", Remaining Payments: " + pay_remaining);
			}
			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}	
		
		return payments;
	}
	
	public Integer getWeeklyPayments() {
		int total = 0;
		String sql = "select * from payments;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				total = total + rs.getInt("pay_value");
			}
			System.out.println("\nTotal weekly sum of payments = $" + total);
			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}	
		
		return total;
	}

	public static void errorLog(String s) { // error outputs string 's'
		((org.apache.logging.log4j.Logger) log).error(s);
		log.info("");
	}
}
