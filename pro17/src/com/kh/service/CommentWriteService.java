package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;
import com.kh.vo.CommentVo;
import com.kh.vo.MemberVo;

public class CommentWriteService implements IBoardService {
	private BoardDao boardDao;
	public CommentWriteService(Connection conn) {
		boardDao=new BoardDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String c_content=request.getParameter("c_content");
		int b_no=Integer.parseInt(request.getParameter("b_no"));
		HttpSession session=request.getSession();
		MemberVo memberVo=(MemberVo)session.getAttribute("memberVo");
		String id=memberVo.getId();
		System.out.println("CommentWriteService: "+c_content+", bno "+b_no+", id "+id);
		CommentVo commentVo=new CommentVo(c_content, id, b_no);
		boolean result=boardDao.commentWrite(commentVo);
		request.setAttribute("data", String.valueOf(result));
		return MyConstants.DATA_SEND;
	}

}
