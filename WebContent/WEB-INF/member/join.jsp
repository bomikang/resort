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

<script>
$(function(){
	
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
				<span class="error">ID를 입력하세요.</span>
				<button id="btn" type="button">아이디중복</button> 
				
			</p>
			<p>
				<label>암호 : </label><input type="password" name="password" placeholder="비밀번호를 입력하세요">
				<span class="error">비밀번호 입력하세요.</span>
			</p>
			<p>
				<label>비밀번호 재확인 :</label><input type="password" name="password2" placeholder="비밀번호를 재확인해주세요">
				<span class="error">비밀번호를 입력하세요.</span>
				<span class="error">입력한 비밀번호가 일치하지 않습니다.</span>
			</p>
			
		</fieldset><br>
		
		<fieldset>
			<p>
				<label>이름 :</label><input type="text" name="name" placeholder="이름을 입력하세요">
				<span class="error">이름을 입력하세요</span>
			</p>
			<p>
				<label>전화번호 : </label><input type="text" name="tel" placeholder="예)010-xxxx-xxxx">
				<span class="error">전화번호를 입력하세요</span>
			</p>
			<p>
				<label>E-Mail :</label><input type="text" name="email" placeholder="본인확인 이메일">
				<span class="error">본인확인에 필요한 이메일을 입력해주세요</span>
			</p>
			
		</fieldset>
		
		<div id="submit_div">
			<input type="submit" value="가입하기" id="sub" >
		</div>
		
		</form>
		
	</div>
</body>
</html>

