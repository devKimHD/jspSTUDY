<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="/WEB-INF/views/include/bootstrap_cdn.jsp"%>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<h2>포인트 내역</h2>

					<p>
						<a class="btn btn-primary btn-large" href="/pro17/board/list">목록으로</a>
					</p>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th>항목</th>
							<th>포인트</th>
							<th>날짜</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${pointList }" var="pointVo" varStatus="status">
						<tr>
							<td>${status.count }</td>
							<td>${pointVo.point_name }</td>
							<td>${pointVo.point }</td>
							<td>
							<fmt:formatDate value="${pointVo.point_date }"
							pattern="yyyy-MM-dd HH:mm:ss"
							/>
							</td>
						</tr>
					</c:forEach>	
					</tbody>
				</table>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
</body>
</html>