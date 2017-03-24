<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	function showConfirm(msg, actionPath){
		var check = confirm(msg);
		if ( !check ) return false;
		$("form[name='f1']").attr("action", actionPath);
	}
	$(function(){
		/* 답변이 존재하면 관리자 답변 폼이 보이게 하고 답변 내용을 보여줌*/
		if ( ${!empty qnaAdmin} ) {
			$("form[name='adminInsertForm']").css("display", "block");
			$("#btnReply").css("display", "none");
			$("#btnGoList").css("display", "none");
		}else {
			$("form[name='adminInsertForm']").css("display", "none");	
		}
		
		/* <관리자> */
		//답변 버튼을 클릭했을 때 관리자 답변 form 뜨도록함
		$("#btnReply").click(function() {
			$("form[name='adminInsertForm']").css("display", "block");
		});
		$("#btnAdminInsert").click(function() {
			var check = confirm("답변을 등록하시겠습니까?");
			if ( !check ) return false;
			$("form[name='adminInsertForm']").attr("action", "qnainsert.do");
		});
		$("#btnAdminCancle").click(function() {
			var check = confirm("답변 등록을 취소하시겠습니까?");
			//'확인'일 떼 return false
			if ( check ) { 
				$("form[name='adminInsertForm']").css("display", "none");
				$("form[name='adminInsertForm']").reset(); //관리자 form 초기화
				return false;
			}
		});
		//관리자 답변 수정
		$("#btnUpdateAdminQna").click(function() {
			//"수정" => "완료"
			//"삭제" => "취소"
			//"목록" => hide
			if ($(this).val() == "수정") {
				$(this).val("완료"); 
				$("#btnDeleteAdminQna").val("취소");
				$("#btnDeleteAdminQna").next("input").css("display", "none");
				$("textarea[name='content']").removeAttr("readonly"); //readonly 해제
				
				return false;
			}else{
				//qnaupdate.do
			}
		});
		//관리자 답변 삭제
		$("#btnDeleteAdminQna").click(function() {
			if ($(this).val() == "취소") {
				$("form[name='adminInsertForm']").reset();
				return false;
			}else{
				//qnadelete.do
			}
		});
		
		/* <회원> */
		$("#btnUpdate").click(function() {
			showConfirm("문의 게시글을 수정하시겠습니까?", "qnaupdate.do");
		});
		$("#btnDelete").click(function() {
			showConfirm("정말 삭제하시겠습니까?", "qnadelete.do");
		});
	});
</script>
</head>
<body>
	<form action="" method="get" name='f1'>
		<fieldset>
			<legend>1:1 문의 상세보기</legend>
			<p>
				<label for="">작성자</label>
				<input type="text" name="name" value="${qna.member.name}" readonly="readonly"/>
			</p>
			<p>
				<label for="">이메일</label>
				<input type="text" name="email" value="${qna.member.mail}" readonly="readonly"/>
			</p>
			<p>
				<label for="">제목</label>
				<input type="text" name="title" value="${qna.title}" readonly="readonly"/>
			</p>
			<p>
				<label for="">내용</label>
				<textarea name="content" cols="100" rows="10" readonly="readonly">${qna.content}</textarea>
			</p>
			<p>
				<input type="hidden" name="qnano" value="${qna.no}" /><!-- 게시물 번호 hidden으로 심기 -->
				<c:if test="${!empty myinfo}">
					<input type="submit" value="수정" id="btnUpdate"/>
					<input type="button" value="삭제" id="btnDelete"/>
					<input type="button" value="목록" onclick="location.replace('qna.do')" /><!-- list화면으로 이동 -->
				</c:if>
				<c:if test="${!empty admin}">
					<input type="button" value="답변" id="btnReply"/><!-- 관리자 일때 버튼 추가 -->
					<input type="button" value="목록" id="btnGoList" onclick="location.replace('qna.do')" />
				</c:if>
			</p>
			
		</fieldset>
	</form>
	
	<!-- 관리자가 회원의 게시글에서 답변을 눌렀을 때 나오게 하는 insert / detail 화면-->
	<form action="" method="post" name='adminInsertForm'>
		<fieldset>
			<legend>1:1 문의 답변</legend>
			<p>
				<label for="">작성자</label>
				<input type="text" name="name" value="관리자" readonly="readonly"/>
			</p>
			<p>
				<label for="">제목</label>
				<input type="text" name="title" value="문의하신 내용의 답변입니다." readonly="readonly"/>
			</p>
			<c:if test="${empty qnaAdmin}">
				<p>
					<label for="">내용</label>
					<textarea name="content" cols="100" rows="10"></textarea>
				</p>
				<p>
					<input type="hidden" name="article" value="${qna.no}" /><!-- 회원 게시글 번호 심기 -->
					<input type="submit" value="등록" id="btnAdminInsert"/>
					<input type="button" value="취소" id="btnAdminCancle"/>
				</p>
			</c:if>
			<c:if test="${!empty qnaAdmin}">
				<p>
					<label for="">내용</label>
					<textarea name="content" cols="100" rows="10" readonly="readonly">${qnaAdmin.content}</textarea>
				</p>
				<c:if test="${!empty admin}">
				<p>
					<input type="hidden" name="article" value="${qna.no}" />
					<input type="submit" value="수정" id="btnUpdateAdminQna"/>
					<input type="button" value="삭제" id="btnDeleteAdminQna"/>
					<input type="button" value="목록" onclick="location.replace('qna.do')" /><!-- list화면으로 이동 -->
				</p>
				</c:if>
			</c:if>
		</fieldset>
	</form>
</body>
</html>