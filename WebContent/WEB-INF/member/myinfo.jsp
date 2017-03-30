<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.update {display: none;}
table{width:750px; margin:0 auto;margin-top:25px;}
table td{text-align: right; height:45px;}
table th:FIRST-CHILD{width:130px;}
input[type='submit'], button{width:50px;}
input[type='text'], input[type='password']{width:200px; display: inline;}
small{color:#777; margin:15px 0;}
h2 b{color:#cc0000;}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/logout_history.js"></script>
<!--로그아웃상태 시 뒤로가기버튼 막음  -->
<script src="js/common.js"></script>
<script>



	$(function(){
		 $("#btn1").click(function(){
			$("#nameUpdate").css("display","inline-block");
			$("#btn1").css("display","none");
			$("#btn1_1").css("display","inline-block");
			$("#btn1_2").css("display","inline-block");
		});
		 $("#btn2").click(function(){
				$("#IdUpdate").css("display","block");	
				$("#btn2").css("display","none");
				$("#btn2_1").css("display","inline-block");
				$("#btn2_2").css("display","inline-block");
			}); 
		 $("#btn3").click(function(){
				$("#passwordUpdate").css("display","inline-block");	
				$("#btn3").css("display","none");
				$("#btn3_1").css("display","inline-block");
				$("#btn3_2").css("display","inline-block");
			}); 
		 $("#btn4").click(function(){
				$("#mailUpdate").css("display","inline-block");
				$("#btn4").css("display","none");
				$("#btn4_1").css("display","inline-block");
				$("#btn4_2").css("display","inline-block");
			});
		 $("#btn5").click(function(){
				$("#telUpdate").css("display","inline-block");
				$("#btn5").css("display","none");
				$("#btn5_1").css("display","inline-block");
				$("#btn5_2").css("display","inline-block");
			});
		 $("#btn1_2").click(function(){
				$("#nameUpdate").css("display","none");
				$("#btn1").css("display","inline-block");
				$("#btn1_1").css("display","none");
				$("#btn1_2").css("display","none");
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
			});
		 $("#btn4_2").click(function(){
				$("#mailUpdate").css("display","none");
				$("#btn4").css("display","inline-block");
				$("#btn4_1").css("display","none");
				$("#btn4_2").css("display","none");
			});
		 $("#btn5_2").click(function(){
				$("#telUpdate").css("display","none");
				$("#btn5").css("display","inline-block");
					$("#btn5_1").css("display","none");
				$("#btn5_2").css("display","none");
			});	
		 
		 
			 $("#btn1_1").click(function(){
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
									location.replace("index.jsp");
								}
					} 
				});
			});    
	
			  $("#btn3_1").click(function(){
				  if($("#password").val() != $("#password2").val()){
			            alert("패스워드가 일치하지 않습니다.");
						return false;            
			        }  
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
									location.replace("index.jsp");
								}
					} 
				});
			}); 
			  $("#btn4_1").click(function(){
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
										location.replace("index.jsp");
									}
						} 
					});
				});   
			  $("#btn5_1").click(function(){
					$.ajax({
						url:"updateInfo.do",
						type:"post",
						timeout:30000,
						dataType:"json",
						data:{"tel":$("#tel").val()},
						success:function(data){
													// Json Handler 에서 처리한 DATA 값이 아래와 갔다면 ..
									if(data=="ok"){
										alert("개인정보가 수정되었습니다.");
										location.replace("index.jsp");
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
								console.log(data);			
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
	<h3>회원정보<br /><span>홈 > 회원정보 > 회원정보</span></h3>
</div>
<div id="main" class='intro_padding'>
	<div id="header">
		<h2><img alt="" src="image/icon_flower_orange.png" class='icon_flower'><b>${info.id }님</b>의 연락처 정보입니다.</h2>
		<small>회원정보는 개인정보처리방침에 따라 안전하게 보호되며, 회원님의 명백한 동의 없이 공개 또는 제 3자에게 제공되지않습니다. 개인정보처리방침</small>
	</div>
	<div id="content">
		<table>
			<tr>
				<th>사용자 이름</th>
				<td>
					<form action="updateInfo.do" method="post">
						${info.name }<button type="button" id="btn1">수정</button>
						<span id="nameUpdate" class="update">
							<input type="text" name="name" id="name" placeholder="이름" required="required">
							<button type="button" id="btn1_1">완료</button>
							<button type="button" id="btn1_2">취소</button>
						</span>
					</form>
				</td>
			</tr>

			<tr>
				<th>비밀번호 변경</th>
				<td>********
					<button type="button" id="btn3">수정</button>
					<span id="passwordUpdate" class="update">
						<input type="password" name="password" id="password" placeholder="비밀번호 입력" required="required">
						<input type="password"name="password2" id="password2" placeholder="비밀번호 확인" required="required">
						<button type="button" id="btn3_1">완료</button>
						<button type="button" id="btn3_2">취소</button>
					</span>
				</td>
			</tr>
			<tr>
				<th>본인확인 이메일</th>
				<td>
					${info.mail }<button type="button" id="btn4">수정</button> 
					<span id="mailUpdate" class="update"> 
						<input type="text" name="mail" id="mail" placeholder="e-mail" required="required">
						<button type="button" id="btn4_1">완료</button>
						<button type="button" id="btn4_2">취소</button>
					</span>
				</td>
			</tr>
			<tr>
				<th>본인확인 전화번호</th>
				<td>
					${info.tel }<button type="button" id="btn5">수정</button> 
					<span id="telUpdate" class="update">
						<input type="text" name="tel" id="tel" placeholder="전화번호" required="required">
						<button type="button" id="btn5_1">완료</button>
						<button type="button" id="btn5_2">취소</button>
					</span>
				</td>
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