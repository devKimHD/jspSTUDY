package com.kh.service;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.MemberDao;
import com.kh.vo.MemberVo;

public class MemberListService implements IMemberService {
	private Connection conn;
	private MemberDao dao;
	public MemberListService(Connection conn) {
		this.conn=conn;
		this.dao=new MemberDao(conn);
	}
	// IMemberService �쓽 異붿긽 硫붿꽌�뱶
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MemberVo> list = dao.listMembers();
		request.setAttribute("list", list);
		//conn.close();
		return "member/listMembers";
	}

}
