<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
#mem_search .btnsP{text-align: center;}
.top_notice{color:#cc0000;}

::-webkit-input-placeholder{color:#fff;}
.login_join_field p{width:390px;}
.login_join_field input[name='bkTel2'], .login_join_field input[name='bkTel3']{width:102px !important; border-radius: 10px; height:23px !important;}
.login_join_field label{margin-right:12px !important;}
.login_join_field legend{text-align: center; font-size:22px; color:#333;}
</style>
<script>
	$(function(){
		$("#btnCancel").click(function(){
			if(confirm("취소하시겠습니까?")){
				location.href="login.do";	
			}		
		});
	});//ready
</script>

<div class="way_top">
	<h2>아이디 / 비밀번호 찾기</h2>
</div>

<div id="mem_search" class='intro_padding'>
	<c:if test="${!empty user_info }">
		<%response.sendRedirect("index.jsp"); %>
	</c:if>
	<c:if test="${key=='id'}">
		<form action="loginsearch.do" method="post">
			<fieldset class='login_join_field'>
				<legend>아이디찾기</legend>
				<p class='top_notice'><small>회원정보에 기재된 이름과 전화번호를 입력해 주세요.</small></p>
				<p>
					<label for="name">이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름</label>
					<input type="text" name="name" id="name" required="required">
				</p>
				<p>
					<label>전화번호</label>
					<select name="bkTel1">
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="019">019</option>
						<option value="017">017</option>
					</select>
					-
					<input type="text" required="required" name="bkTel2">
					-
					<input type="text" required="required" name="bkTel3">				
				</p>
				<p class="btnsP">
					<input type="submit" id="btnSearch" value="아이디 찾기">
					<button id="btnCancel">취 소</button>
				</p>
			</fieldset>
		</form>
	</c:if>
	<c:if test="${key=='password'}">
		<form action="loginsearch.do" method="post">
			<fieldset class='login_join_field'>
				<legend>비밀번호찾기</legend>
				<p class='top_notice'>
					<small>본인의 아이디와 이름, 전화번호를 입력해 주세요. 아이디가 기억나지 않는경우<b><a href="loginsearch.do?key=id">아이디 찾기</a></b>를 이용해 주세요.</small>
				</p>
				<p>
					<label for="id">아&nbsp;&nbsp;이&nbsp;디</label>
					<input type="text" name="id" id="id" required="required">
				</p>
				<p>
					<label for="name">이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름</label>
					<input type="text" name="name" id="name" required="required">
				</p>
				<p>
					<label>전화번호</label>
					<select name="bkTel1">
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="019">019</option>
						<option value="017">017</option>
					</select>
					-
					<input type="text" required="required" name="bkTel2">
					-
					<input type="text" required="required" name="bkTel3">				
				</p>
				<p class="btnsP">
					<input type="submit" id="btnSearch" value="비밀번호 찾기">
					<button id="btnCancel">취 소</button>
				</p>
			</fieldset>
		</form>
	</c:if>
	
</div>