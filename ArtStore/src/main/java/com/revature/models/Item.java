package com.revature.models;

public class Item{

	private Integer id;
	private String name;
	private Boolean owned;
	private Customer customer;
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(Integer id, String name, Boolean owned, Customer customer) {
		super();
		this.id = id;
		this.name = name;
		this.owned = owned;
		this.setCustomer(customer);
	}
	
	public Item (String name) {
		this.name = name;
		this.owned = false;
		this.customer = null;
	}

	public Integer getId() {
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

	public Boolean getOwned() {
		return owned;
	}

	public void setOwned(Boolean owned) {
		this.owned = owned;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
		
}
