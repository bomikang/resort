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
	/* display:none; */
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
#submit{
	width : 330px;	
	margin: 0 auto;
}
#sub{
	width : 330px;
	height: 45px;
	margin-top: 20px;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">

	$(function(){
		
		$("#btn").click(function(){
			/* var url = "join.do?id="+id;    
			$(location).attr('href',url); */
			var id=$("#id").val();
			location.href="join.do?id="+id;	
		});
		
	});
		
		
	
</script>
</head>
<body>
	<div id="title">
		<h1> 회원가입 </h1>
	</div>
	<div id="join">
	<form action="join.do" method="post">
	
		<fieldset>
			<p>
				<label>아이디 : </label>  <input type="text" name="id" id="id" placeholder="아이디를 입력하세요"> 
				<button id="btn" type="button">아이디중복</button> <br>
				<c:if test = "${error}">
					<span class="error">중복된 아이디 입니다.</span>
				</c:if>
			</p>
			<p>
				<label>암호 : </label><input type="password" name="password" placeholder="비밀번호를 입력하세요"><br>
				<span class="error">암호를 입력하세요</span>
			</p>
			<p>
				<label>비밀번호 재확인 :</label><input type="password" name="password2" placeholder="비밀번호를 재확인해주세요"><br>
				<span class="error">일치하지 않습니다</span>
			</p>
			
		</fieldset><br>
		
		<fieldset>
			<p>
				<label>이름 :</label><input type="text" name="name" placeholder="이름을 입력하세요"><br>
			</p>
			<p>
				<label>주소 : </label><input type="text" name="address" placeholder="예)대구광역시 북구"><br>
				<span class="error">주소를 입력하세요</span>
			</p>
			<p>
				<label>전화번호 : </label><input type="text" name="tel" placeholder="예)010-xxxx-xxxx"><br>
				<span class="error">전화번호를 입력하세요</span>
			</p>
			<p>
				<label>E-Mail :</label><input type="text" name="email" placeholder="본인확인 이메일"><br>
				<span class="error">본인확인에 필요한 이메일을 입력해주세요</span>
			</p>
			
		</fieldset>
		
		<div id="submit">
			<input type="submit" value="가입하기" id="sub">
		</div>
		
		</form>
		
	</div>
</body>
</html>

