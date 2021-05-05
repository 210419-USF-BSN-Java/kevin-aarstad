package com.revature.models;

public class Payment{
	
	private Integer id;
	private Integer value;
	private Integer remaining;
	private Customer customer;
	
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Payment(Integer id, Integer value, Integer remaining, Customer customer) {
		super();
		this.id = id;
		this.value = value;
		this.remaining = remaining;
		this.customer = customer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getRemaining() {
		return remaining;
	}
	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
