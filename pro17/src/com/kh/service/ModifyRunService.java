package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.dao.ConnectionManager;
import com.kh.dao.MemberDao;
import com.kh.vo.MemberVo;

public class ModifyRunService implements IMemberService {
	private Connection conn=ConnectionManager.getConnection();
	private MemberDao dao=new MemberDao(conn);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		MemberVo vo = new MemberVo(id, pwd, name, email);
		boolean result = dao.updateMember(vo);
		HttpSession session = request.getSession();
		session.setAttribute("modify_result", result);
		conn.close();
		return "redirect:/pro17/member/list";
	}

}
