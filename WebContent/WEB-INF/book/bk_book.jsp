<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(function(){
		setDateForm();	
		setPriceForm();
		
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

	});
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
	<form>
		<!-- 예약정보1 - 시설정보 및 기간 설정에 따른 가격 계산 -->
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
					시작날짜 : <input type="date" readonly="readonly" id="start">
					끝날짜 : <input type="date" readonly="readonly" id="end">
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
				<td><input type="text" readonly="readonly" disabled="disabled" required="required"></td>
			</tr>
			<tr>
				<th>연락처</th>
				<td>
					<select>
						<option>010</option>
						<option>011</option>
						<option>019</option>
						<option>017</option>
					</select>
					-
					<input type="text" required="required">
					-
					<input type="text" required="required">					
				</td>
			</tr>
		</table>
		<span>*</span>
		<input>
		<input>
	</form>
	<a href="book.do">[돌아가기]</a>
</div>