package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.ConnectionManager;
import com.kh.dao.MemberDao;
import com.kh.vo.MemberVo;

public class MemberJoinRunService implements IMemberService {
	private Connection conn=ConnectionManager.getConnection();
	private MemberDao dao=new MemberDao(conn);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
//		request.getRequestURL();
//		request.getServerPort();
//		request.getQueryString();
//		request.getServerName();
		MemberVo vo = new MemberVo(id, pwd, name, email);
		boolean result = dao.addMember(vo);
		request.getSession().setAttribute("join_result", result);
		// �쉶�썝 媛��엯 泥섎━ �썑�뿉 紐⑸줉�쑝濡� �씠�룞
		conn.close();
		return "redirect:/pro17/member/list"; // forward(x), redirect(o)
	}

}
