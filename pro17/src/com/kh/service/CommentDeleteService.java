package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;

public class CommentDeleteService implements IBoardService {
	private BoardDao boardDao;
	public CommentDeleteService(Connection conn) {
		boardDao=new BoardDao(conn);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int c_id=Integer.parseInt(request.getParameter("c_id"));
		boolean result=boardDao.deleteComment(c_id);
		request.setAttribute("data", String.valueOf(result));
		return MyConstants.DATA_SEND;
	}

}
