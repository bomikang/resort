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
	font-size: 12px;
}
.ex{
	display : none;
	color:red;
	padding:0;
	font-size: 12px;
}
.form_EX{
	font-size: 12px;
	color:#cc3333;
}

.way_top h2{color:#fff; position:relative; left:30px; top:5px;}
fieldset{width:680px; margin:0 auto; margin-top:70px; margin-bottom:80px; padding-top:20px; padding-bottom:50px;background:rgba(255,255,255,0.9); border-radius: 20px; box-shadow: 1px 1px 0px 1px black;}
fieldset p{width:570px;}
fieldset input{display: inline-block;}
fieldset input[name='id'], input[name='password'], input[name='password2'], input[name='name'], input[name='email']{width:200px; height:31px !important;
border-radius: 15px; background:#929292; color:#fff !important; border:1px solid rgba(0,0,0,0) !important;}
input[type='submit'], button{background:#FFBB83 !important; width:120px; border-radius: 15px; height:46px; color:#fff; font-family:"NanumSquareB";}
button{margin-left:12px;}
input[type='submit']{width:415px;} 
::-webkit-input-placeholder{color:#fff;}
fieldset input[name='bkTel2'], input[name='bkTel3']{width:102px; border-radius: 10px; background:#929292; border:none !important;}
fieldset label{color:#929292; margin-right:12px;}
select{border-radius: 10px; background:#929292; color:#fff;  border:none !important;}
#submit_div{width:570px; display:block; margin: 0 auto;}

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/common.js"></script>
<script>
var duplicatedFlag = false;
$(function(){
	var reg_uid = /^[a-z0-9_]{5,12}$/; // 영소문자,숫자 5~12글자 
	var reg_upw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-]|.*[0-9]).{6,24}$/; //6~24자 영문대소문자, 숫자, 특수문자 혼합하여 사용
	var reg_name = /^[가-힣]{2,20}$/; //한글 2~20자 가능
	var reg_tel = /^(010|016|011)\d{3,4}\d{4}$/; //010,016,011,사용가능합니다.
	var reg_mail = /^\w{5,12}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,3}$/; //hotdog123@naver.com , hotdog12@naver.co.kr
	
	/*임시*/
	var reg_tel1 = /^\d{3,4}$/; 
	var reg_tel2 = /^\d{4}$/; 
	
	var check_Id = document.getElementById("id");
	var check_Name = document.getElementById("name");
	var check_Pwd = document.getElementById("password");
	var check_Tel = document.getElementById("tel");
	var check_Mail = document.getElementById("email");
	var check_Pwd2 = document.getElementById("password2");
	$("form[name='f1']").submit(function() { 
		$.ajax({
			url:"checkId.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"id":$("#id").val()},
			success:function(data){
						if(data=="ok"){
							return true;
						}else if(data=="no"){
							$(".error").eq(0).css("display","block");
							alert("중복된 아이디 입니다. 확인하여 주십시오.")
							return false;		
						}
						if(data=="noID"){
							alert("형식에 맞지 않는 ID 입니다.");
							return false;
						}				
			} 
		});
		if(duplicatedFlag == false)
		{
			alert("아이디 중복버튼을 클릭해주세요");
			return false;
		}
		
		if($("#password").val() != $("#password2").val()){
            alert("패스워드가 일치하지 않습니다.");
			return false;            
        }  
		if(reg_uid.test($("#id").val())==false){ 
			alert("형식에 맞지 않는 ID입니다.");
			return false;
		}
		if(reg_upw.test($("#password").val())==false){
			alert("형식에 맞지 앟는 패스워드 입니다.");
			return false;
   	 	}
		if(reg_name.test($("#name").val())==false){
			alert("형식에 맞지 않는 이름 입니다.");
			return false;
   	 	}
		if(reg_tel1.test($("input[name='bkTel3']").val())==false){
			alert("형식에 맞지 않는 전화번호 입니다.");
			$("#bkTel2").focus();
			return false;
   	 	}	
		if(reg_tel2.test($("input[name='bkTel3']").val())==false){
			alert("형식에 맞지 않는 전화번호 입니다.");
			$("#bkTel3").focus();
			return false;
   	 	}
		if(reg_mail.test($("#email").val())==false){
			alert("형식에 맞지 않는 이메일입니다.");
			return false;   
   	 	}	
		
		if(reg_uid.test($("#id").val())==true&&reg_uid.test($("#id").val())==true&&reg_name.test($("#name").val())==true&&
				reg_tel.test($("#tel").val())==true&&reg_mail.test($("#email").val())==true){
			var cf = confirm("가입 하시겠습니까?");
				if(cf == true){
					return true;
				}else{
					return false;
				}	
	}
});
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
	$("input[name='bkTel2']").onchange=function(){
		if(reg_tel1.test($("input[name='bkTel2']").val())==false){
            $(".ex").eq(3).css("display","block");
   	 	}else{
   	 	$(".ex").eq(3).css("display","none");
   	 	}	
	}
	$("input[name='bkTel3']").onchange=function(){
		if(reg_tel1.test($("input[name='bkTel3']").val())==false){
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
		duplicatedFlag = false;	
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
							$(".error").eq(0).css("display","none");
							duplicatedFlag = true;
						}else if(data=="no"){
							$(".error").eq(0).css("display","block");
							alert("중복된 아이디 입니다. 확인하여 주십시오.")
										
						}
						if(data=="noID"){
							alert("형식에 맞지 않는 ID 입니다.");
							
						}				
			} 
		});
	});
	
});
</script>
</head>
<body>
<div class="way_top">
	<h2>눈꽃자연휴양림 로그인</h2>
