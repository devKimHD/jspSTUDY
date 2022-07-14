package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;
import com.kh.dao.MemberDao;
import com.kh.dao.PointDao;
import com.kh.vo.BoardVo;
import com.kh.vo.MemberVo;
import com.kh.vo.PointVo;

public class BoardWriteRunService implements IBoardService {
	private Connection conn ;
	private BoardDao boardDao ;
	private MemberDao memberDao;
	private PointDao pointDao;
	public BoardWriteRunService(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn=conn;
		boardDao=new BoardDao(conn);
		memberDao= new MemberDao(conn);
		pointDao=new PointDao(conn);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String b_title = request.getParameter("b_title");
		String b_content = request.getParameter("b_content");
		//String id = request.getParameter("id");
		// System.out.println("id:"+id+","+"c: "+b_content+"t: "+b_title+"where
		// boardWriterun");
		HttpSession session=request.getSession();
		MemberVo memberVo=(MemberVo)session.getAttribute("memberVo");
		String id=memberVo.getId();
		BoardVo vo = new BoardVo(b_title, b_content, null,id);

		// System.out.println(conn+"\t writerun excute()");
		// 자동 커밋 방지
		conn.setAutoCommit(false);

		boolean insert_result = boardDao.insertArticle(vo);
		PointVo pointVo=new PointVo();
		pointVo.setPoint_code(MyConstants.POINT_CODE_WRITE_BOARD);
		pointVo.setId(id);
		boolean insert_point_result=pointDao.addPoint(pointVo);
		
		boolean update_result = memberDao.updatePoint(id, pointDao.getPointByPointCode(MyConstants.POINT_CODE_WRITE_BOARD));
		boolean result = false;
		if (insert_result  && update_result  && insert_point_result) {
			result = true;
			conn.commit();
			memberVo.setPoint(memberVo.getPoint()+pointDao.getPointByPointCode(MyConstants.POINT_CODE_WRITE_BOARD));
			session.removeAttribute("memberVo");
			session.setAttribute("memberVo", memberVo);
			
		} else {
			conn.rollback();
		}
		conn.setAutoCommit(true);
		//ConnectionManager.close(conn);
		request.getSession().setAttribute("insert_result", result);
		return MyConstants.REDIRECT + "/pro17/board/list";
		// -> redircet:/pro17/board/list 시작위치가 리다이렉트 길이부터 끝까지

	}

}
