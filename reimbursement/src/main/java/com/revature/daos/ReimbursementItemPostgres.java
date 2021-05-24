package com.revature.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.ReimbursementItem;
import com.revature.util.ConnectionUtil;


public class ReimbursementItemPostgres implements ReimbursementItemDao{
	UserPostgres up = new UserPostgres();
	ReimbursementStatusPostgres rsp = new ReimbursementStatusPostgres();
	ReimbursementTypePostgres rtp = new ReimbursementTypePostgres();
	
	
	@Override
	public ReimbursementItem add(ReimbursementItem t) {
		ReimbursementItem reimbItem = null;
		String sql = "insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_reciept, reimb_author, reimb_status_id, reimb_type_id) values (?, CURRENT_TIMESTAMP, ?, ?, ?, ? returning reimb_id;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1,  t.getAmount());
			ps.setTimestamp(2, new Timestamp(t.getSubmitted().getTime()));
			ps.setTimestamp(3, new Timestamp(t.getResolved().getTime()));
			ps.setString(4, t.getDescription());
			ps.setString(5, t.getReceipt());
			ps.setInt(6, t.getAuthor());
			ps.setInt(7, t.getResolver());
			ps.setInt(8, t.getStatus());
			ps.setInt(9, t.getType());
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				reimbItem = t;
				reimbItem.setId(rs.getInt(1));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return reimbItem;
	}

	@Override
	public ReimbursementItem getById(Integer id) {
		ReimbursementItem ri = new ReimbursementItem();
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
				ri.setReceipt(rs.getString("reimb_reciept"));
				ri.setAuthor(rs.getInt("reimb_author"));
				ri.setResolver(rs.getInt("reimb_resolver"));
				ri.setStatus(rs.getInt("reimb_status_id"));
				ri.setType(rs.getInt("reimb_type_id"));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ri;
	}

	@Override
	public List<ReimbursementItem> getAll() {
		List<ReimbursementItem> items = new ArrayList<>();
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
				String receipt = rs.getString("reimb_receipt");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new ReimbursementItem(id, amount, submitted, resolved, description, receipt, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		return items;
	}

	@Override
	public Boolean update(ReimbursementItem t) {
		String sql = "update ers_reimbursement set reimb_amount = ?, reimb_submitted = ?, reimb_resolved = ?, reimb_description = ?, reimb_receipt = ?, reimb_author = ?, reimb_resolver = ?, reimb_status_id = ?, reimb_type_id = ? where reimb_id = ?;";
		boolean success = false;
		
		try(Connection con = ConnectionUtil.getConnectionH2()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, t.getAmount());
			ps.setTimestamp(2, new Timestamp(t.getSubmitted().getTime()));
			ps.setTimestamp(3, new Timestamp(t.getResolved().getTime()));
			ps.setString(4, t.getDescription());
			ps.setString(5, t.getReceipt());
			ps.setInt(6, t.getAuthor());
			ps.setInt(7, t.getResolver());
			ps.setInt(8, t.getStatus());
			ps.setInt(9, t.getType());
			
			ps.executeUpdate();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public Boolean delete(ReimbursementItem t) {
		String sql = "delete from ers_reimbursement where reimb_id = ?;";
		boolean success = false;
		
		try(Connection con = ConnectionUtil.getConnectionH2()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getId());
			
			ps.executeUpdate();
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public List<ReimbursementItem> getAllPending() {
		List<ReimbursementItem> items = new ArrayList<>();
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
				String receipt = rs.getString("reimb_receipt");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new ReimbursementItem(id, amount, submitted, resolved, description, receipt, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		return items;
	}
	
	public List<ReimbursementItem> getAllResolved() {
		List<ReimbursementItem> items = new ArrayList<>();
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
				String receipt = rs.getString("reimb_receipt");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new ReimbursementItem(id, amount, submitted, resolved, description, receipt, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		return items;
	}
	
	public List<ReimbursementItem> getAllRequestsByEmployee(Integer id) {
		List<ReimbursementItem> items = new ArrayList<>();
		String sql = "select * from ers_reimbursement where reimb_author = ?";
		
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
				String receipt = rs.getString("reimb_receipt");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new ReimbursementItem(reimb_id, amount, submitted, resolved, description, receipt, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		return items;
	}
	
	public List<ReimbursementItem> employeeGetAllPending(Integer id){
		List<ReimbursementItem> items = new ArrayList<>();

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
				String receipt = rs.getString("reimb_receipt");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new ReimbursementItem(reimb_id, amount, submitted, resolved, description, receipt, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return items;
	}
	
	public List<ReimbursementItem> employeeGetAllComplete(Integer id){
		List<ReimbursementItem> items = new ArrayList<>();

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
				String receipt = rs.getString("reimb_receipt");
				int author = rs.getInt("reimb_author");
				int resolver = rs.getInt("reimb_resolver");
				int reimbStatus = rs.getInt("reimb_status_id");
				int reimbType = rs.getInt("reimb_type_id");
				
				items.add(new ReimbursementItem(reimb_id, amount, submitted, resolved, description, receipt, author, resolver, reimbStatus, reimbType));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return items;
	}
	
	public boolean resolveReimbursement(int id, int choice, int resolver) {
		String sql = "update ers_reimbursement set reimb_resolved = CURRENT_TIMESTAMP, reimb_resolver = ?, reimb_status = ? where reimb_id = ?;";
		boolean success = false;
		
		try(Connection con = ConnectionUtil.getConnectionH2()){
			PreparedStatement ps = con.prepareStatement(sql);		
			ps.setInt(1, resolver);
			ps.setInt(2, choice);
			ps.setInt(3, id);

			ps.executeUpdate();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}


}
