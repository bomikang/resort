<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 입력 창(관리자용) -->
<script>
	$(function(){
		$("#btnUpdate").click(function(){
			<c:if test="${!empty admin}">		
				if(!confirm("수정 하시겠습니까?")){
					return false;
				}else{
					$("form[name='updFaq']").submit();
				}
			</c:if>	
			<c:if test="${empty admin}">
				alert("관리자 계정으로 로그인해 주세요.");
				locaion.href="faq.do";
			</c:if>
		});
		$("#btnDelete").click(function(){
			<c:if test="${!empty admin}">		
				if(!confirm("삭제 하시겠습니까?")){
					return false;
				}else{
					$("form[name='dltFaq']").submit();
				}
			</c:if>			
			<c:if test="${empty admin}">
				alert("관리자 계정으로 로그인해 주세요.");
				locaion.href="faq.do";
			</c:if>
		});
		
		$("#btnAdd").click(function(){
			<c:if test="${!empty admin}">
				if(!confirm("등록 하시겠습니까?")){
					return false;
				}else{
					$("form[name='addFaq']").submit();
				}
			</c:if>
			<c:if test="${empty admin}">
				alert("관리자 계정으로 로그인해 주세요.");
				locaion.href="faq.do";
			</c:if>
		});
		
		$("#btnBack").click(function(){
			location.href="faq.do";
		});		
	
	});
</script>
<div id="faqDetail">
	<c:if test="${empty admin }">
		<script type="text/javascript">
			alert("관리자만 접근 가능합니다.");
			location.href="login.do"; 
		</script>
	</c:if>
	<c:if test="${type=='update' }">
		<h2>FAQ 수정</h2>
		
		<form action="faqupdate.do" method="post" name="updFaq">
			<input type="hidden" name="fNo" value=${faq.no }>
			<fieldset>
				<p>
					<label for="title">제목 : </label>
					<input type="text" required="required" name="title" id="title" value=${faq.title }>
				</p>
				<p>
					<label for="detail">내용 : </label>
					<textarea rows="" cols="" name="detail" id="detail">${faq.detail }</textarea>
				</p>
				<button type="button" id="btnUpdate">수  정</button>
				<button type="button" id="btnDelete">삭  제</button>
				<button type="button" id="btnBack">취  소</button>
			</fieldset>
		</form>
		<form action="faqdelete.do" method="get" name="dltFaq">
			<input type="hidden" name="fNo" value=${faq.no }>
		</form>
	</c:if>
	<c:if test="${type!='update' }">
		<h2>FAQ 등록</h2>
		<form action="faqinsert.do" method="post" name="addFaq">
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
	</c:if>
		<!-- <fieldset>
			<p>
				<label for="title">제목 : </label>
				<input type="text" required="required" name="title" id="title">
			</p>
			<p>
				<label for="detail">내용 : </label>
				<textarea rows="" cols="" name="detail" id="detail"></textarea>
			</p>
			<input type="submit" value="등록하기">
			<input type="reset" value="취  소">
		</fieldset>
	</form> -->
	<a href="faq.do">[돌아가기]</a>
</div>