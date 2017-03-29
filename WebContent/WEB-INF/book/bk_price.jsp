<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		<c:choose>
		<c:when test="${result==true }">
			<c:if test="${!empty book}">
				alert("예약이 완료 되었습니다.");
				<%
					response.sendRedirect("book.do");
				%>
			</c:if>		
		</c:when>
		<c:otherwise>
			alert("예약에 실패하였습니다.");
			<%
				response.sendRedirect("book.do");
			%>
		</c:otherwise>
	</c:choose>
	});
</script>
</head>
<body>
	
	임시 테스트용 페이지 입니다.
</body>
</html>