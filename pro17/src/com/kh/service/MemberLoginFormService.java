package com.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberLoginFormService implements IMemberService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "member/login_form";
	}

}
