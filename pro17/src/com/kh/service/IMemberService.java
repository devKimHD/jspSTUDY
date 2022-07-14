package com.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IMemberService {
	// String : ?„œë¹„ìŠ¤ ì²˜ë¦¬ ?›„?— ë³´ì—¬ì§? jsp ?˜?´ì§?
	public String execute(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception;
}
