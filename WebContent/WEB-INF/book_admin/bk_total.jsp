<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#bk_total .stateReady{color:blue; font-weight: bold;}
	#bk_total .stateProcess{color:green; font-weight: bold;}
	#bk_total .stateCancel{color:red; font-weight: bold;}
	#bk_total .stateEnd{color:gray; font-weight: bold; text-decoration: line-through;}
	#bk_total .bkStrIdName{color:green; font-weight: bold;}
	#bk_total .totalBook{text-align: center;}
	#bk_total .totalPrice{text-align: right;}
</style>
<script>

	$(function(){
		var date = new Date();
		$("#year").val(date.getFullYear());
		$("#month").val((date.getMonth()+1));		
		setScreen();
		
		/* 예약상태와 시설 구분을 선택한 후 조회하기 버튼 클릭 시 ajax통해 table을 다시 구성하도록 만듦 */
		$(document).on("submit","form[name='book1']",function(){
			setScreen();
			return false;
		});
		
	});//ready
	
	function setScreen(){
		var year = $("#year").val();
		var month = $("#month").val();
		var strId = $("#bkStrId").val();

		$.ajax({
			url:"booktotal.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"year":year, "month":month,"strId":strId},
			success:function(data){
				console.log(data);
				$("#totalTitle").html(data[0]+"년 "+data[1]+"월 예약현황");
				$("#bkTotalRes").html("<span class='bkStrIdName'>"+data[3]+"</span>로 검색한 결과입니다.");
				setTable(data);				
			} 
		}); 
	}
	
	function setTable(data){
		$("#bkTable").empty();	
	
		var bList =  data[2];
		var strId = data[3];
		
		var tableForm = "<tr><th>시설 구분</th><th>시설 명</th><th class='stateReady'>입금 대기</th><th class='stateProcess'>입금 완료</th>"
							+"<th class='stateCancel'>예약 취소</th><th class='stateEnd'>예약 종료</th><th>총 계</th></tr>";
		
		if((bList==null||bList==undefined||bList.length==0) || (strId==null||strId==undefined||strId.length==0)){
			tableForm += "<tr><td colspan='7'>예약 정보가 없습니다.</td></tr>";
		}else{
			
			for(var str = 0; str < bList[strId].length; str++){
				tableForm += "<tr>";
				if(str==0){
					tableForm +="<td rowspan='"+(bList[strId].length*2)+"'>"+strId+"</td><td rowspan='2'>"+bList[strId][str][0].name+"</td>";
				}else{
					tableForm +="<td rowspan='2'>"+bList[strId][str][0].name+"</td>";
				}
					
				for(var state = 0; state < bList[strId][str].length; state++){						
					tableForm +="<td class='totalBook'>"+bList[strId][str][state].totalBook+" 건</td>";						
				}	
				tableForm += "</tr>";
				tableForm += "<tr>";
				for(var state = 0; state< bList[strId][str].length; state++){						
					tableForm +="<td class='totalPrice'>"+bList[strId][str][state].totalPriceForm+"</td>";						
				}	
				tableForm += "</tr>";
			}
						
		}		
		$("#bkTable").append(tableForm);
	}
</script>
<c:if test="${!empty user_info }">
	<c:if test="${user_info.isMng==false }">
		<script>
			alert("관리자만 접근 가능합니다.");
			location.href="index.jsp";
		</script>
	</c:if>	
</c:if>
<c:if test="${empty user_info }">
	<script>
		alert("관리자만 접근 가능합니다.");
		location.href="login.do?category=booktotal";
	</script>
</c:if>	
<div id="bk_total">
	<h2 id="totalTitle"></h2>
	<form action="booktotal.do" method="post" name="book1">
		<fieldset>		
			<p>
				시설 구분 : 
				<select id="bkStrId" name="bkStrId">
					<c:forEach items="${strId }" var="str">
						<option value="${str.id }">${str.nameById }</option>
					</c:forEach>
				</select>
				<br>
				이용 기간 :
				<select name="year" id="year">
					<c:forEach items="${years }" var="year">
						<option value="${year }">${year }</option>
					</c:forEach>
				</select> 
				년 
				<select name="month" id="month">
						<c:forEach items="${months }" var="month">
							<option value="${month }">${month }</option>
						</c:forEach>
				</select>
				월
				<input type="submit" value="조회하기">
			</p>
		</fieldset>
	</form>
	
	<p id="bkTotalRes"></p>
	<table border="1" id="bkTable"></table>
</div>