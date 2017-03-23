<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#bk_list #bkStrNo{opacity: 0;}
</style>
<script>
	var stateList = new Array();
	$(function(){
		var date = new Date();
		
		$("#startDate").val(convertToString(date));
		$("#endDate").val(convertToString(date));
		
		$("#withCon").prop("checked", true);
		setScreen();		
		
		/* Radio Button */
		$("#all").click(function(){
			console.log("전체내역");
			setFormTagDisabled();			
		});
		
		$("#withCon").click(function(){
			setFormTagAbled();
		});
		
		
		$(document).on("click",".btnstate", function(){
			var $tr = $(this).parents("tr")
			var bkNo = $tr.find(".bkNo").text();
			var state = $(this).parent("td").find(".bkState").val();
			console.log(state);
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
		
		/* 예약상태와 시설 구분을 선택한 후 조회하기 버튼 클릭 시 ajax통해 table을 다시 구성하도록 만듦 */
		$(document).on("submit","form[name='book1']",function(){
			setScreen();
			return false;
		});
		
		$("#bkStrId").change(function(){
			if($("#bkStrId").val() == "0"){
				$("#bkStrNo").val("0");
				$("#bkStrNo").css("opacity", "0");
			}else if($("#bkStrId").val() != "0"){
				setBkStrNo();
				$("#bkStrNo").val("0");
				$("#bkStrNo").css("opacity", "1");
			}
		});
		/* 예약자 이름 클릭 시 */
		$(document).on("click",".searchMem", function(){
			var bkMem = $(this).attr("value");
			console.log("bkMem : "+bkMem);
			$.ajax({
				url:"booklist.do",
				type:"post",
				timeout:30000,
				dataType:"json",
				data:{"type":"mem","bkMem":bkMem},
				success:function(data){
					console.log(data);
					setTable(data);					
				} 
			});
			return false;
		});
	});//ready
	
	function setBkStrNo(){
		var strId = $("#bkStrId").val();
		
		$.ajax({
			url:"booklist.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"type":"strList","strId":strId},
			success:function(data){
				console.log(data);
				$("#bkStrNo").empty();
				$("#bkStrNo").append("<option value='0'>전체보기</option>");
				var strList = data[0];
				for(var i=0;i<strList.length;i++){
					var optionForm = "<option value='"+strList[i].no+"'>"+strList[i].name+"</option>"
					$("#bkStrNo").append(optionForm);
				}
			} 
		}); 
	}
	/*date 객체의 getTime()으로 가져온 시간 값을 다시 String형태의 date값으로 변환 */
	function convertToString(date){
		var temp = new Date(date);
		if(temp.getMonth()<9){
			if(temp.getDate()<9){
				return temp.getFullYear()+"-0"+(temp.getMonth()+1)+"-0"+temp.getDate();
			}else{
				return temp.getFullYear()+"-0"+(temp.getMonth()+1)+"-"+temp.getDate();
			}
		}else{
			if(temp.getDate()<9){
				return temp.getFullYear()+"-"+(temp.getMonth()+1)+"-0"+temp.getDate();
			}else{
				return temp.getFullYear()+"-"+(temp.getMonth()+1)+"-"+temp.getDate();
			}			
		}
	}
	
	function setFormTagAbled(){
		$("#startDate").removeProp("disabled");
		$("#endDate").removeProp("disabled");
		$("input[name='cdState']").each(function(i, obj) {
			$(obj).removeProp("disabled");
		});
		$("#bkStrId").removeProp("disabled");
	}
	
	function setFormTagDisabled(){
		$("#startDate").prop("disabled","disabled");
		$("#endDate").prop("disabled","disabled");
		$("input[name='cdState']").each(function(i, obj) {
			$(obj).prop("disabled","disabled");
		});
		$("#bkStrId").prop("disabled", "disabled");
	}
	
	function setScreen(){
		/* 예약상태  */
		var sList = new Array();
		$("input[name='cdState']").each(function(i, obj) {
			if($(obj).prop("checked")==true){
				sList.push($(obj).val());
				console.log($(obj).val());
			}	
		});
		var bkState = sList.join(",");
		console.log(bkState);	
		/* 시설 ID */
		var strId = $("#bkStrId").val();
		console.log(strId);
		var strNo = $("#bkStrNo").val();
		console.log(strNo);
		/* 기간 */
		var start = $("#startDate").val();
		var end = $("#endDate").val();
		/* 전체선택/조건별검색 */
		var condition="";
		$("input[name='condition']").each(function(i, obj) {
			if($(obj).prop("checked")==true){
				condition=$(obj).attr("id");
			}			
		});
		
		$.ajax({
			url:"booklist.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"type":"setTable","cdState":bkState,"strId":strId,"strNo":strNo, "start":start, "end":end, "condition":condition},
			success:function(data){
				console.log(data);
				setTable(data);					
			} 
		}); 
	}//화면구성함수
	
	function setTable(data){
		stateList = new Array();
		var totalPrice = 0;
		$("#bkTable").empty();	
	
		var bList =  data[1];
	
		var tableForm = "<tr><th>예약 번호</th><th>시설 명</th><th>예약자</th><th>입실날짜</th><th>퇴실날짜</th><th>총 금액</th><th>예약 구분</th><th>취소 날짜</th></tr>";
		
		if(bList==null||bList==undefined||bList.length==0){
			tableForm += "<tr><td colspan='9'>조회할 정보가 없습니다.</td></tr>";
		}else{
			for(var j=0;j<bList.length;j++){	
				totalPrice += Number(bList[j].price);
				stateList.push(bList[j].state);
				tableForm += "<tr>";
				tableForm += "<th class='bkNo'>"+bList[j].no+"</th>";//예약번호
				tableForm += "<td>"+bList[j].str.nameById+"<br>"+bList[j].str.name+"</td>";//시설명 			
				tableForm += "<td><a href='#' value='"+bList[j].mem.no+"' class='searchMem'>"+bList[j].mem.name+"<br>("+bList[j].tel+")</a></td>";//예약자명 	
				tableForm += "<td>"+bList[j].startDateForm+"</td>";//시작날짜
				tableForm += "<td>"+bList[j].endDateForm+"</td>";//끝날짜				
				tableForm += "<td>"+bList[j].priceForm +"</td>"//총가격
				tableForm += "<td class='state'><select class='bkState' name='state'><option value = '입금대기'>입금대기</option>";
				tableForm += "<option value = '입금완료'>입금완료</option><option value = '예약취소'>예약취소</option><option value = '예약종료'>예약종료</option>";
				tableForm += "</select><input type='hidden' class='index'><button class='btnstate'>수정</button><button class='btnreset'>취소</button></td>";
				
				
				tableForm += "<td class='bkCancel'>"+bList[j].cancelForm +"</td></tr>";
			}
			
				tableForm += "<tr><th>총 금액</th><th></th><th></th><th></th><th></th><th></th><th>"+number_format(totalPrice)+" 원</th><th></th><th></th></tr>";
			
		}		
		$("#bkTable").append(tableForm);
		setState(stateList);
	}//table 구성 함수 
	
	function setState(stateList){		
		$(".bkState").each(function(i, obj) {
			$(obj).val(stateList[i]);
			$(obj).next(".index").val(i);
		});
	}
	/* 인터넷 블로그에서 발췌한 함수(javaScript 내 Format화 된 String 생성)*/
	function number_format( number ){	
	  var nArr = String(number).split('').join(',').split('');//모든 숫자를 쪼개어 사이사이에 ","삽입
	  for( var i=nArr.length-1, j=1; i>=0; i--, j++){
		  if( j%6 != 0 && j%2 == 0) nArr[i] = '';
		  //쉼표가 들어가는 자리(2의 배수==(j%2==0)) && 쉼표가 있어야할 자리 외의 숫자는 공백처리하여 format된 것 처럼 만듦
	  }
	  return nArr.join('');
	}
</script>
<div id="bk_list">
	<h4>전체 예약 목록</h4>
	<!-- 기간별(시작날짜|끝날짜가 그 달일 때), 상태별, 시설별로 관리자가 조회 할 수 있도록 bk_check참조하여 만들기 -->
	<form name="book1" action="booklist.do" method="post">
		<fieldset>
			<p>
				조회 기준 : 
				<input type="radio" name="condition" id="all">전체 내역 보기
				<input type="radio" name="condition" id="withCon">조건별 검색 
			</p>		
			<p>
				이용 기간 :
				<input type="date" required="required" name="startDate" id="startDate">
				~
				<input type="date" required="required" name="endDate" id="endDate">
			</p>
			<p>
				예약 상태 : 
				<input type="checkbox" value="입금대기" id="bkReady" name="cdState" checked="checked"><label for="bkReady">입금대기</label>
				<input type="checkbox" value="입금완료" id="bkProcess" name="cdState" checked="checked"><label for="bkRbkProcesseady">입금완료</label>
				<input type="checkbox" value="예약취소" id="bkCancel" name="cdState"><label for="bkCancel">예약취소</label>
				<input type="checkbox" value="예약종료" id="bkEnd" name="cdState"><label for="bkEnd">예약종료</label>
			</p>
			<p>시설 구분 : 
				<select id="bkStrId" name="bkStrId">
					<option value="0">전체</option>
					<c:forEach items="${strId }" var="str">
						<option value="${str.id }">${str.nameById }</option>
					</c:forEach>
				</select>
				<select id="bkStrNo" name="bkStrNo">
					<option value="0">전체</option>
				</select>
			</p>
			<p><input type="submit" value="조회하기"></p>
		</fieldset>
	</form>
	<br>
	<table border="1" id="bkTable"></table>

	<%-- <c:choose>
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
	</c:choose>	 --%>
</div>