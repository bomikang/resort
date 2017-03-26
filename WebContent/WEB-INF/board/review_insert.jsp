<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<title>Insert title here</title>
</head>
<body>
	<form action ="rev_insert.do" method = "post" name="f1">
		<p>
			<label>제목</label>
			<input type="text" name="title">
		</p>
		<p>
			<label>내용</label>
			<textarea rows="10" cols="30" name="content">
			</textarea>
		</p>
		<p>
			<input type="submit" value="등록">
		</p>
	</form>
</body>
</html>