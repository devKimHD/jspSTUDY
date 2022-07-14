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

import com.kh.dao.ConnectionManager;
import com.kh.service.DeleteSelectedService;
import com.kh.service.DeleteService;
import com.kh.service.IMemberService;
import com.kh.service.MemberChkDupIdService;
import com.kh.service.MemberJoinFormService;
import com.kh.service.MemberJoinRunService;
import com.kh.service.MemberListService;
import com.kh.service.MemberLoginFormService;
import com.kh.service.MemberLoginRunService;
import com.kh.service.MemberLogoutRunService;
import com.kh.service.MemberPointListService;
import com.kh.service.ModifyFormService;
import com.kh.service.ModifyRunService;



@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private MemberDao dao = MemberDao.getInstance();
	private IMemberService service;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		List<MemberVo> list = dao.listMembers();
//		request.setAttribute("list", list);
		String command=request.getPathInfo();
		Connection conn=null;
		try {
			conn=ConnectionManager.getConnection();
			switch (command) {
			case "/list": // 紐⑸줉
				service = new MemberListService(conn);
				break;
			case "/join_form": // 媛��엯 �뼇�떇
				service = new MemberJoinFormService();
				break;
			case "/join_run": // 媛��엯 泥섎━
				service = new MemberJoinRunService();
				break;
			case "/modify_form": // �닔�젙 �뼇�떇
				service = new ModifyFormService();
				break;
			case "/modify_run": // �닔�젙 泥섎━
				service = new ModifyRunService();
				break;
			case "/delete": // �궘�젣 泥섎━
				service = new DeleteService();
				break;
			case "/delete_selected":
				service = new DeleteSelectedService();
				break;
			//로그인에 대한 요청
			case "/login_form":
				service= new MemberLoginFormService();
				break;
			case "/login_run":
				service= new MemberLoginRunService();
				break;
			case "/logout_run":
				service=new MemberLogoutRunService();
				break;
			case "/point_list" :
			service=new MemberPointListService(conn);
			break;
			case "/chk_dupId":
				service=new MemberChkDupIdService(conn);
				break;
			}
		}
		catch(Exception e) {
			System.out.println("커넥션 에러");
			e.printStackTrace();
		}
		
		
		try {
			String page = service.execute(request, response);
			System.out.println("page:" + page);
			// redirect: �쑝濡� �떆�옉�븯硫� redirect
			if (page.startsWith(MyConstants.REDIRECT)) {
				page = page.substring(MyConstants.REDIRECT.length());
				// -> redirect:/pro17/member/list -> /pro17/member/list
				response.sendRedirect(page);
			} 
			else if(page.equals(MyConstants.DATA_SEND)) {
				response.setCharacterEncoding("utf-8");
				Object obj=request.getAttribute("data");
				PrintWriter writer =response.getWriter();
				writer.print(obj.toString());
			}
			// 洹몃젃吏� �븡�쑝硫� forward
			else {
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher(MyConstants.PREFIX + page + MyConstants.POSTFIX);
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("커넥션닫는다");
			ConnectionManager.close(conn);
		}
		
	}
	
	//private String getCommand(HttpServletRequest request) {
////		String uri = request.getRequestURI();
////		System.out.println("uri:" + uri); // /pro17/member/list
////		int slashIndex = uri.lastIndexOf("/");
////		System.out.println("slashIndex:" + slashIndex); // 13
////		String command = uri.substring(slashIndex + 1);
////		System.out.println("command:" + command);
//		String command=request.getPathInfo();
//		return command;
	//}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
