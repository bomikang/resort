<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	#no_delete{
		display: none;
	}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<table>
	<tr>
		<td>제목 : ${rev_list.rev_title }</td>
	</tr>		
	<tr>
		<td>이름 :${rev_list.rev_name }</td>
	</tr>
	<tr>
		<td>내용 :${rev_detail.rev_detail }</td>
	</tr>	
</table>
	<c:if test="${delete }">
		<span id="delete"><a href="rev_delete.do?no=${rev_list.rev_no }" id="delete">삭제</a></span>
	</c:if>
	<c:if test="${no_delete }">
		<span id="no_delete"><a href="rev_delete.do?no=${rev_list.rev_no }" id="delete">삭제</a></span>
	</c:if>
</body>
</html>