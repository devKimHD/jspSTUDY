package com.kh.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;
import com.kh.vo.BoardVo;

public class BoardInsertTestData implements IBoardService {
	private Connection conn;
	private BoardDao boardDao;
	public BoardInsertTestData(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn=conn;
		boardDao= new BoardDao(conn);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			conn.setAutoCommit(false);

			for (int i = 1; i <= 500; i++) {
				BoardVo vo = new BoardVo();
				vo.setB_title("����-" + i);
				vo.setB_content("����-" + i);
				vo.setId("lee");
				boardDao.insertArticle(vo);
			}
			conn.commit();		
			System.out.println("������ �Է¿Ϸ�");
		} catch (Exception e) {
			System.out.println("������ �Է¿���");
			e.printStackTrace();
			conn.rollback();
			
		}
		conn.setAutoCommit(true);
		//ConnectionManager.close(conn);
		return MyConstants.REDIRECT+"/pro17/board/list";
	}

}
