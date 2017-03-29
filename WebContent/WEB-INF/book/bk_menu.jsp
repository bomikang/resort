<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href="bookinform.do">예약안내</a>
<a href="book.do">예약하기</a>
<a href="bookcheck.do">예약 조회/취소</a>
<c:if test="${!empty user_info }">
	<c:if test="${user_info.isMng==true }">
		<hr />
		<a href="booklist.do" class="forAdmin">예약 List</a>
		<a href="booktotal.do" class="forAdmin">예약 실적조회</a>
	</c:if>	
</c:if>	

<script>
$(function(){
	$(".forAdmin").click(function(){
		<c:if test="${empty user_info }">
			alert("로그인이 필요한 페이지 입니다.");
			location.href="login.do?category=book";
		</c:if>	
		<c:if test="${!empty user_info }">
			<c:if test="${user_info.isMng==false }">
				alert("관리자만 접근이 가능합니다.");
				return false;
			</c:if>	
		</c:if>	
	});
});
</script>