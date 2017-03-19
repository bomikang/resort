<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
/*캐시에 Data를 남기지 않는구문(로그아웃 이후 뒤로가기 Data기록 안남기기 위해 사용)  */
response.setHeader("cache-control","no-store");
response.setHeader("expires","0");
response.setHeader("pragma","no-cache");
%>

<style>
	#bk_now{width:100%;}
	#bk_now #bookTable{width:95%; margin: 0 auto; }
	#bk_now #bookTable table{table-layout:auto;width:100%;}
	#bk_now #bookTable .sun{color: red;}
	#bk_now #bookTable .sat{color: blue;}
	#bk_now #server{text-align: right;color: blue;}
	#bk_now #server #serverTime{width: 110px; float: right;}
</style>
<script type="text/javascript">
	
	$(function(){	

		/* 서버시간 알리미용 임시 테스트 */
		var st = srvTime();
		var now = new Date(st);
		console.log("서버시간 : "+now.getTime());
		
		setInterval(function() {			
			$("#serverTime").html(now.getHours()+":"+now.getMinutes()+":"+now.getSeconds());
			now.setSeconds(now.getSeconds()+1);
		}, 1000);
		

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
			<c:if test="${empty myinfo }">
				alert("로그인이 필요한 페이지 입니다.");
				location.href="login.do";
			</c:if>	
			<c:if test="${!empty myinfo }">
			//alert("예약가능합니다.");	
			var strNo = $(this).find(".strNo").val();
			var date = $(this).find(".date").val();
			var url="bookprocess.do?strNo="+strNo+"&date="+date;
			location.href=url;
			</c:if>
		});
		
		
		
	});//ready
	var xmlHttp;

	function srvTime(){	
		if (window.XMLHttpRequest) {//분기하지 않으면 IE에서만 작동된다.
	
			xmlHttp = new XMLHttpRequest(); // IE 7.0 이상, 크롬, 파이어폭스 등
		
			xmlHttp.open('HEAD',window.location.href.toString(),false);
		
			xmlHttp.setRequestHeader("Content-Type", "text/html");
		
			xmlHttp.send('');
		
			return xmlHttp.getResponseHeader("Date");
	
		}else if (window.ActiveXObject) {
	
			xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
		
			xmlHttp.open('HEAD',window.location.href.toString(),false);
		
			xmlHttp.setRequestHeader("Content-Type", "text/html");
		
			xmlHttp.send('');
		
			return xmlHttp.getResponseHeader("Date");
	
		}

	}
	
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
	}// end of setScreen
	
	function setMonthTable(date, data){
		$("#bookTable").empty();
		var y = date.getFullYear();
		var m = date.getMonth();
		
		var last = [31,28,31,30,31,30,31,31,30,31,30,31];
		
		if(y%4&&y%100!=0||y%400==0){
			last[1]=29;
		}
		
		var dateForm = "<table border='1'><tr><th></th>";
		
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
			date.setMonth(m);
			for(var k=0;k<last[m];k++){	
				date.setDate(k+1);
				//console.log(bList[j][index].startDate);
				if(date.getMonth()==today.getMonth() && (k+1) <= today.getDate()){
					//이번 달 오늘 날짜까지는 예약이 완료 된 것으로 표시하기 위해 
					dateForm += "<td><a href='#' class='isBooked'>X</a></td>";
				}else{	
					//다음달 혹은 오늘 이후날짜
					if(bList[j]!= undefined){
						//시설에 대한 예약 내역이 존재할 때
						if(bList[j][index] != undefined){//시작날짜를 기준으로 정렬 된 예약내역							
							var startDate = new Date(bList[j][index].startDate);
							var endDate = new Date(bList[j][index].endDate);
							console.log(startDate.getDate()+","+endDate.getDate());
							
							if(date.getTime()>=startDate.getTime()&&date.getTime()<endDate.getTime()){
								//index번째 예약 내역의 시작날짜와 끝 날짜 사이에 있을 때
								console.log("일치");
								if(bList[j][index].state=="입금완료"){
									dateForm += "<td><a href='#' class='isBooked'>X</a></td>";
								}else if(bList[j][index].state=="입금대기"){
									dateForm += "<td><a href='#' class='isBooked'>△</a></td>";
								}
								
							}else{
								dateForm += "<td><a href='#' class='noBooked'><input type='hidden' class='strNo' value='"+names[j].no+"'><input type='hidden' class='date' value='"+date.getTime()+"'>O</a></td>";
							}
							
							if(date.getTime()>=(endDate.getTime()-(24*60*60*1000))){
								
								index++;
							}
						}else{ 
							dateForm += "<td><a href='#' class='noBooked'><input type='hidden' class='strNo' value='"+names[j].no+"'><input type='hidden' class='date' value='"+date.getTime()+"'>O</a></td>";
						} 						
					}else{
						//시설에 대한 예약내역이 존재하지 않을 때
						dateForm += "<td><a href='#' class='noBooked'><input type='hidden' class='strNo' value='"+names[j].no+"'><input type='hidden' class='date' value='"+date.getTime()+"'>O</a></td>";
					}
				}
			}
			dateForm += "</tr>";
		}
		dateForm += "</table>";
		date.setDate(1);
		$("#bookTable").prepend(dateForm);
	}//end of setMonthTable
</script>
<div id="bk_now">
	<c:if test="${noStr == true }">
		<script type="text/javascript">
			alert("시설을 조회하는데 문제가 발생하였습니다.");
		</script>
	</c:if>
	<h2 id="server">[서버시간]<span id="serverTime">00:00:00</span></h2>
	<h2 id="bkTable"></h2>
	<div id="bookTable" >
		
	</div>
</div>
