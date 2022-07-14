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
<style>
.highlight{
	color:red;
	font-weight:bold;
}
</style>
<script>
	$(function() {
		$("#spanTitle");
			
		var insert_result = "${sessionScope.insert_result}";
		var delete_result = "${sessionScope.delete_result}";

		if (insert_result == "true") {
			alert("글 등록 완료");
		} else if (insert_result == "false") {
			alert("글 등록 오류");
		}

		if (delete_result == "true") {
			alert("글 삭제 완료");
		} else if (delete_result == "false") {
			alert("글 삭제 오류");
		}
		$("a.page-link").click(function(e) {
			e.preventDefault(); //a태그 이동막기
			var href=$(this).attr("href");
			$("#frmPaging > input[name=page]").val(href);
			$("#frmPaging").submit();
		});
		//n줄씩 보기
		$("select[name=perPage]").change(function(){
			var val=$(this).val()
			$("#frmPaging > input[name=perPage]").val(val);
			$("#frmPaging").submit();
		});
		$("a.a_content").click(function(e){
			e.preventDefault();
			var b_no=$(this).attr("href");
			$("#frmPaging > input[name=b_no]").val(b_no);
			$("#frmPaging").attr("action","/pro17/board/content");
			$("#frmPaging").submit();
		});
	});
</script>
<%
	session.removeAttribute("insert_result");
	session.removeAttribute("delete_result");
%>
</head>
<body>
	<!-- 폼을 get 방식으로 전송 -->
<%@ include file="/WEB-INF/views/include/paging_form.jsp" %>
	<div class="container-fluid">

		<div class="col-md-2"></div>
		<div class="row">

			<div class="col-md-12">
				<div class="jumbotron">
					<h2>글목록</h2>
					<p>
						<a class="btn btn-primary btn-large"
							href="/pro17/board/write_form">글쓰기</a>
					</p>
					<c:if test="${not empty sessionScope.memberVo.id }" >
					<h3>${sessionScope.memberVo.id }
					(${sessionScope.memberVo.name})
					님 반갑습니다.(현재 포인트:<a href="/pro17/member/point_list">${sessionScope.memberVo.point }</a>점)
					<a class="btn btn-sm btn-danger" href="/pro17/member/logout_run">로그아웃</a>
					</h3>
					</c:if>
				</div>
				<form action="/pro17/board/list" method="get">
					<div class="row">
						<div class="col-md-2"></div>
						<div class="col-md-8">
							<select name="searchType">
								<option value="">선택</option>
								<option value="">-------------------</option>
								<option value="t"
									<c:if test="${param.searchType == 't' }"> selected</c:if>>제목</option>
								<option value="c"
									<c:if test="${param.searchType == 'c' }"> selected</c:if>>내용</option>
								<option value="w"
									<c:if test="${param.searchType == 'w' }"> selected</c:if>>작성자</option>
								<option value="tc"
									<c:if test="${param.searchType == 'tc' }"> selected</c:if>>제목+내용</option>
								<option value="tcw"
									<c:if test="${param.searchType == 'tcw' }"> selected</c:if>>제목+내용+작성자</option>
							</select> <input type="text" name="keyword" value="${param.keyword }">
							<select name="perPage"
			>
			<c:forEach var="v" begin="5" end="30" step="5">
			<option value="${v }"
			<c:choose>
			<c:when test="${not empty param.perPage && param.perPage==v }">selected</c:when>
			<c:when test="${empty param.perPage && v==10 }">selected</c:when>
			
			</c:choose> 
			>${v}줄씩 보기
			</option>
			</c:forEach>
			</select>
							<button type="submit" class="btn btn-sn btn-success">검색</button>
						</div>
					</div>
				</form>
				<table class="table">
					<thead>
						<tr>
							<th>글번호</th>
							<th>글제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="boardVo">
							<tr>
								<td>${boardVo.b_no}</td>
								<td style="padding-left: ${boardVo.re_level *40}px" id="high"><c:if
										test="${boardVo.re_level>0 }">ㄴ</c:if> <a class="a_content"
									href="${boardVo.b_no}"><span id="spanTitle">${boardVo.b_title}</span></a>
									<c:if test="${boardVo.read_count > 5 }">
										<img src="/pro17/images/hot.png" width="30">
									</c:if></td>
								<td>${boardVo.id}</td>
								<td><fmt:formatDate value="${boardVo.b_date }"
										pattern="YYYY-MM-dd HH:mm:ss" /></td>
								<td>${boardVo.read_count}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<nav>
					<ul class="pagination justify-content-center">
						<c:if test="${pagingDto.startPage !=1 }">
							<li class="page-item"><a class="page-link"
								href="${pagingDto.startPage - 1 }">이전</a>
							</li>
						</c:if>


						<c:forEach var="v" begin="${pagingDto.startPage }"
							end="${pagingDto.endPage }">
							<li
								<c:choose>
					<c:when test="${pagingDto.page == v }">
					class="page-item active"
					</c:when>
					<c:otherwise>
					class="page-item"
					</c:otherwise>
					</c:choose>>


								<a class="page-link" href="${v}">${v}</a>

							</li>
						</c:forEach>
						<c:if test="${pagingDto.endPage != pagingDto.totalPage }">
							<li><a class="page-link"
								href="${pagingDto.endPage + 1 }">다음</a></li>
						</c:if>
					</ul>
				</nav>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>

</body>
</html>