<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="qnaupdate.do" method="get">
		<fieldset>
			<legend>1:1 문의 상세보기</legend>
			<p>
				<label for="">작성자</label>
				<input type="text" name="name" value="${qna.member.name}" readonly="readonly"/>
			</p>
			<p>
				<label for="">이메일</label>
				<input type="text" name="email" value="${qna.member.mail}" readonly="readonly"/>
			</p>
			<p>
				<label for="">제목</label>
				<input type="text" name="title" value="${qna.title}" readonly="readonly"/>
			</p>
			<p>
				<label for="">내용</label>
				<textarea name="content" cols="100" rows="10" readonly="readonly">${qna.content}</textarea>
			</p>
			<p>
				<input type="hidden" name="qnano" value="${qna.no}" /><!-- 게시물 번호 hidden으로 심기 -->
				<input type="submit" value="수정" />
				<input type="button" value="목록보기" onclick="location.replace('qna.do')" /><!-- list화면으로 이동 -->
			</p>
		</fieldset>
	</form>
</body>
</html>