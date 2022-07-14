<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<title>memberForm.jsp</title>
</head>
<script>
	$(function() {
		
		var is_dupId;
		var oldVal;
		$("#btnOk").fadeOut("slow");
		$("#btnOk").attr("disabled",true);
		$("#btnDupId").click(function(){
			valId=$("#id").val();
			console.log(valId);
			url="/pro17/member/chk_dupId";
			var sendData = {
					"id" : valId
				}
			$.get(url,sendData,function(receivedData){
				console.log(receivedData);
				is_dupId=receivedData;
				if(is_dupId =="dupId"){
					$("#btnDupId").next().text("이미 가입된 아이디입니다").css("color","red");
				}
				else if(is_dupId =="usableId"){
					$("#btnDupId").next().text("사용 가능한 아이디 입니다").css("color","pink");
					$("#btnOk").fadeIn("slow");
					$("#btnOk").attr("disabled",false);
				}
			});
				
			
// 			$(this).next().text("버튼누름");
// 			$("#btnOk").fadeIn("slow");
			
		});
		$("#id").on("propertychange change keyup paste input", function() {
		    var currentVal = $(this).val();
		    if(currentVal == oldVal) {
		        return;
		    }
		 
		    oldVal = currentVal;
			if(is_dupId=="usableId"){
				is_dupId="";
				$("#btnDupId").next().text("");
				$("#btnOk").fadeOut("slow");
				$("#btnOk").attr("disabled",true);
			}
		});
		
		 $('form').submit(function() {
		        if (is_dupId !="usableId") {
		            alert('중복확인이 덜되었습니다');
		            return false;
		        }
		    }); // end submit()
		
	});
</script>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<h2>회원가입양식</h2>
					<p>
						<span>아래 항목을 빠짐없이 작성해주세요</span>
					</p>
					<p>
						<a class="btn btn-primary btn-large" href="/pro17/member/list">회원목록</a>
					</p>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<form role="form" action="/pro17/member/join_run" method="post">
					<div class="form-group">
						<label for="id"> 아이디 </label> <input type="text"
							class="form-control" id="id" name="id"  required="required"/>
						<button type="button" id="btnDupId">아이디 중복체크</button>
						<span></span>
					</div>
					<div class="form-group">
						<label for="pwd"> 패스워드 </label> <input type="password"
							class="form-control" id="pwd" name="pwd" required="required"/>
					</div>
					<div class="form-group">
						<label for="name"> 이름 </label> <input type="text"
							class="form-control" id="name" name="name" required="required" />
					</div>
					<div class="form-group">
						<label for="email"> 이메일 </label> <input type="email"
							class="form-control" id="email" name="email" required="required" />
					</div>
					<button type="submit" class="btn btn-primary" id="btnOk">가입완료</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>