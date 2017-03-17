<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	table,th,td{border:1px solid black;}
	table{border-collapse: collapse;}
</style>
</head>
<body>
	<c:if test="${strList.size() == 0}">
		<p>등록된 숙박 시설이 없습니다.</p>
	</c:if>
	<c:if test="${strList.size() > 0}">
		<table>
			<caption></caption>
			<tr>
				<th>시설번호</th>
				<th>시설구분</th>
				<th>호수(방이름)</th>
				<th>수용인원</th>
				<th>가격</th>
			</tr>
			<c:forEach var="list" items="${strList}">
				<tr>
					<td>${list.no}</td>
					<td>${list.id}<small>(${list.nameById})</small></td>
					<td><a href="structureUpdate.do?no=${list.no}">${list.name}</a></td>
					<td>${list.people}</td>
					<td>${list.originPriceToString}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>