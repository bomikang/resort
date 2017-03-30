<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	.book_book_table, .book_mem_info_table{width:800px; margin:0 auto;}
	.book_book_table th, .book_mem_info_table th{width:120px;}
	.book_book_table td, .book_mem_info_table td{text-align: left; padding-left:10px;}
	select{width:133px; text-align: center; padding:6px 0;}
	input[type='text'], input[type='date']{width:120px;}
	.book_mem_info_table #email_input{width:267px;}
</style>
<script>
	var reg_tel1 = /^\d{3,4}$/; 
	var reg_tel2 = /^\d{4}$/; 
	$(function(){
		/*고객 휴대폰 번호 입력해 놓는 구문 필요*/
		
		setDateForm();	
		setPriceForm();
		setTelForm();
		
		$.ajax({
			url:"bookcheckdate.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"startDate":$("#start").val(),
					"endDate" : $("#end").val(),
					"strNo" : ${str.no }
			},
			async:false,
			success:function(data){
				if(data==false){
					location.href="book.do";
				}							
			} 
		});
		
		$(document).on("change","#period",function() {	
			setDateForm();	
			setPriceForm();	
			$("#btnBook").attr("disabled","disabled");
		});//기간을 변경할 때
		
		$("#btnDate").click(function(){
			var st = srvTime();
			var now = new Date(st);
			var date = new Date(${startDate});
			if(date.getDate()==now.getDate()){
				if(now.getHours()>=12 && now.getMinutes()>=30){
					alert("금일 예약이 마감 되어 예약을 진행할 수 없습니다. 예약을 희망할 경우 054-951-7531로 연락주시기바랍니다.");
					location.href="book.do";
					return false;
				}
			}
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
						var period = $("#period").val();
						alert("예약할 수 없는 날짜입니다.");
						if(period=="1"){
							location.href="book.do";
						}else{
							$("#period").val("1");	
							setDateForm();
							setPriceForm();
						}
					}else{
						alert("예약가능합니다.");
						$("#btnBook").removeAttr("disabled");
					}							
				} 
			});			 
			 return false;
		});

		
		$("#btnBook").click(function(){
			if(!confirm("선택하신 기간으로 예약 하시겠습니까?")){
				return false;
			}else{
				var st = srvTime();
				var now = new Date(st);
				var date = new Date(${startDate});
				if(date.getDate()==now.getDate()){
					if(now.getHours()>=12 && now.getMinutes()>=30){
						alert("금일 예약이 마감 되어 예약을 진행할 수 없습니다. 예약을 희망할 경우 054-951-7531로 연락주시기바랍니다.");
						location.href="book.do";
						return false;
					}	
				}
			
				//예약하기 버튼클릭 후 예약진행을 선택한 경우	
				var tel1 = $("input[name='bkTel2']").val();
				var tel2 = $("input[name='bkTel3']").val();
				if(reg_tel1.test(tel1)==false||reg_tel2.test(tel2)==false){
					alert("형식에 맞지 않는 전화번호 입니다.");
					return false;
		   	 	}else{			
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
								alert("예약이 진행 중이라 예약 하실 수 없습니다.");
								location.href="book.do";
							}else{
								$("#bookProcess").submit();
							}							
						} 
					});	
		   	 	}
				 return false;
			}
		});
		
		$("#btnBack").click(function(){
			if(confirm("예약을 취소하고 이전화면으로 돌아가시겠습니까?")){
				location.href="book.do";
			}
		});
	});//ready
	/* 예약을 진행할 로그인된 회원의 휴대폰 번호를 3자리로 나누어 각자의 자리에 입력 */
	function setTelForm(){
		var tel = "${user_info.my_tel }";
		console.log(tel);
		var tels = tel.split("-");
		if(tels.length==3){
			$("select[name='bktel1']").val(tels[0]);
			$("input[name='bkTel2']").val(tels[1]);
			$("input[name='bkTel3']").val(tels[2]);
		}		
	}
	
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
		
		$("#totalPrice").html(number_format(total)+"원");
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
	/*이전 화면에서 넘어 온 시작 날짜값을 바탕으로 1박2일, 2박 3일, 3박 4일의 기간을 기준으로 끝날짜를 계산하여 input form에 넣는 함수 */
	function setDateForm(){		
		var date = new Date(${startDate});
		
		var val = Number($("#period").val());
		var startForm = convertToString(date.getTime());
		$("#start").val(startForm);
		
		date.setDate(date.getDate()+val);
		var endForm = convertToString(date.getTime());
		$("#end").val(endForm);
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
</script>

<div class="way_top">
	<h3>예약하기<br /><span>홈 > 예약안내 > 예약하기 > 예약하기</span></h3>
</div>
<div id="bk_book" class='intro_padding'>
	<!-- 예약폼이 들어갈 예정입니다. -->
	<form action="bookprocess.do" method="post" id="bookProcess">
		<!-- 예약정보1 - 시설정보 및 기간 설정에 따른 가격 계산 -->
		<input type="hidden" value="${str.no }" name="strNo">
		<input type="hidden" value="${user_info.my_no }" name="memNo">
		
		<h3 class='table_caption'>예약 객실</h3>
		<table class='book_book_table'>
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
				<td>[성수기] : ${str.originPriceToString }원/1박   [비수기] : ${str.lessPriceToString }원/1박</td>
			</tr>
			<tr>
				<th>이용 기간</th>
				<td>
					입실날짜 : <input type="date" readonly="readonly" id="start" name="start">
					퇴실날짜 : <input type="date" readonly="readonly" id="end" name="end">
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
		<h3 class='table_caption'>고객 정보</h3>
		
		<table class='book_mem_info_table'>
			<tr>
				<th>예약자명</th>
				<td><input type="text" readonly="readonly" disabled="disabled" required="required" value="${user_info.my_name }"></td>
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
					<input type="text" readonly="readonly" value="${user_info.my_mail }"  id="email_input">					
				</td>
				<tr>
			</tr>
		</table>
		<p class="act_btn_area">
			<button type="button" id="btnBook" disabled="disabled">예약하기</button>
			<button type="button" id="btnBack"class='moving_btn'>취소하기</button>
			<!-- <a href="book.do" class='moving_btn'>돌아가기</a> -->
		</p> 
	</form>
	
</div>