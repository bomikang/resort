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

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/common.js"></script>
<script>
$(function(){
	var reg_uid = /^[a-z0-9_]{5,12}$/; 
	var reg_upw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-]|.*[0-9]).{6,24}$/; //6~16자 영문대소문자, 숫자, 특수문자 혼합하여 사용
	var reg_name = /^[가-힣]{2,20}$/;
	var reg_tel = /^(010|016|011)\d{3,4}\d{4}$/;
	var reg_mail = /^\w{5,12}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,3}$/;
	
 	$("form[name='f1']").submit(function() { // submit 버튼 클릭스 아이디 공란 Check,password 재학인 조건문 
         if(!checkInputEmpty($("input[name]"))){
            return false;
         }   
         if($("input[name='password']").val() 
               != $("input[name='password2']").val()){
            var $next = $("input[name='password2']").nextAll(".error").eq(1);
            $next.css("display","block");
            return false;
         }  
         
        	 if(reg_uid.test($("#id").val())==false){
        		 
                 $(".ex").eq(0).css("display","block");
        	
        		 return false;
        	 }
			if(reg_upw.test($("#password").val())==false){
        		 
                 $(".ex").eq(1).css("display","block");
        	
        		 return false;
        	 } 
			if(reg_name.test($("#name").val())==false){
       		 
                $(".ex").eq(2).css("display","block");
       	
       		 return false;
       	 }
        	 
			if(reg_tel.test($("#tel").val())==false){
       		 
                $(".ex").eq(3).css("display","block");
       	
       		 return false;
       	 }
			if(reg_mail.test($("#email").val())==false){
       		 
                $(".ex").eq(4).css("display","block");
       	
       		 return false;
       	 }
       
       });
	
	
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
						}else{
							alert("사용불가 아이디 입니다.");
						}	
			} 
		});
	});
	
	
});

</script>
<script type="text/javascript">



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
				<label>아이디 : </label>  <input type="text" name="id" id="id" placeholder="아이디를 입력하세요" > 
				<span class="error">ID를 입력하세요.</span><span class="ex">형식에 맞지 않는 ID 입니다.</span>
				<button id="btn" type="button">아이디중복</button>
				
				
			</p>
			<p>
				<label>암호 : </label><input type="password" id ="password"name="password" placeholder="비밀번호를 입력하세요">
				<span class="error">비밀번호 입력하세요.</span><span class="ex">형식에 맞지 않는 비밀번호 입니다.</span>
			</p>
			<p>
				<label>비밀번호 재확인 :</label><input type="password" name="password2" placeholder="비밀번호를 재확인해주세요">
				<span class="error">비밀번호를 입력하세요.</span>
				<span class="error">입력한 비밀번호가 일치하지 않습니다.</span>
			</p>
			
		</fieldset><br>
		
		<fieldset>
			<p>
				<label>이름 :</label><input type="text" id="name"name="name" placeholder="이름을 입력하세요">
				<span class="error">이름을 입력하세요</span><span class="ex">형식에 맞지 않는 이름입니다.</span>
			</p>
			<p>
				<label>전화번호 : </label><input type="text" id="tel"name="tel" placeholder="예)010-xxxx-xxxx">
				<span class="error">전화번호를 입력하세요</span><span class="ex">형식에 맞지 않는 전화번호 입니다.</span>
			</p>
			<p>
				<label>E-Mail :</label><input type="text" id ="email" name="email" placeholder="본인확인 이메일">
				<span class="error">본인확인에 필요한 이메일을 입력해주세요</span><span class="ex">형식에 맞지 않는 e-mail 입니다.</span>
			</p>
			
		</fieldset>
		
		<div id="submit_div">
			<input type="submit" value="가입하기" id="sub" >
		</div>
		
		</form>
		
	</div>
</body>
</html>

