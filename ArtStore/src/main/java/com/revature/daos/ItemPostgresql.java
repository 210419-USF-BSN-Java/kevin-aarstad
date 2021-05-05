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

public class ItemPostgresql implements ItemDao{
	private static Logger log = (Logger) LogManager.getRootLogger();	
	CustomerPostgresql cust = new CustomerPostgresql();
	
	@Override
	public Item add(Item t) throws SQLException, IOException {
		Item item = null;
		String sql = "insert into items (item_name, item_owned) values (?,'false') returning item_id;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				item = t;
				item.setId(rs.getInt(1));
				
				System.out.println("Added " + item.getName());
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		
		return item;
	}

	@Override
	public Item getById(Integer id) {
		Item item = new Item();
		String sql = "select * from items where item_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				item.setId(id);
				item.setName(rs.getString("item_name"));
				item.setOwned(rs.getBoolean("item_owned"));
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return item;
	}

	@Override
	public List<Item> getAll() {
		List<Item> items = new ArrayList<>();
		String sql = "select * from items;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int item_id = rs.getInt("item_id");
				String item_name = rs.getString("item_name");
				Boolean item_owned = rs.getBoolean("item_owned");
				Customer customer = cust.getById(rs.getInt("cust_id"));
				
				items.add(new Item(item_id, item_name, item_owned, customer));
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return items;
	}

	@Override
	public Integer update(Item t) {
		String sql = "update items set item_name = ?, item_owned = ?, cust_id = ? where item_id = ?;";
		//will return -1 if didn't execute, 0 if tried and failed to update, 1 if successful.
		int a = -1;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setBoolean(2, t.getOwned());
			ps.setInt(3, t.getCustomer().getId());
			ps.setInt(4, t.getId());		
			
			a = ps.executeUpdate();
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return a;
	}

	@Override
	public Integer delete(Item t) {
		String sql = "delete from items where item_id = ? cascade;";
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
	public List<Item> getByName(String name) {
		Item item = new Item();
		String sql = "select * from items where item_name = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				item.setId(rs.getInt("item_id"));
				item.setName(name);
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return null;
	}
	
	public List <Item> getAvailableItems(){
		List<Item> items = new ArrayList<>();
		String sql = "select * from items where item_owned = 'false';";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int item_id = rs.getInt("item_id");
				String item_name = rs.getString("item_name");
				Boolean item_owned = rs.getBoolean("item_owned");
				Customer customer = cust.getById(rs.getInt("cust_id"));
				
				items.add(new Item(item_id, item_name, item_owned, customer));
				System.out.println(item_id + ": " + item_name);
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return items;
	}
	
	public List<Item> getByCustomerId(Integer id){
		List<Item> items = new ArrayList<>();
		String sql = "select * from items where cust_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int item_id = rs.getInt("item_id");
				String item_name = rs.getString("item_name");
				Boolean item_owned = rs.getBoolean("item_owned");
				Customer customer = cust.getById(rs.getInt("cust_id"));
				
				items.add(new Item(item_id, item_name, item_owned, customer));
				System.out.println(item_id + ": " + item_name);
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}	
		return items;
	}
	
	public Item deleteItem(Integer id) {
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			CallableStatement call = con.prepareCall("{call delete_item(?) }");
			call.setInt(1, id);
			call.execute();

			
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		return null;
	}

	public static void errorLog(String s) { // error outputs string 's'
		((org.apache.logging.log4j.Logger) log).error(s);
		log.info("");
	}
}
