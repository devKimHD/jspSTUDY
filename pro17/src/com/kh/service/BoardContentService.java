package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.BoardDao;
import com.kh.vo.BoardVo;



public class BoardContentService implements IBoardService {
	private Connection conn;
	private BoardDao boardDao ;
	public BoardContentService(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn=conn;
		boardDao= new BoardDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int b_no = Integer.parseInt(request.getParameter("b_no"));
		String lastPath=(String)request.getSession().getAttribute("lastPath");
		String path=request.getPathInfo();
		System.out.println("boardConService exe lastPath"+lastPath);
		System.out.println("boardConService exe Path"+path);
		if(lastPath ==null || (lastPath!=null && !lastPath.equals(path))) {
			boardDao.increaseReadCount(b_no);
		} 
		BoardVo boardVo = boardDao.getArticleByBno(b_no);
		
		request.setAttribute("boardVo", boardVo);
		
		//ConnectionManager.close(conn);
		return "board/content";
	}

}
