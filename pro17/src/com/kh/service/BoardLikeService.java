package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;
import com.kh.dao.LikeDao;
import com.kh.vo.MemberVo;

public class BoardLikeService implements IBoardService {
	private LikeDao likeDao;
	private BoardDao boardDao;
	private Connection conn;

	public BoardLikeService(Connection conn) {
		this.conn = conn;
		boardDao = new BoardDao(conn);
		likeDao = new LikeDao(conn);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int b_no = Integer.parseInt(request.getParameter("b_no"));
		HttpSession session = request.getSession();
		MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
		String id = memberVo.getId();
		//System.out.println("likeservice :"+ b_no+","+id);
		conn.setAutoCommit(false);
		boolean like_result = false;
		boolean board_result = false;
		try {
			boolean isLike = likeDao.isLike(b_no, id);
			if (isLike) {
				like_result = likeDao.deleteLike(b_no, id);
				board_result = boardDao.updateLikeCount(b_no, -1);
			} else {
				like_result = likeDao.insertLike(b_no, id);
				board_result = boardDao.updateLikeCount(b_no, 1);
			}
			if(like_result && board_result) {
				conn.commit();
			}
			else {
				conn.rollback();
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.setAutoCommit(true);
		}
		request.setAttribute("data", "success");
		return MyConstants.DATA_SEND;
	}

}
