package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.controller.MyConstants;
import com.kh.dao.MemberDao;

public class MemberChkDupIdService implements IMemberService{
	private MemberDao memberDao;
	public MemberChkDupIdService(Connection conn) {
		memberDao=new MemberDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		boolean result=memberDao.isDupId(id);
		if(result) {
			request.setAttribute("data", "dupId");
		}
		else {
			request.setAttribute("data", "usableId");
		}
		return MyConstants.DATA_SEND;
	}

}
