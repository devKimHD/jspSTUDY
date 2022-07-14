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
<script>
	$(function() {
		var is_like;
		var comment_tr;
		getCommentList();
		var update_result = "${sessionScope.update_result}";
		if (update_result == "true") {
			alert("수정완료");
		} else if (update_result == "false") {
			alert("수정 오류");
		}
		$("#btnModify").click(function(e) {
			e.preventDefault();
			$("*[readonly]").removeAttr("readonly");
			$(this).fadeOut("slow");
			$("#btnModifyRun").fadeIn("slow");
		});
		$("#a_list").click(function(e) {
			e.preventDefault();
			$("#frmPaging > input[name=b_no]").val("");
			$("#frmPaging").submit();
		});
		$("i.fa-heart").click(function() {
			// 		$(this).css("color","red");
			// 		var span=$(this).next();
			// 		span.text("1");
			var b_no = $(this).attr("data-bno");
			var url = "/pro17/board/like";
			var sendData = {
				"b_no" : b_no
			}
			var that = $(this);
			$.post(url, sendData, function(receivedData) {
				console.log("receivedData", receivedData);

				if (receivedData == "success") {
					var span = that.next();
					if (is_like == "true") {
						that.css("color", "graytext");

						span.text(parseInt(span.text().trim()) - 1);
						is_like = "false"
					} else {
						that.css("color", "red");

						span.text(parseInt(span.text().trim()) + 1);
						is_like = "true"
					}

				}
			});

		});
		//처음 화면시작시 새로 고침할때 넘어갈 비동기 요청
		$.get("/pro17/board/is_like", {
			"b_no" : "${boardVo.b_no}"
		}, function(receivedData) {
			console.log(receivedData);
			is_like = receivedData;
			if (receivedData == "true") {
				$("i.fa-heart").css("color", "red");
			} else {
				$("i.fa-heart").css("color", "graytext");
			}
		});
		$("#btnComment").click(function() {
			var c_content = $(this).prev().val();
			console.log(c_content);
			var url = "/pro17/board/commentWrite";
			var sendData = {
				"c_content" : c_content,
				"b_no" : $(this).attr("data-bno")
			}
			$.post(url, sendData, function(receivedData) {
				console.log(receivedData);
				if (receivedData == "true") {
					$("#c_content").val("");
					//0보다 큰 tr다 지워버리기
					$("#commentTable > tbody > tr:gt(0)").remove();
					getCommentList();
				}

			});
		});

		function getCommentList() {
			var url = "/pro17/board/commentList";
			var sendData = {
				"b_no" : "${boardVo.b_no}"

			};
			$.get(url, sendData,
							function(receivedData) {

								var jData = JSON.parse(receivedData);

								console.log(jData);
								$
										.each(
												jData,
												function() {
													var tr = "<tr>";
													tr += "<td>" + this.c_id
															+ "</td>";
													tr += "<td>"
															+ this.c_content
															+ "</td>";
													tr += "<td>" + this.id
															+ "</td>";//댓글 작성자 와 로그인한 사용자 아이디 비교
													tr += "<td>" + this.c_date
															+ "</td>";
													tr += "<td>";
													var login_id = "${sessionScope.memberVo.id}";
													if (login_id == this.id) {
														//클래스는 여러개 있어도 상관없기에 아이디 대신 클래스 사용
														tr += "<button type='button' class='btn btn-sm btn-warning btnCommentModify' >수정</button>";//id 달아서 찾으려 하면 안됨 문서가 다 준비되었을때 찾음(초기) 그래서 상위 엘리먼트에 이벤트를 주던가 해야함
														tr += "<button type='button' class='btn btn-sm btn-danger btnCommentDelete' data-cid='" + this.c_id + "'>삭제</button>";
													}

													tr += "</td>";
													tr += "</tr>";
													$("#commentTable > tbody")
															.append(tr);
												});
							});
		}
		//댓글 수정버튼
		
		$("#commentTable").on("click", ".btnCommentModify", function() {
			$("#modal-796549").trigger("click");//연쇄반응 a태크 클릭한 효과
			//$(this).parent() //td
			comment_tr=$(this).parent().parent();
			var c_content = comment_tr.find("td").eq(1).text(); //tr
			var c_no = comment_tr.find("td").eq(0).text(); //tr
			console.log(c_content);
			$("#btnCommentSave").attr("data-cno",c_no);
			$("#modalContent").val(c_content);

		});

		
		//댓글 저장버튼
		$("#btnCommentSave").click(function(){
			var c_content=$("#modalContent").val();
			var c_no=$(this).attr("data-cno");
// 			console.log("c_no",c_no);
// 			console.log("c_content",c_content);
			var url="/pro17/board/commentModify";
			var sendData={
					"c_no":c_no,
					"c_content":c_content
			};
			$.post(url,sendData,function(rData){
				console.log(rData);
				if(rData =="true"){
					var modify_content=$("#modalContent").val();
					comment_tr.find("td").eq(1).text(modify_content);
				}
				else{
					alert("댓글 수정 오류");
				}
			});
		});
		//댓글삭제
		$("#commentTable").on("click", ".btnCommentDelete", function(){
			var c_id=$(this).attr("data-cid");
			var url="/pro17/board/commentDelete";
			var sData={
					"c_id":c_id
			};
			$.post(url,sData,function(rData){
				console.log(rData);
				if(rData== "true"){
					$("#commentTable > tbody > tr:gt(0)").remove();
					getCommentList();
				}
				else{
					alert("댓글 삭제 오류");
				}
			});
		});
	});