</div>
	<div id="join">
	<form action="join.do" method="post" name="f1">
		<fieldset>
			<p>
				<label>아&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이&nbsp;&nbsp;&nbsp;&nbsp;디 </label>
				<input type="text" name="id" id="id" required="required" placeholder="아이디를 입력하세요" style="width:300px"><button id="btn" type="button">아이디중복</button> 
				<br /><span class="form_EX">5~12자의 영문 소문자, 숫자와 특수기호(_)만 사용 가능합니다.</span>
				<span class="ex">형식에 맞지 않는 아이디 입니다.</span>
				<span class="error">사용중인 아이디 입니다.</span>
				
			</p>
			<p>
				<label>비&nbsp;&nbsp;밀&nbsp;&nbsp;번&nbsp;&nbsp;호</label>
				<input type="password" id ="password"name="password" required="required" placeholder="비밀번호를 입력하세요" style="width:300px">
				<br /><span class="form_EX">6~24자 영문대소문자, 숫자, 특수문자 혼합하여 사용 가능합니다.</span>
				<span class="ex">형식에 맞지 않는 비밀번호 입니다.</span>
			</p>
			<p>
				<label style='font-size:15px;'>비밀번호  확인</label>
				<input type="password" id="password2"name="password2" required="required" placeholder="비밀번호를 재확인해주세요" style="width:300px">
				<span class="error">입력한 비밀번호가 일치하지 않습니다.</span>
			</p>
			<p>
				<label>이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름</label>
				<input type="text" id="name"name="name" required="required" placeholder="이름을 입력하세요" style="width:300px">
				<span class="ex">한글 2~20자 사용 가능합니다.</span>
			</p>
			<p>
				<label>전&nbsp;&nbsp;화&nbsp;&nbsp;번&nbsp;&nbsp;호</label>
				<select name="bkTel1">
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="019">019</option>
						<option value="017">017</option>
					</select>
					-
					<input type="text" required="required" name="bkTel2" placeholder="0000">
					-
					<input type="text" required="required" name="bkTel3" placeholder="0000">
				<span class="ex">잘못된 전화번호입니다.</span>
			</p>
			<p>
				<label>이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;메&nbsp;&nbsp;&nbsp;&nbsp;일</label>
				<input type="text" id ="email" name="email" required="required" placeholder="본인확인 이메일" style="width:300px">
				<br /><span class="form_EX">예)hotdog123@naver.com , hotdog12@naver.co.kr</span>
				<span class="ex">형식에 맞지 않는 email 입니다.</span>
			</p>
			<div id="submit_div">
				<input type="submit" value="가입하기" id="sub" >
			</div>	
		</fieldset>
		
		</form>
		
	</div>
</body>
</html>

