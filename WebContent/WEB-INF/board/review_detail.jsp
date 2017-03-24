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

	<tr>
		<td>제목 : ${rev_list.rev_title }</td>
	</tr>		
	<tr>
		<td>이름 :${rev_list.rev_name }</td>
	</tr>
	<tr>
		<td>내용 :${rev_detail.rev_detail }</td>
	</tr>	
	<tr>
		<td><a href="">삭제</a></td> 
	</tr>		


</table>
</body>
</html>