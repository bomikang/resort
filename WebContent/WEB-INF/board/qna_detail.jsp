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
	$(function(){
		$("#btnUpdate").click(function() {
			var check = confirm("문의 게시글을 수정하시겠습니까?");
			 
			if ( !check ) return false;
			
			$("form[name='f1']").attr("action", "qnaupdate.do");
		});
		$("#btnDelete").click(function() {
			var check = confirm("정말 삭제하시겠습니까?");
			 
			if ( !check ) return false;
			
			$("form[name='f1']").attr("action", "qnadelete.do");
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
				<input type="submit" value="수정" id="btnUpdate"/>
				<input type="submit" value="삭제" id="btnDelete"/>
				<input type="button" value="목록" onclick="location.replace('qna.do')" /><!-- list화면으로 이동 -->
			</p>
		</fieldset>
	</form>
	
	<c:if test="${!empty myinfo}">
		<c:set var="user" value="${myinfo}"></c:set>
	</c:if>
	
	<c:if test="${!empty admin}">
		<c:set var="user" value="${admin}"></c:set>
	</c:if>
	
</body>
</html>