package com.revature.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.ReimbursementStatus;
import com.revature.util.ConnectionUtil;


public class ReimbursementStatusPostgres implements ReimbursementStatusDao{

	@Override
	public ReimbursementStatus add(ReimbursementStatus t) {
		ReimbursementStatus reimbStatus = new ReimbursementStatus();
		String sql = "insert into ers_reimbursement_status (reimb_status) values (?) returning reimb_status_id;";
	
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getStatus());
		
			ResultSet rs = ps.executeQuery();
		
			if (rs.next()) {
				reimbStatus = t;
				reimbStatus.setId(rs.getInt(1));
		}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ReimbursementStatus getById(Integer id) {
		ReimbursementStatus reimbStatus = new ReimbursementStatus();
		String sql = "select * from ers_reimbursement_status where reimb_status_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				reimbStatus.setId(id);
				reimbStatus.setStatus(rs.getString("reimb_status"));				
		}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}		
		return reimbStatus;
	}

	@Override
	public List<ReimbursementStatus> getAll() {
		List<ReimbursementStatus> statuses = new ArrayList<>();
		String sql = "select * from ers_reimbursement_status;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("reimb_status_id");
				String status = rs.getString("reimb_status");
				
				statuses.add(new ReimbursementStatus(id, status));
		}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}		
		return statuses;
	}

	@Override
	public ReimbursementStatus update(ReimbursementStatus t) {
		ReimbursementStatus rs= null;
		String sql = "update ers_reimbursement_status set reimb_status = ? where reimb_status_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getStatus());
			ps.setInt(2, t.getId());
			
			ps.executeUpdate();
			
			rs = t;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}		
		return rs;
	}

	@Override
	public Boolean delete(ReimbursementStatus t) {
		String sql = "delete from ers_reimbursement_status where reimb_status_id = ?;";
		boolean success = false;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getId());
			
			ps.executeUpdate();	
			success = true;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}	
		
		return success;
	}

}
