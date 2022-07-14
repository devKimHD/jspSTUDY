package com.kh.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kh.controller.MyConstants;
import com.kh.dao.BoardDao;
import com.kh.vo.CommentVo;

public class CommentListService implements IBoardService {
	private BoardDao boardDao;
	public CommentListService(Connection conn) {
		boardDao=new BoardDao(conn);
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int b_no=Integer.parseInt(request.getParameter("b_no"));
		List<CommentVo> commentList=boardDao.getCommentList(b_no);
		
		JSONArray jArray = new JSONArray();
		for(CommentVo commentVo: commentList) {
			JSONObject jObject =new JSONObject();
			jObject.put("c_id", commentVo.getC_id());
			jObject.put("c_content", commentVo.getC_content());
			jObject.put("id", commentVo.getId());
			jObject.put("b_no", commentVo.getB_no());
			jObject.put("c_date", commentVo.getC_date().toString());
			jArray.add(jObject);
		}
		request.setAttribute("data",jArray.toJSONString());
		return MyConstants.DATA_SEND;
	}

}
