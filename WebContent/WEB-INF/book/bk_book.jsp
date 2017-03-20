<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(function(){
		/*고객 휴대폰 번호 입력해 놓는 구문 필요*/
		
		setDateForm();	
		setPriceForm();
		
		$.ajax({
			url:"bookcheckdate.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"startDate":$("#start").val(),
					"endDate" : $("#end").val(),
					"strNo" : ${str.no }
			},
			success:function(data){
				if(data==false){
					location.href="book.do";
				}							
			} 
		});
		
		$(document).on("change","#period",function() {	
			setDateForm();	
			setPriceForm();					
		});//기간을 변경할 때
		
		$("#btnDate").click(function(){
			$.ajax({
				url:"bookcheckdate.do",
				type:"post",
				timeout:30000,
				dataType:"json",
				data:{"startDate":$("#start").val(),
						"endDate" : $("#end").val(),
						"strNo" : ${str.no }
				},
				success:function(data){
					if(data==false){
						alert("예약할 수 없는 날짜입니다.");
						$("#period").val("1");	
						setDateForm();
						setPriceForm();
					}else{
						alert("예약가능합니다.");
					}							
				} 
			});			 
			 return false;
		});

		
		$("#bookProcess").submit(function(){
			if(!confirm("선택하신 기간으로 예약 하시겠습니까?")){
				return false;
			}
		});
		
		$("#btnBack").click(function(){
			location.href="book.do";
		});
	});//ready
	function setPriceForm(){
		$("#totalPrice").empty();
		var price = Number(${str.price});
		var temp = new Date(${startDate});
		var total = 0;
		for(i=0;i<Number($("#period").val());i++){
			if(temp.getMonth()==6||temp.getMonth()==7||temp.getDay()==5||temp.getDay()==6){
				total += Number(price);
			}else{
				total +=  Number(price*0.7);
			}
			temp.setDate(temp.getDate()+1);
		}
		$("#totalPrice").html(total+"원");
	}
	
	function setDateForm(){		
		var date = new Date(${startDate});
		
		var val = Number($("#period").val());
		var startForm = convertToString(date.getTime());
		$("#start").val(startForm);
		
		date.setDate(date.getDate()+val);
		var endForm = convertToString(date.getTime());
		$("#end").val(endForm);
	}
	
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
</script>
<div id="bk_book">
	<!-- 예약폼이 들어갈 예정입니다. -->
	<form action="bookprocess.do" method="post" id=bookProcess">
		<!-- 예약정보1 - 시설정보 및 기간 설정에 따른 가격 계산 -->
		<input type="hidden" value="${str.no }" name="strNo">
		<input type="hidden" value="${myinfo.my_no }" name="memNo">
		
		<h4>예약 객실</h4>
		<table border="1">
			<tr>
				<th>객실명</th>
				<td>${str.name }</td>
			</tr>
			<tr>
				<th>규모</th>
				<td>(${str.people}인실)</td>
			</tr>
			<tr>
				<th>객실 요금</th>
				<td>[성수기] : ${str.price }원/1박   [비수기] : ${str.price*0.7 }원/1박</td>
			</tr>
			<tr>
				<th>이용 기간</th>
				<td>
					시작날짜 : <input type="date" readonly="readonly" id="start" name="start">
					끝날짜 : <input type="date" readonly="readonly" id="end" name="end">
					<select id="period">
						<option value="1">1박2일</option>
						<option value="2">2박3일</option>
						<option value="3">3박4일</option>
					</select>
					<button id="btnDate">조회하기</button> 
				</td>
			</tr>
			<tr>
				<th>전체 이용 요금</th>
				<td>
					<span id="totalPrice"></span>
				</td>
			</tr>
		</table>
		<!-- 고객정보  -->
		<h4>고객 정보</h4>
		
		<table border="1">
			<tr>
				<th>예약자명</th>
				<td><input type="text" readonly="readonly" disabled="disabled" required="required" value="${myinfo.my_name }"></td>
			</tr>
			<tr>
				<th>연락처</th>
				<td>
					<select name="bkTel1">
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="019">019</option>
						<option value="017">017</option>
					</select>
					-
					<input type="text" required="required" name="bkTel2">
					-
					<input type="text" required="required" name="bkTel3">					
				</td>
				<tr>
				<th>메일주소</th>
				<td>
					<input type="text" readonly="readonly" value="${myinfo.my_mail }">					
				</td>
				<tr>
			</tr>
		</table>
		<input type="submit" value="예약하기">
		<button type="button" id="btnBack">취소하기</button> 
	</form>
	<a href="book.do">[돌아가기]</a>
</div>