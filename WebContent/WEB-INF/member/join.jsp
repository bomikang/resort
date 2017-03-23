<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>

.error{
	display : none;
	color:red;
	padding:0;
	margin:0 0 0 90px;
	font-size: 12px;
}
.ex{
	display : none;
	color:red;
	padding:0;
	margin:0 0 0 90px;
	font-size: 12px;
}
#title{
	width : 130px;
	margin: 0 auto;
}
fieldset{
	width : 350px;
	margin: 0 auto;
}
#submit_div{
	width : 380px;	
	margin: 0 auto;
}
#sub{
	width : 380px;
	height: 45px;
	margin-top: 20px;
}
.tt{
	display: none;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/common.js"></script>
<script>
$(function(){
	var reg_uid = /^[a-z0-9_]{5,12}$/; // 영소문자,숫자 5~12글자 
	var reg_upw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-]|.*[0-9]).{6,24}$/; //6~24자 영문대소문자, 숫자, 특수문자 혼합하여 사용
	var reg_name = /^[가-힣]{2,20}$/; //한글 2~20자 가능
	var reg_tel = /^(010|016|011)\d{3,4}\d{4}$/; //010,016,011,사용가능합니다.
	var reg_mail = /^\w{5,12}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,3}$/; //hotdog123@naver.com , hotdog12@naver.co.kr
	
	var check_Id = document.getElementById("id");
	var check_Name = document.getElementById("name");
	var check_Pwd = document.getElementById("password");
	var check_Tel = document.getElementById("tel");
	var check_Mail = document.getElementById("email");
	var check_Pwd2 = document.getElementById("password2");
	check_Pwd2.onchange=function(){
		if($("#password").val() != $("#password2").val()){
            $(".error").css("display","block");
        }else{
        	$(".error").css("display","none");
        }  
	}
	check_Id.onchange=function(){
		if(reg_uid.test($("#id").val())==false){ 
            $(".ex").eq(0).css("display","block");
   		
		}else{
			$(".ex").eq(0).css("display","none");
		}	
	}
	check_Pwd.onchange=function(){
		if(reg_upw.test($("#password").val())==false){
            $(".ex").eq(1).css("display","block"); 
   	 	}else{
   	 	 $(".ex").eq(1).css("display","none"); 
   	 	}
	
	}
	check_Name.onchange=function(){
		if(reg_name.test($("#name").val())==false){
            $(".ex").eq(2).css("display","block"); 
   	 	}else{
   	 	 $(".ex").eq(2).css("display","none"); 
   	 	}	
	}
	check_Tel.onchange=function(){
		if(reg_tel.test($("#tel").val())==false){
            $(".ex").eq(3).css("display","block");
   	 	}else{
   	 	$(".ex").eq(3).css("display","none");
   	 	}	
	}
	check_Mail.onchange=function(){
		if(reg_mail.test($("#email").val())==false){
            $(".ex").eq(4).css("display","block");
   	 	}else{
   	 		 $(".ex").eq(4).css("display","none");
   	 	}
	}
	// 아이디 중복 확인 버튼 클릭스 화면 갱신 없이 ajax 로 데이터 값 읽어오기 
	$("#btn").click(function(){
		$.ajax({
			url:"checkId.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"id":$("#id").val()},//게시글의 번호
			success:function(data){
										// Json Handler 에서 처리한 DATA 값이 아래와 갔다면 ..
						if(data=="ok"){
							alert("사용가능 아이디 입니다.");			
						}else if(data=="no"){
							alert("사용불가 아이디 입니다.");
						}
						if(data=="noID"){
							alert("형식에 맞지 않는 ID 입니다.");
							 $(".ex").eq(0).css("display","block");
						}				
			} 
		});
	});	 	
});
</script>
</head>
<body>
	<div id="title">
		<h1> 회원가입 </h1>
	</div>
	<div id="join">
	<form action="join.do" method="post" name="f1">
	
		<fieldset>
			<p>
				<label>아이디 : </label>  <input type="text" name="id" id="id" required="required" placeholder="아이디를 입력하세요" > 
				<span class="ex">5~12자의 영문 소문자, 숫자와 특수기호(_)만 사용 가능합니다.</span>
				<button id="btn" type="button">아이디중복</button>
			</p>
			<p>
				<label>암호 : </label><input type="password" id ="password"name="password" required="required" placeholder="비밀번호를 입력하세요">
				<span class="ex">6~24자 영문대소문자, 숫자, 특수문자 혼합하여 사용 가능합니다.</span>
			</p>
			<p>
				<label>비밀번호 재확인 :</label><input type="password" id="password2"name="password2" required="required" placeholder="비밀번호를 재확인해주세요">
				<span class="error">입력한 비밀번호가 일치하지 않습니다.</span>
			</p>
			
		</fieldset><br>
		
		<fieldset>
			<p>
				<label>이름 :</label><input type="text" id="name"name="name" required="required" placeholder="이름을 입력하세요">
				<span class="ex">한글 2~20자 사용 가능합니다.</span>
			</p>
			<p>
				<label>전화번호 : </label><input type="text" id="tel"name="tel" required="required" placeholder="예)010-xxxx-xxxx">
				<span class="ex">010,016,011,사용 가능합니다.</span>
			</p>
			<p>
				<label>E-Mail :</label><input type="text" id ="email" name="email" required="required" placeholder="본인확인 이메일">
				<span class="ex">예)hotdog123@naver.com , hotdog12@naver.co.kr</span>
			</p>
			
		</fieldset>
		
		<div id="submit_div">
			<input type="submit" value="가입하기" id="sub" >
		</div>
		
		</form>
		
	</div>
</body>
</html>

