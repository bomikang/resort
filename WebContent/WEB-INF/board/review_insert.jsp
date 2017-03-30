<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<title>Insert title here</title>
<style>
	a.style_from_input{width: 100px !important;}
</style>
</head>
<body>
<div class="way_top">
	<h3>후기<br /><span>홈 > 자유게시판 > 후기</span></h3>
</div>
<div class="intro_padding">
	<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>후기 등록</h2>
	<form action ="rev_insert.do" method = "post" name="f1">
		<fieldset>
			<p>
				<label>제목</label>
				<input type="text" name="title">
			</p>
			<p>
				<label>내용</label>
				<textarea rows="10" cols="30" name="content"></textarea>
			</p>
			<p class='act_btn_area'>
				<input type="submit" value="등록">
				<a href="#" onclick="location.replace('review.do')" class='moving_btn'>목록</a>
			</p>
		</fieldset>
	</form>
</div>
</body>
</html>