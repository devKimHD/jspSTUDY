package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.controller.MyConstants;
import com.kh.dao.ConnectionManager;
import com.kh.dao.MemberDao;
import com.kh.vo.MemberVo;

public class MemberLoginRunService implements IMemberService {
	Connection conn = ConnectionManager.getConnection();
	MemberDao memberDao = new MemberDao(conn);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String saveId = request.getParameter("saveId");
		// chek박스에 체크하면 벨류값이 넘어옴
	System.out.println("loginrun:"+saveId);

		MemberVo memberVo = memberDao.selectMemberByIdAndPwd(id, pwd);
		conn.close();
		String page = "";
		if (memberVo == null) {// login fail
			page = MyConstants.REDIRECT + "/pro17/member/login_form";
		} else {// login success
			page = MyConstants.REDIRECT + "/pro17/board/list";
			request.getSession().setAttribute("memberVo", memberVo);
			Cookie loginCookie = new Cookie("login_id", id);
			loginCookie.setPath("/pro17");
			if (saveId==null ) {
				System.out.println("세이브 아이디 널");
				loginCookie.setMaxAge(0);
				
			} else {
				
				loginCookie.setMaxAge(60 * 60 * 24 * 7);
			}
			response.addCookie(loginCookie);
		}
		return page;
	}
}
