<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty myinfo }">
	<script>
		alert("로그인이 필요한 페이지 입니다.");
		location.href="login.do";
	</script>
</c:if>	
<script>
	$(function(){
		$("#btnBack").click(function(){
			location.href = "bookcheck.do";
		});
	});
</script>
<div id="bk_detail">
	<c:if test="${empty book }">
		<script type="text/javascript">
			alert("예약 정보가 존재하지 않습니다.");
		</script>	
	</c:if>
	<c:if test="${!empty book }">
		<h2>예약 내역</h2>
		<h4>[예약자 정보]</h4>
		<table border="1">
			<tr>
				<th>예약 번호</th>
				<td>${book.no }</td>
			</tr>
			<tr>
				<th>예약자</th>
				<td>${book.mem.name }</td>
			</tr>
			<tr>
				<th>연락처</th>
				<td>${book.tel }</td>
			</tr>
		</table>
		<br>
		<h4>[시설 정보]</h4>
		<table border="1">
			<tr>
				<th>시설 명</th>
				<td>${book.str.nameById } ${book.str.name }</td>
			</tr>
			<tr>
				<th>시설 정보</th>
				<td>
					면적 : ${book.str.width }/ 수용인원 : ${book.str.people }명<br>
					옵션 : ${book.str.option }
				</td>
			</tr>
			<tr>
				<th>예약 기간</th>
				<td>${book.startDateForm } ~ ${book.endDateForm }</td>
			</tr>
			<tr>
				<th>예약 구분</th>
				<td>${book.state }</td>
			</tr>
			<tr>
				<th>가격</th>
				<td>${book.priceForm }</td>
			</tr>
		</table>	
		<form>
			<input type="hidden" value="${book.no }" name="bkNo">
			<input type="submit" value="취소하기">
			<button type="button" id="btnBack">돌아가기</button>
		</form>			
	</c:if>
</div>