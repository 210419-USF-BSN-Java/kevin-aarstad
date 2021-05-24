package com.revature.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.util.ConnectionUtil;

public class RolePostgres implements RoleDao{

	@Override
	public Role add(Role t) {
		Role role = null;
		String sql = "insert into ers_user_roles (ers_user_role) values (?) returning ers_user_role_id;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getRoleName());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				role = t;
				role.setId(rs.getInt(1));
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
	public Role getById(Integer id) {
		Role role = new Role();
		String sql = "select * from ers_user_roles where ers_user_role_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				role.setId(id);
				role.setRoleName(rs.getString("ers_user_role"));				
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return role;
	}

	@Override
	public List<Role> getAll() {
		List<Role> roles = new ArrayList<>();
		String sql = "select * from ers_user_roles;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int ers_user_role_id = rs.getInt("ers_user_role_id");
				String ers_user_role = rs.getString("ers_user_role");
				
				roles.add(new Role(ers_user_role_id, ers_user_role));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return roles;
	}

	@Override
	public Boolean update(Role t) {
		String sql = "update ers_user_roles set ers_user_role = ? where ers_user_role_id = ?;";
		boolean success = false;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getRoleName());
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
	public Boolean delete(Role t) {
		String sql = "delete from ers_user_roles where ers_user_role_id = ?;";
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
