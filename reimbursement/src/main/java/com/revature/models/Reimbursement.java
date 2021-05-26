package com.revature.models;

import java.io.Serializable;

import java.util.Date;

public class Reimbursement implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Double amount;
	private Date submitted;
	private Date resolved;
	private String description;
	private Integer author;
	private Integer resolver;
	private Integer status;
	private Integer type;
	
	public Reimbursement() {
		super();
	}
	public Reimbursement(Integer id, Double amount, Date submitted, Date resolved, String description, 
			Integer author, Integer resolver, Integer status, Integer type) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Date date) {
		this.submitted = date;
	}
	public Date getResolved() {
		return resolved;
	}
	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAuthor() {
		return author;
	}
	public void setAuthor(Integer author) {
		this.author = author;
	}
	public Integer getResolver() {
		return resolver;
	}
	public void setResolver(Integer resolver) {
		this.resolver = resolver;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
