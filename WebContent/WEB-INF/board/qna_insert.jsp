<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#btnInsert").click(function() {
			var check = confirm("문의 게시글을 등록하시겠습니까?");
			 
			if ( !check ) return false;
			
			$("form[name='f1']").attr("action", "qnainsert.do");
		});
	});
</script>
</head>
<body>
	<c:if test="${!empty myinfo}">
		<c:set var="user" value="${myinfo}"></c:set>
	</c:if>
	
	<c:if test="${!empty admin}">
		<c:set var="user" value="${admin}"></c:set>
	</c:if>
	
	<form action="" method="post" id='f1'>
		<fieldset>
			<legend>1:1 문의 등록</legend>
			<p>
				<label for="">작성자</label>
				<input type="text" name="name" value="${user.my_name}" readonly="readonly"/>
			</p>
			<p>
				<label for="">이메일</label>
				<input type="text" name="email" value="${user.my_mail}" readonly="readonly"/>
			</p>
			<p>
				<label for="">제목</label>
				<input type="text" name="title" required="required"/>
			</p>
			<p>
				<label for="">내용</label>
				<textarea name="content" cols="100" rows="10" required="required"></textarea>
			</p>
			<p>
				<input type="submit" value="등록" id="btnInsert"/>
				<input type="button" value="취소" onclick="location.replace('qna.do')"/><!-- 리스트로 이동 -->
			</p>
		</fieldset>
	</form>
</body>
</html>