package com.revature.models;

import java.io.Serializable;

public class ReimbursementStatus implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String status;
	
	public ReimbursementStatus() {
		super();
	}
	public ReimbursementStatus(Integer id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
