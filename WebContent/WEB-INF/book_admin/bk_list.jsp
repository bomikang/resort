<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(function(){
		var stateList = new Array();
		<c:forEach var="book" items="${bList }">
			stateList.push("${book.state}");
		</c:forEach>
		
		$(".bkState").each(function(i, obj) {
			$(obj).val(stateList[i]);
		});
	});
</script>
<div id="bk_list">
	<h4>전체 예약 목록</h4>
	<c:choose>
		<c:when test="${empty bList }">
			<script type="text/javascript">
				alert("조회할 목록이 없습니다.");
			</script>
		</c:when>
		<c:otherwise>
			<table border="1">
				<tr>
					<th>예약번호</th>
					<th>예약자명</th>
					<th>시설 이름</th>
					<th>연락처</th>
					<th>시작날짜</th>
					<th>끝날짜</th>
					<th>금액</th>
					<th>예약상태</th>
					<th>취소날짜</th>	
				</tr>
				<c:forEach var="book" items="${bList }">
					<tr>
						<td>${book.no }</td>
						<td>${book.mem.name }</td>
						<td>${book.str.name }</td>
						<td>${book.tel }</td>
						<td>${book.startDateForm }</td>
						<td>${book.endDateForm }</td>
						<td>${book.priceForm }</td>
						<td>
							<select class="bkState">
								<option value = "입금대기">입금대기</option>
								<option value = "입금완료">입금완료</option>
								<option value = "예약취소">예약취소</option>
								<option value = "예약종료">예약종료</option>
							</select>
						</td>
						<td>${book.cancelForm }</td>	
					</tr>				
				</c:forEach>
			</table>	
		</c:otherwise>
	</c:choose>	
</div>