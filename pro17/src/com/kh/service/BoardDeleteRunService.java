package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;
import com.kh.dao.MemberDao;
import com.kh.vo.MemberVo;



public class BoardDeleteRunService implements IBoardService {
	private Connection conn;
	private BoardDao boardDao;
	private MemberDao memberDao;

	public BoardDeleteRunService(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
		boardDao = new BoardDao(conn);
		memberDao = new MemberDao(conn);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		int b_no = Integer.parseInt(request.getParameter("b_no"));
		HttpSession session = request.getSession();
		MemberVo memberVo = (MemberVo) session.getAttribute("memberVo");
		String id = request.getParameter("id");
		String memberId = memberVo.getId();
		conn.setAutoCommit(false);
		boolean result = false;

		if (id.equals(memberId)) {
			boolean delete_result = boardDao.deleteArticle(b_no);
			boolean update_result = memberDao.updatePoint(memberId, -5);
			if (delete_result && update_result) {
				result = true;
				conn.commit();
				memberVo.setPoint(memberVo.getPoint() - 5);
				session.setAttribute("memberVo", memberVo);
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(true);
			request.getSession().setAttribute("delete_result", result);

			// ConnectionManager.close(conn);
			return MyConstants.REDIRECT + "/pro17/board/list?page=" + page;
		} // out if
		else {
			return MyConstants.REDIRECT + "/pro17/board/list";
		}

	}

}
