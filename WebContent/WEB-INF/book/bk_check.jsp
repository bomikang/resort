<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#bk_check .stateReady{color:blue; font-weight: bold;}
	#bk_check .stateProcess{color:green; font-weight: bold;}
	#bk_check .stateCancel{color:red; font-weight: bold;}
	#bk_check .stateEnd{color:gray; font-weight: bold; text-decoration: line-through;}
	#bk_check .bkStrIdName{color:green; font-weight: bold;}
</style>
<script>
	$(function(){
		setScreen();
		
		//setTable(${bList});
		$(document).on("submit", ".bkcancel", function(){
			var bkNo = $(this).find("input[name='bkNo']").val();
		
			if(!confirm("예약을 취소하시겠습니까?")){
				return false;
			}
		});
		/* 예약상태와 시설 구분을 선택한 후 조회하기 버튼 클릭 시 ajax통해 table을 다시 구성하도록 만듦 */
		$(document).on("submit","form[name='book1']",function(){
			setScreen();
			return false;
		});
		
	});//ready
	function setScreen(){
		var stateList = new Array();
		$("input[name='bkState']").each(function(i, obj) {
			if($(obj).prop("checked")==true){
				stateList.push($(obj).val());
				console.log($(obj).val());
			}	
		});
		var bkState = stateList.join(",");
			
		var strId = $("select[name='bkStrId']").val();
		
		$.ajax({
			url:"bookcheck.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"bkState":bkState,"strId":strId},
			success:function(data){
				console.log(data);
				$("#bkCheckRes").html("<span class='bkStrIdName'>"+data[0]+"</span>로 검색한 결과입니다.");
				setTable(data);					
			} 
		}); 
	}
	
	function setTable(data){
		$("#bkTable").empty();	
	
		var bList =  data[1];
	
		var tableForm = "<tr><th>접수 날짜</th><th>시설 명</th><th>이용 기간</th><th>예약 번호</th><th>총 금액</th><th>예약 구분</th><th>취소</th></tr>";
		
		if(bList==null||bList==undefined){
			tableForm = "<tr><td colspan='7'>예약 정보가 없습니다.</td></tr>";
		}else{
			for(var j=0;j<bList.length;j++){
				var strUrl = "structure.do?people=4&houseId="+bList[j].str.id;
				var bkDetailUrl = "bookcheckdetail.do?bkNo="+bList[j].no+"&pageId=check";
				tableForm += "<tr>";
				tableForm += "<th>"+bList[j].regDateNoTimeForm+"</th>";//접수 날짜
				tableForm += "<td><a href='"+strUrl+"' target='_blank' class='toStr'>"+bList[j].str.nameById+"<br>"+bList[j].str.name+"</a></td>";//시설명 - 시설보기로 hyperlink				
				tableForm += "<td><a href='"+bkDetailUrl+"' class='toDetail'>"+bList[j].startDateForm+" ~ "+bList[j].endDateForm+"</a></td>";//예약기간
					<!-- 시설명 클릭시 시설정보로 넘어갈 수 있도록 -->
				
				tableForm += "<td><a href='"+bkDetailUrl+"' class='toDetail'>"+bList[j].no+"</a></td>";//예약번호
				tableForm += "<td><a href='"+bkDetailUrl+"' class='toDetail'>"+bList[j].priceForm +"</a></td>"//총가격
				switch(bList[j].state){
				case '입금대기':
					tableForm += "<td class='stateReady'>"+bList[j].state+"</td>";
					break;
				case '입금완료':
					tableForm += "<td class='stateProcess'>"+bList[j].state+"</td>";				
					break;
				case '예약취소':
					tableForm += "<td class='stateCancel'>"+bList[j].state+"</td>";
					break;
				case '예약종료':
					tableForm += "<td class='stateEnd'>"+bList[j].state+"</td>";
					break;
				}
				tableForm += "<td>";
				if(bList[j].state != '예약취소'){
					tableForm += "<form action='bookcancel.do' method='post' class='bkcancel'>";
					tableForm += "<input type='hidden' name='bkNo' value='"+bList[j].no+"'>";
					tableForm += "<input type='submit' value='취소하기'>";
					tableForm += "</form>";
				}else{
					tableForm += "-";
				}
				tableForm += "</td></tr>";
			}
			
		}		
		$("#bkTable").append(tableForm);
	}
</script>
<c:if test="${empty myinfo }">
	<script>
		alert("로그인이 필요한 페이지 입니다.");
		location.href="login.do";
	</script>
</c:if>	
<div id="bk_check">
	<h2>예약 확인 및 취소</h2>
	<form action="bookcheck.do" method="post" name="book1">
		<fieldset>		
			<p>
				예약 상태 : 
				<input type="checkbox" value="입금대기" id="bkReady" name="bkState" checked="checked"><label for="bkReady">입금대기</label>
				<input type="checkbox" value="입금완료" id="bkProcess" name="bkState" checked="checked"><label for="bkRbkProcesseady">입금완료</label>
				<input type="checkbox" value="예약취소" id="bkCancel" name="bkState"><label for="bkCancel">예약취소</label>
				<input type="checkbox" value="예약종료" id="bkEnd" name="bkState"><label for="bkEnd">예약종료</label>
			</p>
			<p>시설 구분 : 
				<select id="bkStrId" name="bkStrId">
					<c:forEach items="${strId }" var="str">
						<option value="${str.id }">${str.nameById }</option>
					</c:forEach>
				</select>
			</p>
			<p><input type="submit" value="조회하기"></p>
		</fieldset>
	</form>
	<p id="bkCheckRes"></p>
	<table border="1" id="bkTable">
		<tr>
			<th>접수 날짜</th>	
			<th>시설 명</th>								
			<th>이용 기간</th>
			<th>예약 번호</th>			
			<th>총 금액</th>
			<th>예약 구분</th>			
			<th>취소</th>
		</tr>
		<%-- <c:if test="${empty bList }">
			<tr>
				<td colspan="7">예약 정보가 없습니다.</td>
			</tr>		
		</c:if>
		<c:if test="${!empty bList }">
			<c:forEach var="book" items="${bList }">
				<tr>
					<td><a href="bookcheckdetail.do?bkNo=${book.no }&pageId=check">${book.no }</a></td>
					<td>${book.startDateForm } ~ ${book.endDateForm }</td>
					<!-- 시설명 클릭시 시설정보로 넘어갈 수 있도록 -->
					<td><a href="structure.do?people=4&houseId=${book.str.id }" target="_blank">${book.str.nameById }<br>${book.str.name }</a></td>
					<td>${book.regDateNoTimeForm }</td>
					<td>${book.priceForm }</td>
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
		</c:if> --%>
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