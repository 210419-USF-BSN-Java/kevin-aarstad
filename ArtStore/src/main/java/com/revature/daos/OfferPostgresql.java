package com.revature.daos;

import java.io.IOException;
import java.sql.CallableStatement;
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
import com.revature.models.Item;
import com.revature.models.Offer;

public class OfferPostgresql implements OfferDao{
	private static Logger log = (Logger) LogManager.getRootLogger();
	CustomerPostgresql cust = new CustomerPostgresql();
	ItemPostgresql itemsql = new ItemPostgresql();

	@Override
	public Offer add(Offer t) throws SQLException, IOException {
		Offer offer = null;
		String sql = "insert into offers (offer_value, cust_id, item_id) values (?, ?, ?) returning offer_id;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getValue());
			ps.setInt(2, t.getCustomer().getId());
			ps.setInt(3, t.getItem().getId());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				offer = t;
				offer.setId(rs.getInt(1));
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return offer;
	}

	@Override
	public Offer getById(Integer id) {
		Offer offer = new Offer();
		String sql = "select * from offers where offer_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				offer.setId(id);
				offer.setValue(rs.getInt("offer_value"));
				offer.setCustomer(cust.getById(rs.getInt("cust_id")));
				offer.setItem(itemsql.getById(rs.getInt("item_id")));
			}
			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
			
		return offer;
	}

	@Override
	public List<Offer> getAll() {
		List<Offer> offers = new ArrayList<>();
		String sql = "select * from offers;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				offers.add(new Offer(rs.getInt("offer_id"),
						rs.getInt("offer_value"),
						cust.getById(rs.getInt("cust_id")),
						itemsql.getById(rs.getInt("item_id"))
						));
			}
			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return offers;
	}

	@Override
	public Integer update(Offer t) {
		String sql = "update offers set offer_value = ?, cust_id = ?, item_id = ? where offer_id = ?;";
		//returns -1 if no execution, 0 if attempt and failed, 1 if successful
		int a = -1;

		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getValue());
			ps.setInt(2, t.getCustomer().getId());
			ps.setInt(3, t.getItem().getId());
			
			a = ps.executeUpdate();
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		return a;
	}

	@Override
	public Integer delete(Offer t) {
		String sql = "delete from offers where offer_id = ?;";
		//return -1 if no execution, 0 if attempt and failed, 1 if successful
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
	public List<Offer> getByValue(Integer value) {
		String sql = "select * from offers where offer_value = ?;";
		List<Offer> offers = new ArrayList<>();
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, value);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				offers.add(new Offer(rs.getInt("offer_id"),
						rs.getInt("offer_value"),
						cust.getById(rs.getInt("cust_id")),
						itemsql.getById(rs.getInt("item_id"))
						));
			}
			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		return offers;
	}
	
	public List<Offer> getOfferListByItem(Integer id){
		List<Offer> offers = new ArrayList<>();
		String sql = "select * from offers where item_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int offer_id = rs.getInt("offer_id");
				int offer_value = rs.getInt("offer_value");
				Customer customer = cust.getById(rs.getInt("cust_id"));
				Item item = itemsql.getById(rs.getInt("item_id"));
				
				offers.add(new Offer(offer_id, offer_value, customer, item));
				System.out.println(offer_id + ": $" + offer_value);
			}
			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
			
		return offers;
	}
	
	public int acceptOffer(Integer id) {
		int success = 0;
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			CallableStatement call = con.prepareCall("{call accept_offer(?) }");
			call.setInt(1, id);
			call.execute();

			success = 1;
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		return success;
	}
	
	public static void errorLog(String s) { // error outputs string 's'
		((org.apache.logging.log4j.Logger) log).error(s);
		log.info("");
	}
}
