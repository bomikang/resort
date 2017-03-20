<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(function(){
		var stateList = new Array();
		<c:forEach var="book" items="${bList }">
			stateList.push("${book.state}");
		</c:forEach>
		
		$(".bkState").each(function(i, obj) {
			$(obj).val(stateList[i]);
			$(obj).next(".index").val(i);
			if(stateList[i]=='예약종료'){
				$(obj).empty();
				$(obj).html("<option>예약종료</option>");
				$(obj).nextAll(".btnstate").css("display", "none");
				$(obj).nextAll(".btnreset").css("display", "none");
			}
		});
		
		$(document).on("click",".btnstate", function(){
			var $tr = $(this).parents("tr")
			var bkNo = $tr.find(".bkNo").text();
			var state = $(this).parent("td").find(".bkState").val();
			if(confirm(state+"로 변경 하시겠습니까?")){
				$.ajax({
					url:"bookstate.do",
					type:"post",
					dataType:"json",
					data:{"bkNo":bkNo,
							"state" : state
					},
					success:function(data){
						console.log(data);
						if(data[0]==true){
							alert("수정되었습니다.");
							$tr.find(".state").find(".bkState").val(data[1].state);
							
							if(data[1].state=="예약취소"){
								$tr.find(".bkPrice").text("-");
								$tr.find(".bkCancel").text(data[1].cancelForm);								
							}else{
								$tr.find(".bkPrice").text(data[1].priceForm);
								$tr.find(".bkCancel").text("");
							}
						}else{
							alert("오류가 발생하였습니다.");
						}						
					}
				});
			}
			
		});
		$(document).on("click", ".btnreset", function(){
			var index = $(this).parent("td").find("input[type='hidden']").val();
			var state = stateList[index];			
			$(this).parent("td").find(".bkState").find("option[value='"+state+"']").prop("selected", true);
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
						<td class="bkNo">${book.no }</td>
						<td>${book.mem.name }</td>
						<td>${book.str.name }(${book.str.nameById })</td>
						<td>${book.tel }</td>
						<td>${book.startDateForm }</td>
						<td>${book.endDateForm }</td>
						<td class="bkPrice">
							<c:choose>							
								<c:when test="${book.state=='예약취소' }">
									-								
								</c:when>
								<c:otherwise>
									${book.priceForm }
								</c:otherwise>
							</c:choose>							
						</td>
						<td class="state">	
							<select class="bkState" name="state">
								<option value = "입금대기">입금대기</option>
								<option value = "입금완료">입금완료</option>
								<option value = "예약취소">예약취소</option>
								<option value = "예약종료">예약종료</option>
							</select>
							<input type="hidden" class="index">
							<button class="btnstate">수정</button>
							<button class="btnreset">취소</button>						
						</td>
						<td class="bkCancel">${book.cancelForm }</td>	
					</tr>				
				</c:forEach>
			</table>	
		</c:otherwise>
	</c:choose>	
</div>