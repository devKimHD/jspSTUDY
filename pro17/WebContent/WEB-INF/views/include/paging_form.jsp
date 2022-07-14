<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<form id="frmPaging" action="/pro17/board/list" method="get">
		<input type="hidden" name="b_no" value="${param.b_no }">
		<input type="hidden" name="page" value="${param.page }"> 
		<input type="hidden" name="perPage" value="${param.perPage }">
		<input type="hidden"
			name="searchType" value="${param.searchType }"> 
			<input type="hidden" name="keyword" value="${param.keyword }">
			
	</form>