package com.kh.service;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.BoardDao;
import com.kh.vo.BoardVo;
import com.kh.vo.PagingDto;

public class BoardListService implements IBoardService {
	private Connection conn;
	private BoardDao boardDao;
	public BoardListService(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn=conn;
		boardDao=new BoardDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PagingDto pagingDto = new PagingDto();
		String strPerPage=request.getParameter("perPage");
		if(strPerPage !=null && !"".equals(strPerPage)) {
			pagingDto.setPerPage(Integer.parseInt(strPerPage));
		}
		String searchType=request.getParameter("searchType");
		String keyword=request.getParameter("keyword");
		
		pagingDto.setSearchType(searchType);
		pagingDto.setKeyword(keyword);
		//처음방문시 page값이 없기에 미리 1을저장
		int page=1;
		String strPage=request.getParameter("page");
		if(strPage!=null && !strPage.equals("")) {
			page=Integer.parseInt(strPage);
		}
		pagingDto.setPage(page,boardDao.getCount( searchType , keyword));
		System.out.println("pagingdto :"+pagingDto);
		List<BoardVo> list=boardDao.getList(pagingDto);
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("pagingDto", pagingDto);
		//ConnectionManager.close(conn);
		return "board/list";
	}

}
