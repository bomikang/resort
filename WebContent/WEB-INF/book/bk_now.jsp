<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#bk_now{width:100%;}
	#bk_now #bookTable{width:95%; margin: 0 auto;}
	#bk_now #bookTable .sun{color: red;}
	#bk_now #bookTable .sat{color: blue;}
</style>
<script type="text/javascript">
	
	$(function(){	
		/* 달력에 날짜 넣기 */
		var date = new Date();		
		setMonthTable(date);		
		$("#bkTable").html((date.getMonth()+1)+"월 달력<a href='#' class='nextMonth'>&gt;</a>");		
		$.ajax({
			url:"book.do",
			type:"get",
			timeout:30000,
			dataType:"json",
			data:{"date":date},//게시글의 번호
			success:function(data){
				console.log(data);
				if(data=="ok"){
					alert("사용가능 아이디 입니다.");			
				}else{
					alert("사용불가 아이디 입니다.");
				}	
			} 
		});
		 
		$(document).on("click", ".nextMonth", function(){
			var thisMonth = date.getMonth();
			var thisYear = date.getFullYear();
		
			if(thisMonth==11){
				thisYear += 1;
				thisMonth = 0;
				date.setFullYear(thisYear);
				date.setMonth(thisMonth);
				setMonthTable(date);
			}else{
				date.setMonth(thisMonth+1);
				setMonthTable(date);
			}
			
			$("#bkTable").html("<a href='#' class='prevMonth'>&lt;</a>"+(date.getMonth()+1)+"월 달력");
			
		});

		
		$(document).on("click", ".prevMonth", function(){
			var thisMonth = date.getMonth();
			var thisYear = date.getFullYear();
		
			if(thisMonth==0){
				thisYear -= 1;
				thisMonth = 11;
				date.setFullYear(thisYear);
				date.setMonth(thisMonth);
				setMonthTable(date);
			}else{
				date.setMonth(thisMonth-1);
				setMonthTable(date);
			}
			
			$("#bkTable").html((date.getMonth()+1)+"월 달력<a href='#' class='nextMonth'>&gt;</a>");			
		});
	});//ready
	
	function setMonthTable(date){
		$("#bookTable").empty();
		var y = date.getFullYear();
		var m = date.getMonth();
		
		var last = [31,28,31,30,31,30,31,31,30,31,30,31];
		
		if(y%4&&y%100!=0||y%400==0){
			last[1]=29;
		}
		
		var dateForm = "<tr><th></th>";
		
		for(var i=0;i<last[m];i++){
			date.setDate(i+1);
			if(date.getDay()== 0){
				dateForm += "<th class='sun'>"+(i+1)+"</th>";
			}else if(date.getDay()==6){
				dateForm += "<th class='sat'>"+(i+1)+"</th>";
			}else{
				dateForm += "<th>"+(i+1)+"</th>";	
			}			
		}	
		dateForm += "</tr>";
		date.setDate(1);
		$("#bookTable").append(dateForm);
	}
</script>
<div id="bk_now">
	<h2 id="bkTable"></h2>
	<table id="bookTable">
		
	</table>
</div>
