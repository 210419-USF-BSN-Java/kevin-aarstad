package com.revature.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.ReimbursementType;
import com.revature.util.ConnectionUtil;



public class ReimbursementTypePostgres implements ReimbursementTypeDao{

	@Override
	public ReimbursementType add(ReimbursementType t) {
		ReimbursementType reimbType = new ReimbursementType();
		String sql = "insert into ers_reimbursement_type (reimb_type) values (?) returning reimb_type_id;";
	
	try (Connection con = ConnectionUtil.getConnectionFromFile()){
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, t.getType());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			reimbType = t;
			reimbType.setId(rs.getInt(1));
		}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		return null;
	}

	@Override
	public ReimbursementType getById(Integer id) {
		ReimbursementType rt = new ReimbursementType();
		String sql = "select * from ers_reimbursement_type where reimb_type_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				rt.setId(id);
				rt.setType(rs.getString("reimb_type"));				
		}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return rt;
	}


	@Override
	public List<ReimbursementType> getAll() {
		List<ReimbursementType> types = new ArrayList<>();
		String sql = "select * from ers_reimbursement_type;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("reimb_type_id");
				String type = rs.getString("reimb_type");
				
				types.add(new ReimbursementType(id, type));
		}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return types;
	}

	@Override
	public Boolean update(ReimbursementType t) {
		String sql = "update ers_reimbursement_type set reimb_type = ? where reimb_type_id = ?;";
		boolean success = false;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getType());
			ps.setInt(2, t.getId());
			
			ps.executeUpdate();
			success = true;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return success;
	}

	@Override
	public Boolean delete(ReimbursementType t) {
		String sql = "delete from ers_reimbursement_type where reimb_type_id = ?;";
		boolean success = false;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getId());
			
			ps.executeUpdate();
			success = true;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		return success;
	}

}
