package com.kh.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.controller.MyConstants;



public class MemberLogoutRunService implements IMemberService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		return MyConstants.REDIRECT+"/pro17/member/login_form";
	}

}
