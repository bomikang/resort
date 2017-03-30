<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
/* <<<<<<< HEAD
#infoName, #infoPwd, #infoMail, #infoTel{display: inline-block; width:170px;}
input[type='submit'], button{width:50px;}
input[type='text'], input[type='password']{width:120px; display: inline;}
input#mail{width:220px;}
input#tel1_input, input#tel2_input{width:50px !important;}
small{color:#777; margin:15px 0;}
h2 b{color:#cc0000;}
======= */
.update {display: none;}
table {width: 520px;margin: 0 auto;margin-top: 25px;}
table td {text-align: right;height: 60px;}
table th:FIRST-CHILD {width: 130px;}
input[type='submit'], button {width: 50px;}
input[type='text'], input[type='password'] {width: 200px;display: inline;}
small {color: #777;margin: 15px 0;}
h2 b {color: #cc0000;}
#btn1, #btn2, #btn3, #btn4, #btn5{position:relative;}
#infoName, #infoPwd, #infoMail, #infoTel{display: inline-block; width:170px;}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/logout_history.js"></script>
<!--로그아웃상태 시 뒤로가기버튼 막음  -->
<script src="js/common.js"></script>
<script>
var reg_tel1 = /^\d{3,4}$/; 
var reg_tel2 = /^\d{4}$/; 
var reg_upw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-]|.*[0-9]).{6,24}$/; //6~24자 영문대소문자, 숫자, 특수문자 혼합하여 사용
var reg_name = /^[가-힣]{2,20}$/; //한글 2~20자 가능
var reg_mail = /^\w{5,12}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,3}$/; //hotdog123@naver.com , hotdog12@naver.co.kr

	$(function(){
		 $("#btn1").click(function(){
			$("#nameUpdate").css("display","block");
			$("#btn1").css("display","none");
			$("#btn1_1").css("display","inline-block");
			$("#btn1_2").css("display","inline-block");
			$("#infoName").css("display", "none");
			$("#name").val($("#infoName").text());
		});
		 $("#btn2").click(function(){
				$("#IdUpdate").css("display","block");	
				$("#btn2").css("display","none");
				$("#btn2_1").css("display","inline-block");
				$("#btn2_2").css("display","inline-block");
			}); 
		 $("#btn3").click(function(){
			 $.ajax({
					url:"myinfo_passwordcheck.do",
					type:"post",
					timeout:30000,
					dataType:"json",
					data:{"origin_pwd":$("#origin_pwd").val()},
					success:function(data){
							// Json Handler 에서 처리한 DATA 값이 아래와 갔다면 ..
								if(data=="no"){
									alert("비밀번호가 틀렸습니다.");
								}else if(data=="ok"){
									$("#passwordUpdate").css("display","inline-block");	
									$("#btn3").css("display","none");
									$("#origin_pwd").css("display","none");
									$("#btn3_1").css("display","inline-block");
									$("#btn3_2").css("display","inline-block");
								}
					} 
				});

			}); 
		 $("#btn4").click(function(){
				$("#mailUpdate").css("display","block");
				$("#btn4").css("display","none");
				$("#btn4_1").css("display","inline-block");
				$("#btn4_2").css("display","inline-block");
				$("#infoMail").css("display", "none");
				$("#mail").val($("#infoMail").text());
			});
		 $("#btn5").click(function(){
		
				var tel = $("#infoTel").text();
				var bkTels = tel.split("-");
				if(bkTels.length == 3){
					$("select[name='bkTel1']").val(bkTels[0]);
					$("input[name='bkTel2']").val(bkTels[1]);
					$("input[name='bkTel3']").val(bkTels[2]);
				}
				
			 	$("#infoTel").css("display", "none");
				$("#telUpdate").css("display","block");
				$("#btn5").css("display","none");
				$("#btn5_1").css("display","inline-block");
				$("#btn5_2").css("display","inline-block");
			});
		 $("#btn1_2").click(function(){
				$("#nameUpdate").css("display","none");
				$("#btn1").css("display","inline-block");
				$("#btn1_1").css("display","none");
				$("#btn1_2").css("display","none");
				
				$("#infoName").css("display", "inline-block");
				$("#name").val($("#infoName").text());
			});
		 $("#btn2_2").click(function(){
				$("#IdUpdate").css("display","none");
				$("#btn2").css("display","inline-block");
				$("#btn2_1").css("display","none");
				$("#btn2_2").css("display","none");
			});
		 $("#btn3_2").click(function(){
				$("#passwordUpdate").css("display","none");
				$("#btn3").css("display","inline-block");
				$("#btn3_1").css("display","none");
				$("#btn3_2").css("display","none");
				$("#infoPwd").css("display","inline-block");
			});
		 $("#btn4_2").click(function(){
				$("#mailUpdate").css("display","none");
				$("#btn4").css("display","inline-block");
				$("#btn4_1").css("display","none");
				$("#btn4_2").css("display","none");
				$("#infoMail").css("display", "inline-block");
				$("#mail").val($("#infoMail").text());
			});
		 $("#btn5_2").click(function(){

				$("#infoTel").text($("#infoTel").text());
			 	$("#infoTel").css("display", "inline-block");
				$("#telUpdate").css("display","none");
				$("#btn5").css("display","inline-block");
					$("#btn5_1").css("display","none");
				$("#btn5_2").css("display","none");
			});	
		 
		 
			 $("#btn1_1").click(function(){
				 if(reg_name.test($("#name").val())==false){
					 alert("형식에 맞지 않는 이름입니다.");
				 }else{
				 $.ajax({
					url:"updateInfo.do",
					type:"post",
					timeout:30000,
					dataType:"json",
					data:{"name":$("#name").val()},
					success:function(data){
												// Json Handler 에서 처리한 DATA 값이 아래와 갔다면 ..
								if(data=="ok"){
									
									
									alert("개인정보가 수정되었습니다.");
									$("#nameUpdate").css("display","none");
									$("#btn1").css("display","inline-block");
									$("#btn1_1").css("display","none");
									$("#btn1_2").css("display","none");
									location.replace("myinfo.do");
						}
					}
				 });
				}
			});    
			 
			 
			  $("#btn3_1").click(function(){
				  if($("#password").val() != $("#password2").val()){
			            alert("패스워드가 일치하지 않습니다.");
						return false;            
			        }else{  
				$.ajax({
					url:"updateInfo.do",
					type:"post",
					timeout:30000,
					dataType:"json",
					data:{"password":$("#password").val()},
					success:function(data){
												// Json Handler 에서 처리한 DATA 값이 아래와 갔다면 ..
								if(data=="ok"){
									alert("개인정보가 수정되었습니다.");
									$("#btn3").css("display","inline-block");
									$("#origin_pwd").css("display","inline-block");
									$("#password").css("display","none");
									$("#password2").css("display","none");
									$("#btn3_1").css("display","none");
									$("#btn3_2").css("display","none");
									location.replace("myinfo.do");
								}
					} 
				});
			   }
			}); 
			  $("#btn4_1").click(function(){
				  if(reg_mail.test($("#mail").val())==false){
						alert("형식에 맞지 않는 이메일입니다.");   
			   	 	}else{	
					$.ajax({
						url:"updateInfo.do",
						type:"post",
						timeout:30000,
						dataType:"json",
						data:{"mail":$("#mail").val()},
						success:function(data){
													// Json Handler 에서 처리한 DATA 값이 아래와 갔다면 ..
									if(data=="ok"){
										alert("개인정보가 수정되었습니다.");
										$("#mailUpdate").css("display","none");
										$("#btn4").css("display","inline-block");
										$("#btn4_1").css("display","none");
										$("#btn4_2").css("display","none");
										location.replace("myinfo.do");
									}
						} 
					});
			   	 }
			});   
			  
			  $("#btn5_1").click(function(){
				  	var bkTel1 = $("select[name='bkTel1']").val();
				  	var bkTel2 = $("input[name='bkTel2']").val();
				  	var bkTel3 = $("input[name='bkTel3']").val();
				  	if(reg_tel1.test(bkTel2)==false){
				  		alert("형식에 맞지 않는 전화번호 입니다.");
				  		$("input[name='bkTel2']").focus();
						return false;
				  	}
				  	if(reg_tel2.test(bkTel3)==false){
						alert("형식에 맞지 않는 전화번호 입니다.");
						$("input[name='bkTel3']").focus();
						return false;
			   	 	}
				  	var tel = bkTel1+"-"+bkTel2+"-"+bkTel3;
					$.ajax({
						url:"updateInfo.do",
						type:"post",
						timeout:30000,
						dataType:"json",
						data:{"tel":tel},
						success:function(data){
													// Json Handler 에서 처리한 DATA 값이 아래와 갔다면 ..
									if(data=="ok"){
										alert("개인정보가 수정되었습니다.");
										 $("#btn5_1").css("display","none");
										 $("#btn5_2").css("display","none");
										 $("#telUpdate").css("display","none");
										 $("#infoTel").css("display","inline-block");
										 $("#btn5").css("display","inline-block");
										 location.replace("myinfo.do");
										 
									}
						} 
					});
				});
			  $("#withdrawal").click(function(){
					var cf = confirm("정말 탈퇴하시겠습니까?");
					if(cf==true){
					$.ajax({
						url:"withdrawal.do",
						type:"post",
						timeout:30000,
						dataType:"json",
						success:function(data){			
							location.replace("index.jsp");
										
						} 
					});
					}else {
						return false;
					}
				});
			  
			  <c:if test="${!empty user_info }">
				<c:if test="${user_info.isMng==true }">

					$("#main button").css("display", "none");
				</c:if>
			</c:if>	 
	});	