</script>
</head>
<body>
	<%
		session.removeAttribute("update_result");
	%>
	<%@ include file="/WEB-INF/views/include/paging_form.jsp"%>
	<div class="container-fluid">
		<!-- 댓글모달 -->
		<div class="row">
			<div class="col-md-12">
				<a id="modal-796549" href="#modal-container-796549" role="button"
					class="btn" data-toggle="modal" style="display: none;">Launch
					demo modal</a>

				<div class="modal fade" id="modal-container-796549" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="myModalLabel">댓글 수정</h5>
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">×</span>
								</button>
							</div>
							<div class="modal-body">
								<input type="text" class="form-control" id="modalContent">
							</div>
							<div class="modal-footer">

								<button type="button" class="btn btn-primary"
									id="btnCommentSave" data-cno="" data-dismiss="modal">저장</button>
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">닫기</button>
							</div>
						</div>

					</div>

				</div>

			</div>
		</div>
		<!-- //댓글모달 -->
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<h2>글 내용 보기</h2>

					<p>
						<a class="btn btn-primary btn-large" href="#" id="a_list">글목록</a>
					</p>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<form action="/pro17/board/modify_run" method="post">
					<input type="hidden" name="b_no" value="${boardVo.b_no }">
					<input type="hidden" name="page" value="${param.page}"> <input
						type="hidden" name="id" value="${boardVo.id }">
					<table class="table">

						<tbody>
							<tr>
								<th>글번호</th>
								<td>${boardVo.b_no }</td>
							</tr>
							<tr>
								<th>글제목</th>
								<td><input type="text" value="${boardVo.b_title}"
									class="form-control" name="b_title" readonly></td>
							</tr>
							<tr>
								<th>글내용</th>
								<td><textarea class="form-control" name="b_content"
										readonly>${boardVo.b_content}</textarea></td>
							</tr>
							<tr>
								<th>작성자</th>
								<td>${boardVo.id }</td>
							</tr>
							<tr>
								<th>작성일</th>
								<td><fmt:formatDate value="${boardVo.b_date }"
										pattern="YYYY-MM-dd HH:mm:ss" /></td>
							</tr>
							<tr>
								<th>조회수</th>
								<td>${boardVo.read_count}</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center"><i
									class="fas fa-heart"
									style="font-size: 30px; color: graytext; cursor: pointer;"
									data-bno="${boardVo.b_no }"></i> <span style="font-size: 30px">${boardVo.like_count }</span>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center"><c:if
										test="${ boardVo.id eq sessionScope.memberVo.id}">
										<a href="#" class="btn btn-warning" id="btnModify">수정</a>
										<button type="submit" class="btn btn-success"
											id="btnModifyRun" style="display: none;">수정완료</button>

										<a
											href="/pro17/board/delete_run?b_no=${boardVo.b_no}&page=${param.page}&id=${boardVo.id}"
											class="btn btn-danger">삭제</a>
									</c:if> <a
									href="/pro17/board/reply_form?g_no=${boardVo.g_no }&re_seq=${boardVo.re_seq}&re_level=${boardVo.re_level}"
									class="btn btn-primary">답글</a></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div class="col-md-2"></div>
		</div>
		<!-- 댓글 -->
		<div class="row">
			<div class="col-md-2"></div>

			<div class="col-md-8">
				<input type="text" size="50" id="c_content">
				<button type="button" class="btn btn-sm btn-primary" id="btnComment"
					data-bno="${boardVo.b_no }">완료</button>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<table class="table" id="commentTable">
				<tbody>
					<tr>
						<td>#</td>
						<td>내용</td>
						<td>작성자</td>
						<td>날짜</td>
						<td></td>
					</tr>

				</tbody>
			</table>
		</div>
		<div class="col-md-2"></div>
	</div>
</body>
</html>