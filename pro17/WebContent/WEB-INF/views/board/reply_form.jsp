<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/views/include/bootstrap_cdn.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글달기</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<h2>답글쓰기</h2>
					<p>아래항목을 빠짐없이 넣으세요.</p>
					<p>
						<a class="btn btn-primary btn-large" href="/pro17/board/list">글목록</a>
					</p>
				</div>
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-8">
						<form role="form" action="/pro17/board/reply_run" method="post">
						<input type="hidden" name="g_no" value="${param.g_no }">
						<input type="hidden" name="re_seq" value="${param.re_seq }">
						<input type="hidden" name="re_level" value="${param.re_level }">
							<div class="form-group">

								<label for="b_title"> 답글제목(필수입력사항) </label> <input type="text"
									class="form-control" id="b_title" name="b_title"
									value="[re]" 
									required/>
							</div>
							<div class="form-group">

								<label for="b_content"> 답글내용 </label>
								<textarea class="form-control" id="b_content" name="b_content"
								required></textarea>
							</div>
							<div class="form-group">

								<label for="b_file_name"> 첨부파일 </label> <input type="file"
									class="form-control-file" id="b_file_name" name="b_file_name" />

							</div>
<!-- 							<div class="form-group"> -->

<!-- 								<label for="id">작성자</label> <input type="text" -->
<!-- 									class="form-control" id="id" name="id"  -->
<!-- 									required/> -->
<!-- 							</div> -->
							<button type="submit" class="btn btn-primary">작성완료</button>
						</form>
					</div>
					<div class="col-md-2"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>