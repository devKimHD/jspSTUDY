<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>listMembers.jsp</title>
<meta name="viewport" content="width=device-width, initial-scale=1">													
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">													
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>													
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>													
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>														
<script>
$(document).ready(function() {
	var join_result = "${sessionScope.join_result}";
	var modify_result = "${sessionScope.modify_result}";
	if (join_result == "true") {
		alert("회원 가입 완료");
	} else if (join_result == "false") {
		alert("회원 가입 오류");
	}
	if (modify_result == "true") {
		alert("회원 정보 수정 완료");
	} else if (modify_result == "false"){
		alert("회원 정보 수정 오류");
	}
	var isChecked = false;
	$("#chkSelect").click(function() {
		isChecked = !isChecked;
		console.log(isChecked);
		var el = $(".checkSel").attr("checked", isChecked);
	});
	
	
	
	
});
</script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<a href="/pro17/member/join_form"
				class="btn btn-success">회원가입</a>
		</div>
	</div>
	<div class="row">
		<div class="col-md-2">
		</div>
		<div class="col-md-8">
			<table class="table">
				<thead>
					<tr>
						<th><input type="checkbox" id="chkSelect"></th>
						<th>아이디</th>
						<th>비밀번호</th>
						<th>이름</th>
						<th>이메일</th>
						<th>가입일</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="vo" varStatus="status">
					<tr
						<c:if test="${status.index % 2 == 1}">
							class="table-active"
						</c:if>
					>
						<td><input type="checkbox"
								class="checkSel"></td>
						<td>${vo.id}</td>
						<td>${vo.pw}</td>
						<td>${vo.name}</td>
						<td>${vo.email}</td>
						<td>${vo.joindate}</td>
						<td><a href="/pro17/member/modify_form?id=${vo.id}" 
							class="btn btn-sm btn-warning">수정</a></td>
						<td><a href="/pro17/member/delete?id=${vo.id}" 
							class="btn btn-sm btn-danger">삭제</a></td>
					</tr>
				</c:forEach>	
				</tbody>
			</table>
		</div>
		<div class="col-md-2">
		</div>
	</div>
</div>
</body>
</html>
<%
	session.removeAttribute("join_result");
	session.removeAttribute("modify_result");
%>