package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;
import com.kh.vo.BoardVo;
import com.kh.vo.MemberVo;

public class BoardModifyRunService implements IBoardService {
	private Connection conn;
	private BoardDao boardDao;
	public BoardModifyRunService(Connection conn ) {
		// TODO Auto-generated constructor stub
		this.conn=conn;
		boardDao=new BoardDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int b_no=Integer.parseInt(request.getParameter("b_no"));
		String page=request.getParameter("page");
		String b_title=request.getParameter("b_title");
		String b_content=request.getParameter("b_content");
		String id=request.getParameter("id");
		
		
		HttpSession session=request.getSession();
		MemberVo memberVo=(MemberVo)session.getAttribute("memberVo");
//		System.out.println("modifyrun id"+id);
//		System.out.println("modifyrun memberid"+memberVo.getId());
		BoardVo boardVo =new BoardVo();
		boardVo.setB_no(b_no);
		boardVo.setB_title(b_title);
		boardVo.setB_content(b_content);
		if(id.equals(memberVo.getId())) {
			boolean result=boardDao.updateArticle(boardVo);
			request.getSession().setAttribute("update_result", result);
			//ConnectionManager.close(conn);
			return MyConstants.REDIRECT+"/pro17/board/content?b_no="+b_no+"&page="+page;
		}
		else {
			return MyConstants.REDIRECT+"/pro17/board/list";
		}
		
		
	}

}
