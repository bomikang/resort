<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
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
		console.log($("#bkStrId").val());
		/* 서버시간 알리미용 임시 테스트 */
		$("#bkStrId").val("${id}");
		var st = srvTime();
		var now = new Date(st);
		console.log("서버시간 : "+now.getTime());
		
		setInterval(function() {			
			$("#serverTime").html(now.getHours()+":"+now.getMinutes()+":"+now.getSeconds());
			now.setSeconds(now.getSeconds()+1);
		}, 1000);		
		popUp("bookNotice.jsp", "주의사항", 900, 700);
		/* 달력에 날짜 넣기 */		
		var date = new Date();
/* 		setMonthTable(date); */		
		setScreen(date); 
		$("#bkTable").html((date.getMonth()+1)+"월 달력<a href='#' class='nextMonth'>&gt;</a>");			
		
		/** combo box 내에서 선택된 아이템이 변할 때 마다 그에 맞는 화면으로 전환하도록 함(필요시 버튼삽입 필요)*/
		$(document).on("change", "#bkStrId", function(){
			setScreen(date);
		});
		
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
			return false;
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
			return false;
		});
		
		$(document).on("click", ".isBooked", function(){
			alert("예약할 수 없습니다.");
			return false;
		});
		
		$(document).on("click", ".noBooked", function(){		
			<c:if test="${empty user_info }">
				alert("로그인이 필요한 페이지 입니다.");
				location.href="login.do";
			</c:if>	
			<c:if test="${!empty user_info }">
				<c:if test="${user_info.isMng==true }">
					alert("관리자는 예약할 수 없습니다.");
					return false;
				</c:if>	
				//alert("예약가능합니다.");	
				var strNo = $(this).find(".strNo").val();
				var date = $(this).find(".date").val();
				var url="bookprocess.do?strNo="+strNo+"&date="+date;
				location.href=url;
			</c:if>
		});
		
		$(document).on("click",".toStr",function(){
			var url = $(this).attr("href");
			console.log(url);
			window.open()
			return false;
		});
		
	});//ready
	function popUp(url, name, width, height){
		var x = (screen.availWidth-width)/2;
		var y = (screen.availHeight-height)/2;
		
		window.open(url, name,  'width='+width+',height='+height+', left='+x+', top='+y+'');
	}
	
	var xmlHttp;
	/* 서버 시간을 표기하기 위한 메소드(인터넷 참조) */
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
				data:{"date":date.getTime(),
					"strId":$("#bkStrId").val()},//현재 시간
				
				success:function(data){
					console.log(data);
					setMonthTable(date, data);		
					$("#bkStr").html($("#bkStrId").find("option[selected='true']").text());
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
		var today = new Date();
		
		for(var j=0;j < names.length;j++){
			var bList = data[1][names[j].name];
			var index = 0;
			dateForm += "<tr>";
			var url="structure.do?people=4&houseId="+names[j].id;
			dateForm += "<th><a href='"+url+"' target='_blank'>";
			dateForm += names[j].name;
			console.log("j : "+j+"|"+names);
			dateForm += "</a></th>";
			date.setMonth(m);
			for(var k=0;k<last[m];k++){	
				date.setDate(k+1);				
				if(bList != undefined){
					if(date.getMonth()==today.getMonth() && (k+1) <= today.getDate()){
						//이번 달 오늘 날짜까지는 예약이 완료 된 것으로 표시하기 위해 
						dateForm += "<td><a href='#' class='isBooked'>★</a></td>";
					}else{
						//시설에 대한 예약 내역이 존재할 때
						if((k+1)==bList[index].date){
							console.log("일치");
							if(bList[index].state=="입금완료"){
								dateForm += "<td><a href='#' class='isBooked'>X</a></td>";
							}else if(bList[index].state=="입금대기"){
								dateForm += "<td><a href='#' class='isBooked'>△</a></td>";
							}
						}else{
							dateForm += "<td><a href='#' class='noBooked'><input type='hidden' class='strNo' value='"+names[j].no+"'><input type='hidden' class='date' value='"+date.getTime()+"'>O</a></td>";
						}
					}
					if((bList[index+1] != undefined) && (k+1)==bList[index].date){
						index++;
					}
				}else{
					if(date.getMonth()==today.getMonth() && (k+1) <= today.getDate()){
						//이번 달 오늘 날짜까지는 예약이 완료 된 것으로 표시하기 위해 
						dateForm += "<td><a href='#' class='isBooked'>★</a></td>";
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
	<c:if test="${noCount == true }">
		<script type="text/javascript">
			alert("지정된 예약횟수를 초과하여 예약 할 수 없습니다.");
			location.href="book.do";
		</script>
	</c:if>
	<h2 id="server">[서버시간]<span id="serverTime">00:00:00</span></h2>
	<h2 id="bkTable"></h2>
	<p>
		시설 이름 : 
		<select id="bkStrId">
			<c:forEach items="${strId }" var="str">
				<option value="${str.id }">${str.nameById }</option>
			</c:forEach>
		</select>
	</p>
	<h2 id="bkStr">${strId.get(0).nameById }</h2>
	<div id="bookTable" >
		
	</div>
</div>
