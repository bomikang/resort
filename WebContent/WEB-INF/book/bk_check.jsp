<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#bk_check .stateReady{color:blue; font-weight: bold;}
	#bk_check .stateProcess{color:green; font-weight: bold;}
	#bk_check .stateCancel{color:red; font-weight: bold;}
	#bk_check .stateEnd{color:gray; font-weight: bold; text-decoration: line-through;}
</style>
<script>
	$(function(){
		$(".bkcancel").submit(function(){
			var bkNo = $(this).find("input[name='bkNo']").val();
		
			if(!confirm("예약을 취소하시겠습니까?")){
				return false;
			}
		});
	});
</script>
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
					<td><a href="bookcheckdetail.do?bkNo=${book.no }&pageId=check">${book.no }</a></td>
					<td>${book.mem.name }</td>
					<td>${book.str.nameById }<br>${book.str.name }</td>
					<td>${book.startDateForm } ~ ${book.endDateForm }</td>
					<!-- 예약상태에 따른 글씨색 변화 -->
					<c:choose>
						<c:when test="${book.state=='입금대기' }">
							<td class='stateReady'>${book.state }</td>
						</c:when>
						<c:when test="${book.state=='입금완료' }">
							<td class='stateProcess'>${book.state }</td>
						</c:when>
						<c:when test="${book.state=='예약취소' }">
							<td class='stateCancel'>${book.state }</td>
						</c:when>
						<c:when test="${book.state=='예약종료' }">
							<td class='stateEnd'>${book.state }</td>
						</c:when>
					</c:choose>				
					<td>
						<c:if test="${book.state!='예약취소' }">
						<form action="bookcancel.do" method="post" class="bkcancel">
							<input type="hidden" name="bkNo" value="${book.no }">
							<input type="submit" value="취소하기">
						</form>
						</c:if>
						<c:if test="${book.state=='예약취소' }">
							-
						</c:if>
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