package com.revature.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;


public class ReimbursementPostgres implements ReimbursementDao{
	UserPostgres up = new UserPostgres();
	ReimbursementStatusPostgres rsp = new ReimbursementStatusPostgres();
	ReimbursementTypePostgres rtp = new ReimbursementTypePostgres();
	
	
	@Override
	public Reimbursement add(Reimbursement t) {
		Reimbursement reimbItem = null;
		String sql = "insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) values (?, CURRENT_TIMESTAMP, ?, ?, 1, ?) returning reimb_id;";
		System.out.println("*****ReimbursementPostgres - add*****");
		
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			System.out.println("in try");
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1,  t.getAmount());
			ps.setString(2, t.getDescription());
			ps.setInt(3, t.getAuthor());
			ps.setInt(4, t.getType());
			
			ResultSet rs = ps.executeQuery();
			System.out.println("after query");
			if (rs.next()) {
				reimbItem = t;
				reimbItem.setId(rs.getInt(1));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return reimbItem;
	}

	@Override
	public Reimbursement getById(Integer id) {
		Reimbursement ri = new Reimbursement();
		String sql = "select * from ers_reimbursement where reimb_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				ri.setId(id);
				ri.setAmount(rs.getDouble("reimb_amount"));
				ri.setSubmitted(rs.getTimestamp("reimb_submitted"));
				ri.setResolved(rs.getTimestamp("reimb_resolved"));
				ri.setDescription(rs.getString("reimb_description"));
				ri.setAuthor(rs.getInt("reimb_author"));
				ri.setResolver(rs.getInt("reimb_resolver"));
				ri.setStatus(rs.getInt("reimb_status_id"));
				ri.setType(rs.getInt("reimb_type_id"));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return ri;
	}

	@Override
	public List<Reimbursement> getAll() {
		List<Reimbursement> items = new ArrayList<>();
		String sql = "select * from ers_reimbursement";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("reimb_id");
				Double amount = rs.getDouble("reimb_amount");
				Timestamp submitted = rs.getTimestamp("reimb_submitted");
				Timestamp resolved = rs.getTimestamp("reimb_resolved");
				String description = rs.getString("reimb_description");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new Reimbursement(id, amount, submitted, resolved, description, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public Reimbursement update(Reimbursement t) {
		Reimbursement r = null;
		String sql = "update ers_reimbursement set reimb_amount = ?, reimb_submitted = ?, reimb_resolved = ?, reimb_description = ?, reimb_author = ?, reimb_resolver = ?, reimb_status_id = ?, reimb_type_id = ? where reimb_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, t.getAmount());
			ps.setTimestamp(2, new Timestamp(t.getSubmitted().getTime()));
			ps.setTimestamp(3, new Timestamp(t.getResolved().getTime()));
			ps.setString(4, t.getDescription());
			ps.setInt(6, t.getAuthor());
			ps.setInt(7, t.getResolver());
			ps.setInt(8, t.getStatus());
			ps.setInt(9, t.getType());
			
			ps.executeUpdate();
			
			r = t;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public Boolean delete(Reimbursement t) {
		String sql = "delete from ers_reimbursement where reimb_id = ?;";
		boolean success = false;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getId());
			
			ps.executeUpdate();
			success = true;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public List<Reimbursement> getAllPending() {
		List<Reimbursement> items = new ArrayList<>();
		String sql = "select * from ers_reimbursement where reimb_status_id = 1";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("reimb_id");
				Double amount = rs.getDouble("reimb_amount");
				Timestamp submitted = rs.getTimestamp("reimb_submitted");
				Timestamp resolved = rs.getTimestamp("reimb_resolved");
				String description = rs.getString("reimb_description");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new Reimbursement(id, amount, submitted, resolved, description, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}	
		return items;
	}
	
	public List<Reimbursement> getAllResolved() {
		List<Reimbursement> items = new ArrayList<>();
		String sql = "select * from ers_reimbursement where reimb_status_id != 1";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("reimb_id");
				Double amount = rs.getDouble("reimb_amount");
				Timestamp submitted = rs.getTimestamp("reimb_submitted");
				Timestamp resolved = rs.getTimestamp("reimb_resolved");
				String description = rs.getString("reimb_description");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new Reimbursement(id, amount, submitted, resolved, description, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}	
		return items;
	}
	
	public List<Reimbursement> getAllRequestsByEmployee(Integer id) {
		List<Reimbursement> items = new ArrayList<>();
		String sql = "select * from ers_reimbursement where reimb_author = ?";
		System.out.println("*****ReimbursementPostgres - getAllRequestsByEmployee*****");
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int reimb_id = rs.getInt("reimb_id");
				Double amount = rs.getDouble("reimb_amount");
				Timestamp submitted = rs.getTimestamp("reimb_submitted");
				Timestamp resolved = rs.getTimestamp("reimb_resolved");
				String description = rs.getString("reimb_description");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new Reimbursement(reimb_id, amount, submitted, resolved, description, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public List<Reimbursement> employeeGetAllPending(Integer id){
		List<Reimbursement> items = new ArrayList<>();

		String sql = "select * from ers_reimbursement where reimb_author = ? and reimb_status_id = 1";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int reimb_id = rs.getInt("reimb_id");
				Double amount = rs.getDouble("reimb_amount");
				Timestamp submitted = rs.getTimestamp("reimb_submitted");
				Timestamp resolved = rs.getTimestamp("reimb_resolved");
				String description = rs.getString("reimb_description");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new Reimbursement(reimb_id, amount, submitted, resolved, description, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public List<Reimbursement> employeeGetAllComplete(Integer id){
		List<Reimbursement> items = new ArrayList<>();

		String sql = "select * from ers_reimbursement where reimb_author = ? and reimb_status_id = 2";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int reimb_id = rs.getInt("reimb_id");
				Double amount = rs.getDouble("reimb_amount");
				Timestamp submitted = rs.getTimestamp("reimb_submitted");
				Timestamp resolved = rs.getTimestamp("reimb_resolved");
				String description = rs.getString("reimb_description");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new Reimbursement(reimb_id, amount, submitted, resolved, description, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public boolean resolveReimbursement(int id, int choice, int resolver) {
		String sql = "update ers_reimbursement set reimb_resolved = CURRENT_TIMESTAMP, reimb_resolver = ?, reimb_status_id = ? where reimb_id = ?;";
		boolean success = false;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);		
			ps.setInt(1, resolver);
			ps.setInt(2, choice);
			ps.setInt(3, id);

			ps.executeUpdate();
			success = true;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return success;
	}


}
