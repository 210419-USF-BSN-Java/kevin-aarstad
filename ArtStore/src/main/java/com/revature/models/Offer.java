package com.revature.models;

public class Offer{

	private Integer id;
	private Integer value;
	private Customer customer;
	private Item item;
	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Offer(Integer id, Integer value, Customer customer, Item item) {
		super();
		this.id = id;
		this.value = value;
		this.customer = customer;
		this.item = item;
	}
	public Offer(Integer value, Customer customer, Item item) {
		super();
		this.value = value;
		this.customer = customer;
		this.item = item;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
}
