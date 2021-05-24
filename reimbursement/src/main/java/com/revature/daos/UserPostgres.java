package com.revature.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserPostgres implements UserDao{
	RolePostgres rp = new RolePostgres();

	@Override
	public User add(User t) {
		User user = null;
		String sql = "insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) "
					+ "values (?, ?, ?, ?, ?, ?) returning ers_users_id;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getRole());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user = t;
				user.setId(rs.getInt(1));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return user;
	}

	@Override
	public User getById(Integer id) {
		User user = new User();
		String sql = "select * from ers_users where ers_user_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user.setId(id);
				user.setUsername(rs.getString("ers_username"));
				user.setPassword(rs.getString("ers_password"));
				user.setFirstName(rs.getString("user_first_name"));
				user.setLastName(rs.getString("user_last_name"));
				user.setEmail(rs.getString("user_email"));
				user.setRole(rs.getInt("user_role_id"));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		String sql = "select * from ers_users;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int ers_users_id = rs.getInt("ers_users_id");
				String username = rs.getString("ers_username");
				String password = rs.getString("ers_password");
				String firstName = rs.getString("user_first_name");
				String lastName = rs.getString("user_last_name");
				String email = rs.getString("user_email");
				int role = rs.getInt("user_role_id");
				
				users.add(new User(ers_users_id, username, password, firstName, lastName, email, role));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return users;
	}

	@Override
	public Boolean update(User t) {
		String sql = "update ers_users set ers_username = ?, ers_password = ?, user_first_name = ?, user_last_name = ?, user_email = ?, user_role_id = ? where ers_users_id = ?;";
		boolean success = false;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getUsername());
			ps.setString(2, t.getPassword());
			ps.setString(3, t.getFirstName());
			ps.setString(4, t.getLastName());
			ps.setString(5, t.getEmail());
			ps.setInt(6, t.getRole());
			
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
	public Boolean delete(User t) {
		String sql = "delete from ers_users where ers_users_id = ?;";
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
	
	public List<User> getAllEmployees() throws ClassNotFoundException {
		List<User> users = new ArrayList<>();
		String sql = "select * from ers_users where user_role_id = 1;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int ers_users_id = rs.getInt("ers_users_id");
				String username = rs.getString("ers_username");
				String password = rs.getString("ers_password");
				String firstName = rs.getString("user_first_name");
				String lastName = rs.getString("user_last_name");
				String email = rs.getString("user_email");
				int role = rs.getInt("user_role_id");
				
				users.add(new User(ers_users_id, username, password, firstName, lastName, email, role));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}		
		return users;
	}
	
	public User getByUsername(String username) throws ClassNotFoundException {
		User user = new User();
		String sql = "select * from ers_users where ers_username = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user.setId(rs.getInt("ers_user_id"));
				user.setUsername(username);
				user.setPassword(rs.getString("ers_password"));
				user.setFirstName(rs.getString("user_first_name"));
				user.setLastName(rs.getString("user_last_name"));
				user.setEmail(rs.getString("user_email"));
				user.setRole(rs.getInt("user_role_id"));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User getByUsernameAndPassword(String username, String password) throws ClassNotFoundException {
		User user = new User();
		String sql = "select * from ers_users where ers_username = ? AND ers_password = ?;";
		System.out.println(("~~~~~~~~~Looking for " + username + " " + password + "~~~~~~~~~"));
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				user.setUsername(username);
				user.setPassword(password);
				user.setId(rs.getInt("ers_users_id"));
				user.setFirstName(rs.getString("user_first_name"));
				user.setLastName(rs.getString("user_last_name"));
				user.setEmail(rs.getString("user_email"));
				user.setRole(rs.getInt("user_role_id"));
			}
			System.out.println("~~~~~~found " + user + " ~~~~~~");
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		if (user.getId() == null) {
			return null;
		}else {		
		return user;
		}
	}

}
