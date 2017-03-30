<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#mem_search .btnsP{text-align: center;}
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
	<h3>아이디 / 비밀번호 찾기</h3>
</div>

<div id="mem_search" class='intro_padding'>
	<c:if test="${!empty user_info }">
		<%response.sendRedirect("index.jsp"); %>
	</c:if>
	<c:if test="${key=='id'}">
		<form action="loginsearch.do" method="post">
			<fieldset>
				<legend>아이디 찾기</legend>
				<p>회원정보에 기재된 이름과 전화번호를 입력해 주세요.</p>
				<p>
					<label for="name">이름 : </label>
					<input type="text" name="name" id="name" required="required">
				</p>
				<p>
					<label>전화번호 : </label>
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
			<fieldset>
				<p>본인의 아이디와 이름, 전화번호를 입력해 주세요. 아이디가 기억나지 않는경우 <a href="loginsearch.do?key=id">아이디 찾기</a>를 이용해 주세요.</p>
				<legend>비밀번호 찾기</legend>
				<p>
					<label for="id">아이디 : </label>
					<input type="text" name="id" id="id" required="required">
				</p>
				<p>
					<label for="name">이름 : </label>
					<input type="text" name="name" id="name" required="required">
				</p>
				<p>
					<label>전화번호 : </label>
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