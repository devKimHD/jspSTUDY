package com.kh.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter({"/board/*" , "/member/point_list"})
public class MemberFilter implements Filter {

    public MemberFilter() {
        // TODO Auto-generated constructor stub
    }

	
	public void destroy() {
		// TODO Auto-generated method stub
	}



	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		//회원제 게시판 할거라서 로그인 되있는경우에만 사용가능하게
		//memberVo 로그인 성공시 세션 영역에 넣었던 데이터
		Object obj=((HttpServletRequest)request).getSession().getAttribute("memberVo");
		System.out.println("filter obj"+obj);
		if(obj ==null) {
			((HttpServletResponse)response).sendRedirect("/pro17/member/login_form");
		}
		
		// pass the request along the filter chain
		//로그인 되어 있지 않다면, 로그인 폼으로 리다이렉트
		//로그인이 있다면 원래가고자 한곳으로 해당요청 처리
		else {
			chain.doFilter(request, response);
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
