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
	<form action="">
		<p>
			<label for="">작성자</label>
			<input type="text" name=""/>
		</p>
	</form>
</body>
</html>