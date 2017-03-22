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
		<a href="qnainsert.do">게시글 등록</a>
		<c:set var="user" value="${myinfo}"></c:set>
	</c:if>
	
	<c:if test="${!empty admin}">
		<c:set var="user" value="${admin}"></c:set>
	</c:if>
	
	<c:if test="${qnaList.size() == 0}">
		<p>등록된 게시물이 없습니다</p>
	</c:if>
	<c:if test="${qnaList.size() > 0}">
		<table>
			<tr>
				<th>게시글 번호</th>
				<th>제목</th>
				<th>등록 날짜</th>
			</tr>
			<c:forEach var="list" items="${qnaList}">
				<tr>
					<td>${list.no}</td>
					<td><a href="qnadetail.do?qnano=${list.no}">${list.title}</a></td>
					<td>${list.regDateNoTimeForm}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>