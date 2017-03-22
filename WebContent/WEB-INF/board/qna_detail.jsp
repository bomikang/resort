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
	<c:if test="${!empty myinfo}">
		<c:set var="user" value="${myinfo}"></c:set>
	</c:if>
	
	<c:if test="${!empty admin}">
		<c:set var="user" value="${admin}"></c:set>
	</c:if>
	
	<form action="qnaupdate.do" method="get">
		<fieldset>
			<legend>1:1 문의 상세보기</legend>
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
				<input type="text" name="title" value="${qna.title}" disabled="disabled"/>
			</p>
			<p>
				<label for="">내용</label>
				<textarea name="content" cols="100" rows="10" disabled="disabled">${qna.content}</textarea>
			</p>
			<p>
				<input type="submit" value="수정" />
				<input type="button" value="목록보기" onclick="location.replace('qna.do')" /><!-- 1:1문의 리스트화면 -->
			</p>
		</fieldset>
	</form>
</body>
</html>