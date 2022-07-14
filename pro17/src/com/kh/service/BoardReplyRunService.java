package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;
import com.kh.dao.MemberDao;
import com.kh.vo.BoardVo;
import com.kh.vo.MemberVo;

public class BoardReplyRunService implements IBoardService {
	
	private Connection conn ;
	private BoardDao boardDao ;
	private MemberDao memberDao;
	public BoardReplyRunService(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn=conn;
		boardDao=new BoardDao(conn);
		memberDao= new MemberDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		MemberVo memberVo=(MemberVo)session.getAttribute("memberVo");
		int g_no =Integer.parseInt(request.getParameter("g_no"));
		int re_seq =Integer.parseInt(request.getParameter("re_seq"));
		int re_level =Integer.parseInt(request.getParameter("re_level"));
		String b_title=request.getParameter("b_title");
		String b_content=request.getParameter("b_content");
		String id=memberVo.getId();
		
		conn.setAutoCommit(false);
		BoardVo vo= new BoardVo();
		vo.setG_no(g_no);
		vo.setB_title(b_title);
		vo.setB_content(b_content);
		vo.setId(id);
		vo.setRe_seq(re_seq);
		vo.setRe_level(re_level);
		boolean result1=boardDao.insertReply(vo);
		boolean result2=memberDao.updatePoint(id, 5);
		if(result1 && result2 ) {
			conn.commit();
			memberVo.setPoint(memberVo.getPoint()+5);
			session.removeAttribute("memberVo");
			session.setAttribute("memberVo", memberVo);
		}
		else {
			conn.rollback();
		}
		conn.setAutoCommit(true);
		//ConnectionManager.close(conn);
		return MyConstants.REDIRECT+"/pro17/board/list";
	}

}
