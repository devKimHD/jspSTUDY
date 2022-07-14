package com.kh.service;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.dao.PointDao;
import com.kh.vo.MemberVo;
import com.kh.vo.PointVo;

public class MemberPointListService implements IMemberService {
	private PointDao pointDao;
	public MemberPointListService(Connection conn) {
		// TODO Auto-generated constructor stub
		pointDao=new PointDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberVo memberVo=(MemberVo)request.getSession().getAttribute("memberVo"); 
		
		List<PointVo> pointList=pointDao.getPointList(memberVo.getId());
		request.setAttribute("pointList", pointList);
		return "member/point_list";
	}

}
