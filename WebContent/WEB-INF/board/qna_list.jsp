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
		<p>일반회원임</p>
		<a href="qnainsert.do">게시글 등록</a>
		
		<c:if test="${qnaList.size() == 0}">
			<p>등록된 게시물이 없습니다</p>
		</c:if>
		<c:if test="${qnaList.size() > 0}">
			<table>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>등록일</th>
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
	</c:if>
	
	<c:if test="${!empty admin}">
		<p>관리자임</p>
		<a href="qna.do">게시글 전체 보기</a>
		<a href="#">답변 완료 게시글 목록</a>
		<a href="#">답변 미완료 게시글 목록</a>
		
		<c:if test="${qnaList.size() == 0}">
			<p>등록된 게시물이 없습니다</p>
		</c:if>
		<c:if test="${qnaList.size() > 0}">
			<table>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>등록일</th>
					<th>작성자</th>
				</tr>
				<c:forEach var="list" items="${qnaList}">
					<tr>
						<td>${list.no}</td>
						<td><a href="qnadetail.do?qnano=${list.no}">${list.title}</a></td>
						<td>${list.regDateNoTimeForm}</td>
						<td>${list.member.id}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</c:if>
	
	
</body>
</html>