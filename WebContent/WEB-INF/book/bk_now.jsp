<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#bk_now{width:100%;}
	#bk_now #bookTable{width:95%; margin: 0 auto; table-layout:auto;}
	#bk_now #bookTable .sun{color: red;}
	#bk_now #bookTable .sat{color: blue;}
</style>
<script type="text/javascript">
	
	$(function(){	
		/* 달력에 날짜 넣기 */
		
		var date = new Date();
/* 		setMonthTable(date); */		
		setScreen(date); 
		$("#bkTable").html((date.getMonth()+1)+"월 달력<a href='#' class='nextMonth'>&gt;</a>");			
		 
		$(document).on("click", ".nextMonth", function(){			
			var thisMonth = date.getMonth();
			var thisYear = date.getFullYear();
		
			if(thisMonth==11){
				thisYear += 1;
				thisMonth = 0;
				date.setFullYear(thisYear);
				date.setMonth(thisMonth);
			}else{
				date.setMonth(thisMonth+1);
			}
			
			setScreen(date);
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
			}else{
				date.setMonth(thisMonth-1);
			}
			
			setScreen(date);
			$("#bkTable").html((date.getMonth()+1)+"월 달력<a href='#' class='nextMonth'>&gt;</a>");			
		});
		
		$(document).on("click", ".isBooked", function(){
			alert("예약할 수 없습니다.");
			return false;
		});
		
		$(document).on("click", ".noBooked", function(){
			alert("예약가능합니다.");	
			location.href="";
		});
		
	});//ready
	
	function setScreen(date){
		 $.ajax({
				url:"book.do",
				type:"post",
				timeout:30000,
				dataType:"json",
				data:{"date":date.getTime()},//현재 시간
				success:function(data){
					console.log(data);
					setMonthTable(date, data);					
				} 
			}); 
	}
	
	function setMonthTable(date, data){
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
		var names = data[0];
		var bList = data[1];
		var today = new Date();
		
		for(var j=0;j < names.length;j++){
			var index = 0;
			dateForm += "<tr>";
			dateForm += "<th><a href='#'>";
			dateForm += names[j].name;
			console.log("j : "+j+"|"+names);
			dateForm += "</a></th>";
			for(var k=0;k<last[m];k++){	
				//console.log(bList[j][index].startDate);
				if(date.getMonth()==today.getMonth() && (k+1) <= today.getDate()){
					dateForm += "<td><a href='#' class='isBooked'>X</a></td>";
				}else{				
					if(bList[j]!= undefined){	
						if(bList[j][index] != undefined){
							var startDate = new Date(bList[j][index].startDate);
							var endDate = new Date(bList[j][index].endDate);
							
							if((k+1)>=startDate.getDate()||(k+1)<=endDate.getDate()){
								console.log("일치");
								if(bList[j][index].state=="입금완료"){
									dateForm += "<td><a href='#' class='isBooked'>X</a></td>";
								}else if(bList[j][index].state=="입금대기"){
									dateForm += "<td><a href='#' class='isBooked'>△</a></td>";
								}
							}else{
								dateForm += "<td><a href='#' class='noBooked'>O</a></td>";
							}
							index++;
						}else{
							dateForm += "<td><a href='#' class='noBooked'>O</a></td>";
						}						
					}else{ 
						dateForm += "<td><a href='#' class='noBooked'>O</a></td>";
					} 
				}
			}
			dateForm += "</tr>";
		}
		date.setDate(1);
		$("#bookTable").prepend(dateForm);
	}
</script>
<div id="bk_now">
	<h2 id="bkTable"></h2>
	<table id="bookTable" border="1">
		
	</table>
</div>
