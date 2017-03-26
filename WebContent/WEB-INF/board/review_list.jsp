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
	<table>
	<tr>
				<td><b>게시물 번호</b></td>
				<td><b>제목</b></td>
				<td><b>작성자</b></td>
				<td><b>작성날짜</b></td>
				<td><b>조회수</b></td>
				
			</tr>
		<c:forEach var="i"  items="${list }">
			
			<tr>
				<td>${i.rev_no }</td>
				<td><a href="rev_detail.do?no=${i.rev_no }">${i.rev_title }(${i.rev_replycnt })</a></td>
				<td>${i.rev_name }</td>
				<td>${i.rev_regdate }</td>
				<td>${i.rev_readcnt }</td>
				
		</tr>
		</c:forEach>
	</table>
	<a href="rev_insert.do">게시글 등록</a>
</body>
</html>