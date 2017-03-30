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
.errors{color:red;padding:0;display:block;font-size: 12px;}
.errors{text-align: left;}

.way_top h2{color:#fff; position:relative; left:30px; top:5px;}
fieldset{width:500px; margin:0 auto; margin-top:70px; padding-top:20px; padding-bottom:50px;background:rgba(255,255,255,0.9); border-radius: 20px; box-shadow: 1px 1px 0px 1px black;}
fieldset p{width:380px;}
fieldset p label{  margin-right:16px;}
fieldset p label img{width:40px; position:relative; top:16px;}
fieldset p input{width:300px !important; height:31px !important; border-radius: 15px; background:#929292; color:#fff !important; border:1px solid rgba(0,0,0,0) !important;}
::-webkit-input-placeholder{color:#fff;}
fieldset #sub{background:#FFBB83 !important; width:370px; border-radius: 15px; height:46px; color:#fff; font-family:"NanumSquareB"; font-size:20px;}
 .bottom_btn p{font-size:14px; width:500px; margin:0 auto; margin-top:30px;} 


</style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript">

</script>
<body>
<div class="way_top">
	<h2>눈꽃자연휴양림 로그인</h2>
</div>
<div id="login">
	<form action="login.do" method="post" name="f1">
		<fieldset>
			<p>
				<label><img src="image/icon_id_gray.png" alt="" /></label>
				<input type="text" name="id" id="id" placeholder="아이디" required="required"> 

				<c:if test="${notJoin}">
					<span class="errors">아이디가  틀렸습니다.</span>
					<script type="text/javascript">
						$(function(){
							$("#id").focus();
						});
					</script>
				</c:if>
				<c:if test="${outId }">
					<span class="errors">아이디를 입력하세요.</span>
					<script type="text/javascript">
						$(function(){
							$("#id").focus();
						});
					</script>
				</c:if>
				<c:if test="${no_member }">
					<span class="errors">탈퇴한 회원의 아이디입니다.</span>
					<script type="text/javascript">
						$(function(){
							$("#id").focus();
						});
					</script>
				</c:if> 
			</p>
			<p>
				<label><img src="image/icon_pwd_gray.png" alt="" /></label>
				<input type="password" name="password" id="password" placeholder="비밀번호" required="required"> 
				 <c:if test="${outPass }">
				<span class="errors">비밀번호를 입력하세요.</span>
				<script type="text/javascript">
					$(function(){
						$("#password").focus();
					});
				</script>
				</c:if>
				<c:if test="${notPass }">
				<span class="errors">비밀번호가 틀렸습니다.</span>
				<script type="text/javascript">
					$(function(){
						$("#password").focus();
					});
				</script>
				</c:if>
			</p>
			
			<div id="submit_div" class='act_btn_area'>
				<input type="submit" value="로그인" id="sub" >
			</div>
			
		</fieldset>
		</form>
		<div class='bottom_btn'>
				<p>아이디나 비밀번호가 기억나지 않으세요?
					<a href="loginsearch.do?key=id"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;아이디</b></a>
					&nbsp;&nbsp;/&nbsp;&nbsp;<a href="loginsearch.do?key=password"><b>비밀번호 찾기</b></a></p>
				<p>아직 눈꽃자연휴양림의 고객이 아니신가요?<a href="join.do"><b>&nbsp;&nbsp;&nbsp;&nbsp;회원가입</b></a></p>
			</div>
	</div>
</body>
</html>