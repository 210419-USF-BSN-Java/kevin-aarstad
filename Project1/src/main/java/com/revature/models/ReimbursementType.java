package com.revature.models;

import java.io.Serializable;

public class ReimbursementType implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String type;
	
	
	public ReimbursementType() {
		super();
	}
	public ReimbursementType(Integer id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
