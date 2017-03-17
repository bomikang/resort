<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty myinfo }">
	<script>
		alert("로그인이 필요한 페이지 입니다.");
		location.href="login.do";
	</script>
</c:if>	
<div id="bk_check">
	<h2>예약 확인 및 취소</h2>
	<table border="1">
		<tr>
			<th>예약 번호</th>
			<th>예약자</th>
			<th>시설 명</th>
			<th>예약 기간</th>
			<th>예약 구분</th>
			<th>취소</th>
		</tr>
		<c:if test="${empty bList }">
			<tr>
				<td colspan="6">예약 정보가 없습니다.</td>
			</tr>		
		</c:if>
		<c:if test="${!empty bList }">
			<c:forEach var="book" items="${bList }">
				<tr>
					<td><a href="bookcheckdetail.do?bkNo=${book.no }">${book.no }</a></td>
					<td>${book.mem.name }</td>
					<td>${book.str.name }</td>
					<td>${book.startDateForm } ~ ${book.endDateForm }</td>
					<td>${book.state }</td>
					<td>
						<form action="" method="post">
							<input type="hidden" name="bkNo" value="${book.no }">
							<input type="submit" value="취소하기">
						</form>
					</td>
				</tr>
			</c:forEach>
		</c:if>
<!-- 		<tr>
			<th>예약 번호</th>
			<th>예약자</th>
			<th>시설 명</th>
			<th>예약 기간</th>
			<th>예약 구분</th>
			<th>취소</th>
		</tr> -->
	</table>
</div>