</script>
</head>
<body>
	<div class="way_top">
		<h3>
			회원정보<br /> <span>홈 > 회원정보 > 회원정보</span>
		</h3>
	</div>
	<div id="main" class='intro_padding'>
		<div id="header">
			<h2>
				<img alt="" src="image/icon_flower_orange.png" class='icon_flower'><b>${info.id }님</b>의
				연락처 정보입니다.
			</h2>
			<small>회원정보는 개인정보처리방침에 따라 안전하게 보호되며, 회원님의 명백한 동의 없이 공개 또는 제
				3자에게 제공되지않습니다. 개인정보처리방침</small>
		</div>
		<div id="content">
			<table>
				<tr>
					<th>사용자 이름</th>
					<td>
						<form action="updateInfo.do" method="post">
							${info.name }
							<button type="button" id="btn1">수정</button>
							<span id="nameUpdate" class="update"> <input type="text"
								name="name" id="name" placeholder="이름" required="required">
								<button type="button" id="btn1_1">완료</button>
								<button type="button" id="btn1_2">취소</button>
							</span>
						</form>
					</td>
				</tr>
				<tr>
					<th>비밀번호 변경</th>
					<td><input type="password" id="origin_pwd" name="origin_pwd"
						placeholder="사용중인 비밀번호를 입력하세요!">
						<button type="button" id="btn3">수정</button> <span
						id="passwordUpdate" class="update"> <input type="password"
							name="password" id="password" placeholder="비밀번호 입력"
							required="required"> <input type="password"
							name="password2" id="password2" placeholder="비밀번호 확인"
							required="required">
							<button type="button" id="btn3_1">완료</button>
							<button type="button" id="btn3_2">취소</button>
					</span></td>
				</tr>
				<tr>
					<th>본인확인 이메일</th>
					<td>${info.mail }
						<button type="button" id="btn4">수정</button> <span id="mailUpdate"
						class="update"> <input type="text" name="mail" id="mail"
							placeholder="e-mail" required="required">
							<button type="button" id="btn4_1">완료</button>
							<button type="button" id="btn4_2">취소</button>

					</span>
					</td>
				</tr>
				<tr>
					<th>본인확인 전화번호</th>
					<td><span id='infoTel'>${info.tel }</span>
						<button type="button" id="btn5">수정</button> <span id="telUpdate"
						class="update"> <select name="bkTel1">
								<option value="010">010</option>
								<option value="011">011</option>
								<option value="019">019</option>
								<option value="017">017</option>
						</select> - <input type="text" required="required" name="bkTel2"> -
							<input type="text" required="required" name="bkTel3">


							<button type="button" id="btn5_1">완료</button>
							<button type="button" id="btn5_2">취소</button>
					</span></td>
				</tr>
				<tr>
					<th>탈퇴</th>
					<td><button type="button" id="withdrawal">탈 퇴</button></td>
				</tr>

			</table>

		</div>
	</div>
</body>
</html>