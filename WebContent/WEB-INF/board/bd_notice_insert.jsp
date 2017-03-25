<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>

</script>
<div id="notice_insert">
	<h2>공지사항 등록</h2>
		<form action="noticeinsert.do" method="post" name="addNotice">
				<fieldset>
				<p>
					<label for="title">제목 : </label>
					<input type="text" required="required" name="title" id="title">
				</p>
				<p>
					<label for="detail">내용 : </label>
					<textarea rows="" cols="" name="detail" id="detail"></textarea>
				</p>
				<button type="button" id="btnAdd">등  록</button>
				<button type="button" id="btnBack">취  소</button>
			</fieldset>
		</form>

</div>