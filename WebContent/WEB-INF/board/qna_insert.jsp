<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${empty customer}">
		<script>
			location.href="login.do";
		</script>
	</c:if>
	
	<c:if test="${!empty myinfo}">
		<c:set var="user" value="${myinfo}"></c:set>
	</c:if>
	
	<c:if test="${!empty admin}">
		<c:set var="user" value="${admin}"></c:set>
	</c:if>
	
	<form action="qnainsert.do" method="post">
		<fieldset>
			<legend>1:1 문의 등록</legend>
			<p>
				<label for="">작성자</label>
				<input type="text" name="name" value="${user.my_name}" disabled="disabled"/>
			</p>
			<p>
				<label for="">이메일</label>
				<input type="text" name="email" value="${user.my_mail}" disabled="disabled"/>
			</p>
			<p>
				<label for="">제목</label>
				<input type="text" name="title" />
			</p>
			<p>
				<label for="">내용</label>
				<textarea name="content" cols="100" rows="10"></textarea>
			</p>
			<p>
				<input type="submit" value="등록" />
				<input type="button" value="취소" />
			</p>
		</fieldset>
	</form>
</body>
</html>