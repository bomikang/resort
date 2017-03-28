<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

</head>
<body>
	<form action ="rev_update.do" method = "post">
		<input type="hidden" name="no" value="${rev.rev_no }">
		<p>
			<label>제목</label>
			<input type="text" name="title" value="${rev.rev_title }">
		</p>
		<p>
			<label>내용</label>
			<textarea rows="10" cols="30" name="content">${rev_Detail.rev_detail }</textarea>
		</p>
		<p>
			<input type="submit" value="등록">
		</p>
	</form>
</body>
</html>