package com.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IMemberService {
	// String : ?��비스 처리 ?��?�� 보여�? jsp ?��?���?
	public String execute(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception;
}
