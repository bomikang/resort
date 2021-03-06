<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#bk_check .stateReady{color:blue; font-weight: bold;}
	#bk_check .stateProcess{color:green; font-weight: bold;}
	#bk_check .stateCancel{color:red; font-weight: bold;}
	#bk_check .stateEnd{color:gray; font-weight: bold; text-decoration: line-through;}
	#bk_check .bkStrIdName{color:#cc0000; font-weight: bold;}
	#bk_check_field p{width:500px; margin:15px 0;}
	#page_index{text-align: center;}
	#bkTable th:NTH-CHILD(1) {width:100px;}
	#bkTable th:NTH-CHILD(2) {width:100px;}
	#bkTable th:NTH-CHILD(3) {width:250px;}
	#bkTable th:NTH-CHILD(4) {width:250px;}
	#bkTable th:NTH-CHILD(5) {width:100px;}
	#bkTable th:NTH-CHILD(6) {width:100px;}
</style>
<script>

	$(function(){
		var date = new Date();
		$("#year").val(date.getFullYear());
		$("#month").val((date.getMonth()+1));
		
		$("#withCon").prop("checked",true);
		setScreen();
		setFormTagAbled();
		
		/* Radio Button */
		$("#all").click(function(){
			console.log("전체내역");
			setFormTagDisabled();			
		});
		
		$("#withCon").click(function(){
			setFormTagAbled();
		});
		
		//setTable(${bList});
		$(document).on("submit", ".bkcancel", function(){
			var bkNo = $(this).find("input[name='bkNo']").val();
		
			if(!confirm("예약을 취소하시겠습니까?")){
				return false;
			}else{
				$.ajax({
					url:"bookcancel.do",
					type:"post",
					timeout:30000,
					dataType:"json",
					data:{"bkNo":bkNo},
					asnc : false,
					success:function(data){
						console.log(data);
						if(data=="true"||data==true){
							alert("정상적으로 취소되었습니다.");
							setScreen();
						}else{
							alert("오류가 발생되어 취소되지 못했습니다. 잠시후 다시 시도해 주세요.");
						}
					} 
				}); 
			}
			return false;
		});
		
		/* 예약상태와 시설 구분을 선택한 후 조회하기 버튼 클릭 시 ajax통해 table을 다시 구성하도록 만듦 */
		$(document).on("submit","form[name='book1']",function(){
			setScreen();
			return false;
		});
		
		$(document).on("click",".pageIndex", function(){
			var index = $(this).attr("index");
			$("#pageIndex").val(index);
			setScreen();
			return false;
		});
	});//ready
	function setPageIndex(data){
		/* Page 하단에 index 표시(10개 단위로 끊어 표시 ) */
		var index = data[2];
		if(index != null){
			var indexForm = "<a class='pageIndex paging_btn' href='#' index='1' title='첫 페이지'><img src='image/paging_left2.png'/></a>";
			if(index.start > 10 ){
				indexForm += "<a class='pageIndex paging_btn' href='#' index='"+(index.start-10)+"' title='이전 10페이지'><img src='image/paging_left1.png'/></a>";
			}else{
				indexForm += "<a class='paging_btn'><img src='image/paging_left1.png'/></a>";
			}
			
			for(var i = index.start; i <= index.end; i++){
				if(i==1){
					if(i == index.nowIndex){
						indexForm += "<b><a class='paging_btn_num'>"+i+"</a></b>";
					}else{
						indexForm += "<a class='pageIndex paging_btn_num' href='#' index='"+i+"'>"+i+"</a>";	
					}
				}else if(i>1){
					if(i == index.nowIndex){
						indexForm += " | <b><a class='paging_btn_num'>"+i+"</a></b>";
					}else{
						indexForm += " | <a class='pageIndex paging_btn_num' href='#' index='"+i+"'>"+i+"</a>";
					}
				}
			}
			
			if(index.end < index.maxIndex){
				indexForm += "<a class='pageIndex paging_btn' href='#' index='"+(index.start+10)+"' title='다음 10페이지'><img src='image/paging_right1.png'/></a>";
			}else{
				indexForm += "<a class='paging_btn'><img src='image/paging_right1.png'/></a>";
			}
			
			indexForm += "<a class='pageIndex paging_btn' href='#' index='"+index.maxIndex+"' title='마지막 페이지'><img src='image/paging_right2.png'/></a>";
			$("#page_index").html(indexForm);
		}
	}
	
	function setScreen(){
		var sList = new Array();
		$("input[name='cdState']").each(function(i, obj) {
			if($(obj).prop("checked")==true){
				sList.push($(obj).val());
				console.log($(obj).val());
			}	
		});
		var bkState = sList.join(",");
		console.log(bkState);	
		var strId = $("#bkStrId").val();
		console.log(strId);
		var year = $("#year").val();
		var month = $("#month").val();
		var condition="";
		$("input[name='condition']").each(function(i, obj) {
			if($(obj).prop("checked")==true){
				condition=$(obj).attr("id");
			}			
		});
		/* 페이지 인덱스 */
		var index = $("#pageIndex").val();
		if(index==null || index==undefined){
			index = "1";
		}
		console.log(condition);
		$.ajax({
			url:"bookcheck.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"cdState":bkState,"strId":strId, "year":year, "month":month, "condition":condition,"index":index},
			success:function(data){
				console.log(data);
				$("#bkCheckRes").html("<span class='bkStrIdName'>"+data[0]+"</span>로 검색한 결과입니다.");
				setTable(data);		
				setPageIndex(data);
			} 
		}); 
	}
	
	function setTable(data){
		$("#bkTable").empty();	
	
		var bList =  data[1];
	
		var tableForm = "<tr><th>접수 날짜</th><th>시설 명</th><th>이용 기간</th><th>예약 번호</th><th>총 금액</th><th>예약 구분</th><th>취소</th></tr>";
		
		if(bList==null||bList==undefined||bList.length==0){
			tableForm += "<tr><td colspan='7'>예약 정보가 없습니다.</td></tr>";
		}else{
			for(var j=0;j<bList.length;j++){
				var strUrl = "structure.do?people=4&houseId="+bList[j].str.id;
				var bkDetailUrl = "bookcheckdetail.do?bkNo="+bList[j].no+"&pageId=check";
				tableForm += "<tr>";
				tableForm += "<th>"+bList[j].regDateNoTimeForm+"</th>";//접수 날짜
				tableForm += "<td><a href='"+strUrl+"' target='_blank' title='"+bList[j].str.nameById+" "+bList[j].str.name+" 정보' class='toStr'>"+bList[j].str.nameById+"<br>"+bList[j].str.name+"</a></td>";//시설명 - 시설보기로 hyperlink				
				tableForm += "<td><a href='"+bkDetailUrl+"' title='예약번호 : "+bList[j].no+" 내역' class='toDetail'>"+bList[j].startDateForm+" ~ "+bList[j].endDateForm+"</a></td>";//예약기간
					<!-- 시설명 클릭시 시설정보로 넘어갈 수 있도록 -->
				
				tableForm += "<td><a href='"+bkDetailUrl+"' title='예약번호 : "+bList[j].no+" 내역' class='toDetail'>"+bList[j].no+"</a></td>";//예약번호
				tableForm += "<td><a href='"+bkDetailUrl+"' title='예약번호 : "+bList[j].no+" 내역' class='toDetail'>"+bList[j].priceForm +"</a></td>"//총가격
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
				if(bList[j].state == '입금대기'){
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
	
	function setFormTagAbled(){
		$("#year").removeProp("disabled");
		$("#month").removeProp("disabled");
		$("input[name='cdState']").each(function(i, obj) {
			$(obj).removeProp("disabled");
		});
		$("#bkStrId").removeProp("disabled");
	}
	function setFormTagDisabled(){
		$("#year").prop("disabled","disabled");
		$("#month").prop("disabled","disabled");
		$("input[name='cdState']").each(function(i, obj) {
			$(obj).prop("disabled","disabled");
		});
		$("#bkStrId").prop("disabled", "disabled");
	}
</script>
<c:if test="${empty user_info }">
	<script>
		alert("로그인이 필요한 페이지 입니다.");
		location.href="login.do?category=bookcheck";
	</script>
</c:if>	
<div class="way_top">
	<h3>예약조회 및 취소<br /><span>홈 > 예약안내 > 예약조회 / 취소</span></h3>
</div>
<div id="bk_check" class='intro_padding'>
	<form action="bookcheck.do" method="post" name="book1">
		<fieldset id="bk_check_field">
			<input type="hidden" name="index" id="pageIndex">
			<p>
				<label for="">조회 기준 : </label> 
				<input type="radio" name="condition" id="all"><span  class='small_label'>전체 내역 보기</span>
				<input type="radio" name="condition" id="withCon"><span  class='small_label'>조건별 검색 </span>
			</p>		
			<p>
				<label for="">이용 기간 : </label>
				<select name="year" id="year">
					<c:forEach items="${years }" var="year">
						<option value="${year }">${year }</option>
					</c:forEach>
				</select> 
				<span class='small_label'>년</span> 
				<select name="month" id="month">
						<c:forEach items="${months }" var="month">
							<option value="${month }">${month }</option>
						</c:forEach>
				</select>
				<span class='small_label'>월</span> 
			</p>
			<p>
				<label for="">예약 상태 : </label>
				<input type="checkbox" value="입금대기" id="bkReady" name="cdState" checked="checked"><label for="bkReady" class='small_label'>입금대기</label>
				<input type="checkbox" value="입금완료" id="bkProcess" name="cdState" checked="checked"><label for="bkRbkProcesseady" class='small_label'>입금완료</label>
				<input type="checkbox" value="예약취소" id="bkCancel" name="cdState"><label for="bkCancel" class='small_label'>예약취소</label>
				<input type="checkbox" value="예약종료" id="bkEnd" name="cdState"><label for="bkEnd" class='small_label'>예약종료</label>
			</p>
			<p>
				<label for="">시설 구분 : </label> 
				<select id="bkStrId" name="bkStrId">
					<option value="0">전체 보기</option>
					<c:forEach items="${strId }" var="str">
						<option value="${str.id }">${str.nameById }</option>
					</c:forEach>
				</select>
				<input type="submit" value="조회하기">
			</p>
			
		</fieldset>
	</form>
	<%-- <form action="bookcheck.do" method="post" name="book1">
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
	</form> --%>
	<p id="bkCheckRes"></p>
	<table id="bkTable">
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
	</table>
	<p id="page_index"></p>
</div>