<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.getSession().getAttribute("user_info"); %>
<c:if test="${empty user_info }">
	<a href="join.do"  class='nav_hover_style'>회원가입</a>
	<a href="login.do" class='nav_hover_style'>로그인</a>
</c:if>
<c:if test="${!empty user_info }">
	<c:if test="${user_info.isMng==true }">
		<a href="myinfo.do"  class='nav_hover_style'>관리자정보</a>
		<a href="booklist.do?key=admin" class="forAdmin nav_hover_style">예약 관리</a>
		<a href="booktotal.do?key=admin" class="forAdmin nav_hover_style">예약 실적조회</a>
		<a href="structureList.do"  class='nav_hover_style'>시설 관리</a>
	</c:if>	
	<c:if test="${user_info.isMng==false }">
		<a href="myinfo.do"  class='nav_hover_style'>회원정보</a>
		<a href="bookcheck.do"  class='nav_hover_style'>예약조회/취소</a>
	</c:if>
	<a href="qna.do"  class='nav_hover_style'>1:1문의</a>
</c:if>

