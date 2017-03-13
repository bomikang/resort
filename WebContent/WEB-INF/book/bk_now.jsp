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
		var date = new Date();
		 
		setMonthTable(date);		
		$("#bkTable").html((date.getMonth()+1)+"월 달력<a href='#' id='nextMonth'>&gt;</a>");		
		
		
		$(document).on("click", "#nextMonth", function(){
			
			var m = date.getMonth();
			var y = date.getFullYear();
		
			if(m==11){
				y += 1;
				m = 0;
				date.setFullYear(y);
				date.setMonth(m);
				setMonthTable(date);
			}else{
				m += 1;
				date.setMonth(m)
				setMonthTable(date);
			}
			$("#bkTable").html("<a href='#' id='prevMonth'>&lt;</a>"+(date.getMonth()+1)+"월 달력");
			
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
		
		for(var i=0;i<last[m]-1;i++){
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
		$("#bookTable").append(dateForm);
	}
</script>
<div id="bk_now">
	<h2 id="bkTable"></h2>
	<table id="bookTable">
	
	</table>
</div>
