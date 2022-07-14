package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.ConnectionManager;
import com.kh.dao.MemberDao;
import com.kh.vo.MemberVo;

public class ModifyFormService implements IMemberService {
	private Connection conn=ConnectionManager.getConnection();
	private MemberDao dao=new MemberDao(conn);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		MemberVo vo = dao.selectMemberById(id);
		request.setAttribute("vo", vo);
		conn.close();
		return "member/memberModifyForm";
	}

}
