<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/bootstrap_cdn.jsp" %>
</head>
<body>
<%-- ${cookie.login_id.value} --%>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron">
				<h2>로그인</h2>
				
				<p>
					<a class="btn btn-primary btn-large" href="/pro17/member/join_form">회원가입</a>
				</p>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-2">
		</div>
		<div class="col-md-8">
			<form role="form" action="/pro17/member/login_run" method="post">
				<div class="form-group">
					 
					<label for="id">
						아이디
					</label>
					<input type="text" class="form-control" required="required" id="id" name="id"
					value="${cookie.login_id.value}"/>
				</div>
				<div class="form-group">
					 
					<label for="pwd">
						비밀번호
					</label>
					<input type="password" class="form-control" required="required" id="pwd" name="pwd" />
				</div>
				
				<div class="checkbox">
					 
					<label>
						<input type="checkbox" name="saveId" value="save"/> 아이디 저장
					</label>
				</div> 
				<button type="submit" class="btn btn-primary">로그인</button>
			</form>
		</div>
		<div class="col-md-2">
		</div>
	</div>
</div>
</body>
</html>