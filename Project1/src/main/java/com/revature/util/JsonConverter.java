package com.revature.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.models.Reimbursement;
import com.revature.models.User;


public class JsonConverter {

	private final Gson gson;
	
	public JsonConverter() {
		gson = new GsonBuilder().create();
	}
	public String convertToJson(User user) {
		return gson.toJson(user);
	}
	public String convertToJson(Reimbursement reimbursement) {
		return gson.toJson(reimbursement);
	}
	public String convertToJson(List<Reimbursement> list) {
		return gson.toJson(list);
	}
	public String convertUsersToJson(List<User> userList) {
		return gson.toJson(userList);
	}
}
