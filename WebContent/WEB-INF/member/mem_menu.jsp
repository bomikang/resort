<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.getSession().getAttribute("user_info"); %>
<c:if test="${empty user_info }">
	<a href="join.do">회원가입</a>
	<a href="login.do">로그인</a>
</c:if>
<c:if test="${!empty user_info }">
	<c:if test="${user_info.isMng==true }">
		<a href="myinfo.do">관리자정보</a>
		<a href="booklist.do?key=admin" class="forAdmin">예약 관리</a>
		<a href="booktotal.do?key=admin" class="forAdmin">예약 실적조회</a>
	</c:if>	
	<c:if test="${user_info.isMng==false }">
		<a href="myinfo.do">회원정보</a>
		<a href="bookcheck.do">예약조회/취소</a>
	</c:if>
	<a href="qna.do">1:1문의</a>
</c:if>

