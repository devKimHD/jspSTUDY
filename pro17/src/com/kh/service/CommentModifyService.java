package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;

public class CommentModifyService implements IBoardService{
	private BoardDao boardDao;
	public CommentModifyService(Connection conn) {
		boardDao=new BoardDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int c_no=Integer.parseInt(request.getParameter("c_no"));
		String c_content=request.getParameter("c_content");
		//System.out.println("CommentModifyService" +c_no+ "c_content "+c_content);
		boolean result=boardDao.updateComment(c_no, c_content);
		request.setAttribute("data", String.valueOf(result));
		return MyConstants.DATA_SEND;
	}

}
