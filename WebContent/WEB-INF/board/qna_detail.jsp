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
	function showConfirm(msg, actionPath, formName){
		var check = confirm(msg);
		if ( !check ) return false;
		
		$("form[name='"+formName+"']").attr("action", actionPath);
	}//showConfirm
	
	function checkEmptyAdmin(){
		/* 답변이 존재하면 관리자  답변 내용을 보여주고, 본인의 수정 버튼을 비활성화
		답변이 존재하지 않으면 관리자 답변 내용 숨김*/
		if ( ${!empty qnaAdmin} ) {
			$("form[name='adminForm']").css("display", "block");
			$("#btnReply").css("display", "none");
			$("#btnGoList").css("display", "none");
			$("#btnUpdate").attr("disabled", "disabled");
		}else {
			$("form[name='adminForm']").css("display", "none");	
		}
	}//checkEmptyAdmin
	
	$(function(){
		checkEmptyAdmin();//답변 존재 여부 함수 호출
		
		/* <관리자> */
		//답변 버튼을 클릭했을 때 관리자 답변 form 뜨도록함
		$("#btnReply").click(function() {
			$("form[name='adminForm']").css("display", "block");
		});
		//관리자 답변 등록
		$("#btnAdminInsert").click(function() {
			var check = confirm("답변을 등록하시겠습니까?");
			if ( !check ) return false;
			$("form[name='adminForm']").attr("action", "qnainsert.do");
		});
		$("#btnAdminCancel").click(function() {
			var check = confirm("답변 등록을 취소하시겠습니까?");
			//'확인'일 떼 return false
			if ( check ) { 
				$("form[name='adminForm']").css("display", "none");
				$("form[name='adminForm']").reset(); //관리자 form 초기화
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
				$("#adminContent").removeAttr("readonly"); //readonly 해제
				
				return false;
			}else if($(this).val() == "완료"){
				showConfirm("답변을 수정하시겠습니까?", "qnaupdate.do", "adminForm");
			}
		});
		//관리자 답변 삭제
		$("#btnDeleteAdminQna").click(function() {
			//"완료" => "수정" 
			//"취소" => "삭제" 
			//"목록" => show 
			if ($(this).val() == "취소") {
				var check = confirm("답변 수정을 취소하시겠습니까?");
				if ( !check ) return false;
				
				$(this).val("삭제");
				$("#btnUpdateAdminQna").val("수정");
				$("#btnDeleteAdminQna").next("input").css("display", "inline-block");
				$("#adminContent").val("${qnaAdmin.content}"); //기본 내용으로 되돌리기
				$("#adminContent").attr("readonly", "readonly") //readonly 재설정
				return false;
			}else if($(this).val() == "삭제"){
				showConfirm("정말 삭제하시겠습니까?", "qnadelete.do", "adminForm");
			}
		});
		
		/* <회원> */
		$("#btnUpdate").click(function() {
			$("form[name='memberForm']").attr("action", "qnaupdate.do");
		});
		$("#btnDelete").click(function() {
			showConfirm("정말 삭제하시겠습니까?", "qnadelete.do", "memberForm");
		});
	});//ready
</script>
</head>
<body>
	<form action="" method="get" name="memberForm">
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
				<textarea name="content" readonly="readonly">${qna.content}</textarea>
			</p>
			<p class='act_btn_area'>
				<input type="hidden" name="qnano" value="${qna.no}" /><!-- 게시물 번호 hidden으로 심기 -->
				<c:if test="${!empty user_info}">
					<!-- 회원 -->
					<c:if test="${user_info.isMng == false}">
						<input type="submit" value="수정" id="btnUpdate"/>
						<input type="submit" value="삭제" id="btnDelete"/>
						<input type="button" value="목록" onclick="location.replace('qna.do')" /><!-- list화면으로 이동 -->
					</c:if>
					
					<!-- 관리자-->
					<c:if test="${user_info.isMng == true}">
						<input type="button" value="답변" id="btnReply"/>
						<input type="button" value="목록" id="btnGoList" onclick="location.replace('qna.do')" />
					</c:if>
				</c:if>
			</p>
		</fieldset>
	</form>
	
	<!-- 관리자가 회원의 게시글에서 답변을 눌렀을 때 나오게 하는 insert / detail / update 화면-->
	<form action="" method="post" name='adminForm'>
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
			
			<!-- 답변이 미존재하면 insert화면 -->
			<c:if test="${empty qnaAdmin}">
				<p>
					<label for="">내용</label>
					<textarea name="content" id="adminContent" required="required"></textarea>
				</p>
				<p class='act_btn_area'>
					<input type="hidden" name="article" value="${qna.no}" /><!-- 회원 게시글 번호 심기 -->
					<input type="submit" value="등록" id="btnAdminInsert"/>
					<input type="button" value="취소" id="btnAdminCancel"/>
				</p>
			</c:if>
			
			<!-- 답변이 존재하면 detail화면 -->
			<c:if test="${!empty qnaAdmin}">
				<p>
					<label for="">내용</label>
					<textarea name="content" readonly="readonly" id="adminContent" required="required">${qnaAdmin.content}</textarea>
				</p>
				
				<!-- 로그인 상태면서 관리자 -->
				<c:if test="${!empty user_info}">
					<c:if test="${user_info.isMng == true}">
						<p class='act_btn_area'>
							<input type="hidden" name="article" value="${qna.no}" /><!-- 회원 게시글 번호 심기 -->
							<input type="hidden" name="qnano" value="${qnaAdmin.no}" /><!-- 관리자 게시글 번호 심기 -->
							<input type="submit" value="수정" id="btnUpdateAdminQna"/>
							<input type="submit" value="삭제" id="btnDeleteAdminQna"/>
							<input type="button" value="목록" onclick="location.replace('qna.do')" /><!-- list화면으로 이동 -->
						</p>
					</c:if>
				</c:if>
			</c:if>
		</fieldset>
	</form>
</body>
</html>