<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style>
	.err{display:none; color:red; font-size:12px;}
	input[name='title']{width:300px;}
</style>
<script>
	function checkRegExr($input, limit){
		$($input).keyup(function() {
			if ( $(this).val().length > limit-1 ) {
				$(this).next(".err").css("display", "block"); //err 메시지 표신
				$(this).val($(this).val().substring(0, limit)); //넘어온 제한 글자수 만큼 짜름
			}else{
				$(this).next(".err").css("display", "none");
			}
		});
	}//checkRegExr
	
	$(function(){
		var memTextArea = "${qna.content}";
		if (memTextArea != "") {
			var memTextAreaArr = memTextArea.split("<br>");
			var realMemTextArea = "";
			for (var i = 0; i < memTextAreaArr.length; i++) {
				realMemTextArea += memTextAreaArr[i]+"\r";
			}
			$("#memContent").val(realMemTextArea);
		}
		
		checkRegExr($("input[name='title']"), 50);
		checkRegExr($("textarea[name='content']"), 400);
		
		$("#btnUpdate").click(function() {
			/* 1. 에러 메시지가 보이는 지 체크 */
			if ($(".err").css("display") == "block") return false;
			
			/* 2. confirm창 */
			var check = confirm("게시글을 수정하시겠습니까?");
			if ( !check ) return false;
		});
		
		$("#btnCancel").click(function() {
			var check = confirm("게시글 수정을 취소하시겠습니까?");
			if ( check ) location.replace("qnadetail.do?qnano=${qna.no}");
			else return false;
		});
	});
</script>
</head>
<body>
	<form action="qnaupdate.do" method="post" name="f1">
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
				<span class="err">더 이상 입력이 불가능 합니다.</span>
			</p>
			<p>
				<label for="">내용</label>
				<textarea name="content" required="required" id="memContent"></textarea>
				<span class="err">더 이상 입력이 불가능 합니다.</span>
			</p>
			<p>
				<input type="hidden" name="qnano" value="${qna.no}" />
				<input type="submit" value="수정" id="btnUpdate"/>
				<input type="button" value="취소" id="btnCancel" /><!-- detail화면으로 -->
			</p>
		</fieldset>
	</form>
</body>
</html>