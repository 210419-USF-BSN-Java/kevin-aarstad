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
import com.revature.models.Employee;

public class EmployeePostgresql implements EmployeeDao{	
	private static Logger log = (Logger) LogManager.getRootLogger();

	@Override
	public Employee add(Employee t) throws SQLException, IOException {
		Employee employee = null;
		String sql = "insert into employees (emp_name) values (?) returning emp_id;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				employee = t;
				employee.setId(rs.getInt(1));
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}
		
		return null;
	}

	@Override
	public Employee getById(Integer id) {
		Employee employee = new Employee();
		String sql = "select * from employees where emp_id = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				employee.setId(id);
				employee.setName(rs.getString("emp_name"));				
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return null;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<>();
		String sql = "select * from employees;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int emp_id = rs.getInt("emp_id");
				String emp_name = rs.getString("emp_name");
				
				employees.add(new Employee(emp_id, emp_name));
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return null;
	}

	@Override
	public Integer update(Employee t) {
		String sql = "update employees set emp_name = ? where emp_id = ?;";
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
	public Integer delete(Employee t) {
		String sql = "delete from employees where emp_id = ?;";
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
	public Employee getByName(String name) {
		Employee employee = new Employee();
		String sql = "select * from employees where emp_name = ?;";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				employee.setId(rs.getInt("emp_id"));
				employee.setName(name);				
			}
		} catch (SQLException | IOException e) {
			errorLog(e.getMessage());
		}		
		return employee;
	}

	public static void errorLog(String s) { // error outputs string 's'
		((org.apache.logging.log4j.Logger) log).error(s);
		log.info("");
	}	
}
