package com.kh.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IMemberService {
	// String : ?λΉμ€ μ²λ¦¬ ?? λ³΄μ¬μ§? jsp ??΄μ§?
	public String execute(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception;
}
