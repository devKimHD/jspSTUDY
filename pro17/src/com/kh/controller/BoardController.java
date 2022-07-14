package com.kh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.dao.ConnectionManager;
import com.kh.service.BoardContentService;
import com.kh.service.BoardDeleteRunService;
import com.kh.service.BoardInsertTestData;
import com.kh.service.BoardIsLikeService;
import com.kh.service.BoardLikeService;
import com.kh.service.BoardListService;
import com.kh.service.BoardModifyRunService;
import com.kh.service.BoardReplyFormService;
import com.kh.service.BoardReplyRunService;
import com.kh.service.BoardWriteFormService;
import com.kh.service.BoardWriteRunService;
import com.kh.service.CommentDeleteService;
import com.kh.service.CommentListService;
import com.kh.service.CommentModifyService;
import com.kh.service.CommentWriteService;
import com.kh.service.IBoardService;



@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IBoardService service;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getPathInfo();
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
			
			System.out.println("control" + path);
			switch (path) {
			case "/list":
				service = new BoardListService(conn);

				break;
			case "/content":
				service = new BoardContentService(conn);
				break;
			case "/write_form":
				service = new BoardWriteFormService();
				break;
			case "/write_run":
				service = new BoardWriteRunService(conn);
				break;
			case "/modify_run":
				service = new BoardModifyRunService(conn);
				break;
			case "/delete_run":
				service = new BoardDeleteRunService(conn);
				break;
			case "/reply_form":
				service = new BoardReplyFormService();
				break;
			case "/reply_run":
				service = new BoardReplyRunService(conn);
				break;
			case "/insert_sample_data":
				service = new BoardInsertTestData(conn);
				break;
			case "/like" :
				service= new BoardLikeService(conn);	
				break;
			case "/is_like" :
				service=new BoardIsLikeService(conn);
				break;
			case "/commentWrite":
				service=new CommentWriteService(conn);
				break;
			case "/commentList":
				service=new CommentListService(conn);
				break;
			case "/commentModify":
				service=new CommentModifyService(conn);
				break;
			case "/commentDelete":
				service=new CommentDeleteService(conn);
				break;
			}

			String page = service.execute(request, response);

			if (page.startsWith(MyConstants.REDIRECT)) {
				response.sendRedirect(page.substring(MyConstants.REDIRECT.length()));

			} else if(page.equals(MyConstants.DATA_SEND)) {
				response.setCharacterEncoding("utf-8");
				PrintWriter writer=response.getWriter();
				Object obj=request.getAttribute("data");
				writer.print(obj.toString());
			}
			
			else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(MyConstants.PREFIX + page + MyConstants.POSTFIX);
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionManager.close(conn);
		}
		// 마지막 방문 페이지를 세션에 저장
		session.setAttribute("lastPath", path);
		System.out.println("control last" + path);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
