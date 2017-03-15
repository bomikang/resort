<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
</script>
<style>
	.error{
	
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
</head>
<body>
<div id="title">
		<h1> 로그인 </h1>
	</div>
	<div id="login">
	<form action="login.do" method="post" name="f1">
	
		<fieldset>
			<p>
				<label>아이디 : </label>  <input type="text" name="id" id="id" placeholder="아이디" > 
				<c:if test="${notJoin }">
				<span class="error">아이디가 없습니다.</span>
				</c:if>
				<c:if test="${no_member }">
				<span class="error">탈퇴한 회원의 아이디입니다.</span>
				</c:if> 
			</p>
			<p>
				<label>비밀번호 : </label>  <input type="password" name="password" id="password" placeholder="비밀번호" > 
				 <span class="error">비밀번호를 입력하세요</span>
				<c:if test="${notPass }">
				<span class="error">비밀번호가 틀렸습니다.</span>
				</c:if>
			</p>
		</fieldset>
		
		<div id="submit_div">
			<input type="submit" value="로그인" id="sub" >
		</div>
		
		</form>
		
	</div>
</body>
</html>