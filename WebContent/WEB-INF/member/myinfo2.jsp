<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table,td{
		border: 1px solid #BDBDBD;
	 
	}
	.update{
		display: none;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/common.js"></script>
<script>
	$(function(){
		 $("#btn1").click(function(){
			$("#nameUpdate").css("display","block");
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
				$("#passwordUpdate").css("display","block");	
				$("#btn3").css("display","none");
				$("#btn3_1").css("display","inline-block");
				$("#btn3_2").css("display","inline-block");
			}); 
		 $("#btn4").click(function(){
				$("#mailUpdate").css("display","block");
				$("#btn4").css("display","none");
				$("#btn4_1").css("display","inline-block");
				$("#btn4_2").css("display","inline-block");
			});
		 $("#btn5").click(function(){
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
		 
	});	
</script>
</head>
<body>
	<div id="main">
		<div id="header">
			<h1>회원정보</h1>
				<p>hotdog19k2님의 연락처 정보입니다.</p>
				
				<p>회원정보는 개인정보처리방침에 따라 안전하게 보호되며, 회원님의 명백한 동의 없이 공개 또는 제 3자에게 제공되지 않습니다.
					 개인정보처리방침</p>
		</div>
		<div id="content">
			
			
				<table>
				
					<tr>
						<td>사용자 이름  </td>
						<td><form action="updateInfo.do" method="post">${info.name } <button type="button" id="btn1">수정</button>
							<span id="nameUpdate" class="update">
											
								<input type="text" name="name" placeholder="이름">
								<input type="submit" id=btn1_1>
									<!-- <button type="button" id="btn1_1">완료</button>  -->
									<button type="button" id="btn1_2">취소</button>
							</span>
						</form></td>
			</tr>
					<tr>
						<td>사용자 아이디 </td>
						<td> ${info.id } <button type="button" id="btn2">수정</button>
							<span id="IdUpdate" class="update">
								<input type="text" name="id" placeholder="아이디">
									<button type="button" id="btn2_1">완료</button>
									<button type="button" id="btn2_2">취소</button>
							</span>
						</td>
					</tr>
					<tr>
						<td>비밀번호 변경  </td>
						<td>${info.password } <button type="button" id="btn3">수정</button>
							<span id="passwordUpdate" class="update">
								<input type="text" name="password" placeholder="비밀번호">
									<button type="button" id="btn3_1">완료</button>
									<button type="button" id="btn3_2">취소</button>
							</span>
						</td>
					</tr>
					<tr>
						<td>본인확인 이메일  ${info.mail }</td>
						<td>${info.mail } <button type="button" id="btn4">수정</button>
							<span id="mailUpdate" class="update">
								<input type="text" name="mail" placeholder="e-mail">
									<button type="button" id="btn4_1">완료</button>
									<button type="button" id="btn4_2">취소</button>
							</span>
						</td>
					</tr>
					<tr>
						<td>본인확인 전화번호 </td>
						<td> ${info.tel } <button type="button" id="btn5">수정</button>
							<span id="telUpdate" class="update">
								<input type="text" name="tel" placeholder="전화번호">
									<button type="button" id="btn5_1">완료</button>
									<button type="button" id="btn5_2">취소</button>
							</span>
						</td>
					</tr>
					<tr>
						<td>탈퇴</td>
						<td>테스트 중 <button type="button" id="btn6">탈 퇴</button>
							
						</td>
					</tr>
					
				</table>
		
		</div>
	</div>
</body>
</html>