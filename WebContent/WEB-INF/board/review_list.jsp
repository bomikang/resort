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
	<table>
		<c:forEach var="i"  items="${list }">
			<tr>
				<td>${i.rev_no }</td>
				<td><a href="rev_detail.do">${i.rev_title }</a></td>
				<td>${i.rev_name}</td>
				<td>${i.rev_regdate }</td>
				<td>${i.rev_readcnt }</td>
		</tr>
		</c:forEach>
	</table>
	<a href="rev_insert.do">게시글 등록</a>
</body>
</html>