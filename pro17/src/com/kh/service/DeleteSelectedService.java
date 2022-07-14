package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.ConnectionManager;
import com.kh.dao.MemberDao;

public class DeleteSelectedService implements IMemberService {
	private Connection conn=ConnectionManager.getConnection();
	private MemberDao dao=new MemberDao(conn);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] delIds = request.getParameterValues("delId");
		for (String id : delIds) {
			System.out.println(id);
		}
		dao.delMember(delIds);
		conn.close();
		return "redirect:/pro17/member/list";
	}

}
