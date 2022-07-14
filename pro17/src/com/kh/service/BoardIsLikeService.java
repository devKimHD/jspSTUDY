package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.controller.MyConstants;
import com.kh.dao.LikeDao;
import com.kh.vo.MemberVo;

public class BoardIsLikeService implements IBoardService {
	private LikeDao likeDao;
	public BoardIsLikeService(Connection conn) {
		likeDao=new LikeDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int b_no= Integer.parseInt(request.getParameter("b_no"));
		HttpSession session=request.getSession();
		MemberVo memberVo=(MemberVo)session.getAttribute("memberVo");
		String id=memberVo.getId();
		boolean result=likeDao.isLike(b_no, id);
		request.setAttribute("data", result);
		
		return MyConstants.DATA_SEND;
	}

}
