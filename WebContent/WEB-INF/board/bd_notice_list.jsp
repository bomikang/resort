<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(function(){
		$("#btnAdd").click(function(){
			<c:if test="${!empty admin}">
				location.href="noticeinsert.do";
			</c:if>
			<c:if test="${empty admin}">
				alert("관리자만 접근할 수 있습니다.");
				location.href="login.do";
			</c:if>
		});
	});
</script>

<div id="notice_list">
	<h2>공지사항 List</h2>
	<table border="1">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회</th>
		</tr>
		<c:if test="${!empty rnList }">
			<c:forEach items="${rnList }" var="real">
				<tr>
					<td>공지</td>
					<td>${real.title }</td>
					<td>${real.writer.name }</td>
					<td>${real.regDateForm }</td>
					<td>${real.readCnt }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${!empty nList }">
			<c:forEach items="${nList }" var="notc">
				<tr>
					<td>${notc.no }</td>
					<td>${notc.title }</td>
					<td>${notc.writer.name }</td>
					<td>${notc.regDateForm }</td>
					<td>${notc.readCnt }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty rnList && empty nList }">
			<tr>
				<td colspan="5">공지사항이 없습니다.</td>
			</tr>
		</c:if>
	</table>
	<c:if test="${!empty admin}">
		<button id="btnAdd">글 등록</button>
		<button id="btnRmv">글 삭제</button>
	</c:if>
	<!-- <form action="faqdelete.do" method="post" id="delete">
		<input type="hidden" name="numbers" id="numbers">
	</form>	 -->
</div>