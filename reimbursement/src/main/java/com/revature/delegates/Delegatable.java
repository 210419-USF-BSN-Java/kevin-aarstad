package com.revature.delegates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Delegatable {
	void process(HttpServletRequest request, HttpServletResponse response);
}
