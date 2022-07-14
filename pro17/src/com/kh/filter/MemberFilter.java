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
		//ȸ���� �Խ��� �ҰŶ� �α��� ���ִ°�쿡�� ��밡���ϰ�
		//memberVo �α��� ������ ���� ������ �־��� ������
		Object obj=((HttpServletRequest)request).getSession().getAttribute("memberVo");
		System.out.println("filter obj"+obj);
		if(obj ==null) {
			((HttpServletResponse)response).sendRedirect("/pro17/member/login_form");
		}
		
		// pass the request along the filter chain
		//�α��� �Ǿ� ���� �ʴٸ�, �α��� ������ �����̷�Ʈ
		//�α����� �ִٸ� ���������� �Ѱ����� �ش��û ó��
		else {
			chain.doFilter(request, response);
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
