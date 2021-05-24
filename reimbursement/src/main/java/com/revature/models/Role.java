package com.revature.models;

import java.io.Serializable;

public class Role implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String roleName;
	
	
	public Role() {
		super();
	}
	public Role(Integer id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
