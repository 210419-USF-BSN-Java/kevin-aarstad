package com.revature.models;

public class Employee{

	private Integer id;
	private String name;
	
	public Employee() {
		super();
	}
	
	public Employee(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	
	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
