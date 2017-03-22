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
	<form action="qnaupdate.do" method="post">
		<fieldset>
			<legend>1:1 문의 수정</legend>
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
				<input type="text" name="title" value="${qna.title }" required="required"/>
			</p>
			<p>
				<label for="">내용</label>
				<textarea name="content" cols="100" rows="10" required="required">${qna.content }</textarea>
			</p>
			<p>
				<input type="hidden" name="qnano" value="${qna.no}" />
				<input type="submit" value="수정" id="btnUpdate"/>
				<input type="button" value="취소" onclick="location.replace('qnadetail.do?qnano=${qna.no}')" /><!-- detail화면으로 -->
			</p>
		</fieldset>
	</form>
</body>
</html>