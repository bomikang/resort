<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style>
	.err{display:none; color:red; font-size:12px;}
	
</style>
<script type="text/javascript">
	function checkRegExr($input, limit){
		$($input).keyup(function() {
			if ( $(this).val().length > limit-1 ) {
				$(this).next(".err").css("display", "block"); //err 메시지 표신
				$(this).val($(this).val().substring(0, limit)); //넘어온 제한 글자수 만큼 짜름
			}else{
				$(this).next(".err").css("display", "none");
			}
		});
	}//checkRegExr

	$(function(){
		checkRegExr($("input[name='title']"), 50);
		checkRegExr($("textarea[name='content']"), 400);
		
		$("#btnInsert").click(function() {
			/* 1. 에러 메시지가 보이는 지 체크 */
			if ($(".err").css("display") == "block") return false;
			
			/* 2. confirm창 */
			var check = confirm("게시글을 등록하시겠습니까?");
			if ( !check ) return false;
		});
		
		$("#btnCancel").click(function() {
			var check = confirm("게시글 등록을 취소하시겠습니까?");
			if ( check ) location.replace('qna.do');
			else return false;
		});
	});//ready
</script>
</head>
<body>

<c:if test="${!empty user_info}">
	<!-- 일반회원 -->
	<c:if test="${user_info.isMng == false }">
		<c:set var="user" value="${user_info}"></c:set>
	</c:if>
</c:if>
<div class="way_top">
		<h3>1:1 문의<br /><span>홈 > 자유게시판 > 1:1 문의</span></h3>
</div>
<div class='intro_padding'>	
	<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>1:1 문의 등록</h2>
	<form action="qnainsert.do" method="post" id='f1'>
		<fieldset>
			<p>
				<label for="">작성자</label>
				<input type="text" name="name" value="${user.my_name}" readonly="readonly" disabled="disabled"/>
			</p>
			<p>
				<label for="">이메일</label>
				<input type="text" name="email" value="${user.my_mail}" readonly="readonly" disabled="disabled"/>
			</p>
			<p>
				<label for="">제목</label>
				<input type="text" name="title" required="required"/>
				<span class="err">더 이상 입력이 불가능 합니다.</span>
			</p>
			<p>
				<label for="">내용</label>
				<textarea name="content" required="required"></textarea>
				<span class="err">더 이상 입력이 불가능 합니다.</span>
			</p>
			<p class='act_btn_area'>
				<input type="submit" value="등록" id="btnInsert"/>
				<input type="button" value="취소" id="btnCancel"/><!-- 리스트로 이동 -->
			</p>
		</fieldset>
	</form>
</div>	
</body>
</html>