<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 입력 창(관리자용) -->
<style>
	#faqDetail .btnsP{text-align: center;}
</style>
<script>
	$(function(){
		$("#btnUpdate").click(function(){
			<c:if test="${!empty user_info}">
				<c:if test="${user_info.isMng == true }">		
					if(!confirm("수정 하시겠습니까?")){
						return false;
					}else{
						$("form[name='updFaq']").submit();
					}
				</c:if>
				<c:if test="${user_info.isMng == false }">
					alert("관리자 계정으로 로그인해 주세요.");
					locaion.href="faq.do";
				</c:if>
			</c:if>
			<c:if test="${!empty user_info}">
				alert("관리자 계정으로 로그인해 주세요.");
				locaion.href="login.do?category=faq";
			</c:if>
			
		});
		$("#btnDelete").click(function(){
			<c:if test="${!empty user_info}">
				<c:if test="${user_info.isMng == true }">	
				
					if(!confirm("삭제 하시겠습니까?")){
						return false;
					}else{
						$("form[name='dltFaq']").submit();
					}
					
				</c:if>	
				<c:if test="${user_info.isMng == false }">
					alert("관리자 계정으로 로그인해 주세요.");
					locaion.href="faq.do";
				</c:if>
			</c:if>						
		});
		
		$("#btnAdd").click(function(){
			<c:if test="${!empty user_info}">
				<c:if test="${user_info.isMng == true }">	
					if(!confirm("등록 하시겠습니까?")){
						return false;
					}else{
						$("form[name='addFaq']").submit();
					}
					
				</c:if>
				<c:if test="${user_info.isMng == false }">	
					alert("관리자 계정으로 로그인해 주세요.");
					locaion.href="faq.do";
				</c:if>
			</c:if>			
		});
		
		$("#btnBack").click(function(){
			var title = $("#faqDetail h2").text().split(" ")[1];
			if(confirm(title+"을 취소하고 이전화면으로 돌아가시겠습니까?")){
				location.href="faq.do";
			}
		});		
	
	});
</script>
<div id="faqDetail" class='intro_padding'>

	<c:if test="${!empty user_info}">
		<c:if test="${user_info.isMng == false }">	
			<script type="text/javascript">
				alert("관리자만 접근 가능합니다.");
				location.href="login.do?category=faq"; 
			</script>
		</c:if>
	</c:if>
	<c:if test="${type=='update' }">
		<h2>FAQ 수정</h2>
		<hr />
		<form action="faqupdate.do" method="post" name="updFaq">
			<input type="hidden" name="fNo" value=${faq.no }>
			<fieldset>
				<p>
					<label for="title">제목</label>
					<input type="text" required="required" name="title" id="title" value=${faq.title }>
				</p>
				<p>
					<label for="detail">내용</label>
					<textarea rows="" cols="" name="detail" id="detail">${faq.detail }</textarea>
				</p>
				<p class='btnsP'>
				<button type="button" id="btnUpdate">수  정</button>
				<button type="button" id="btnDelete">삭  제</button>
				<button type="button" id="btnBack">취  소</button>
				</p>
			</fieldset>
		</form>
		<form action="faqdelete.do" method="get" name="dltFaq">
			<input type="hidden" name="fNo" value=${faq.no }>
		</form>
	</c:if>
	<c:if test="${type!='update' }">
		<h2>FAQ 등록</h2>
		<hr />
		<form action="faqinsert.do" method="post" name="addFaq">
				<fieldset>
				<p>
					<label for="title">제목</label>
					<input type="text" required="required" name="title" id="title">
				</p>
				<p>
					<label for="detail">내용</label>
					<textarea rows="" cols="" name="detail" id="detail"></textarea>
				</p>
				<p class='btnsP'>
				<button type="button" id="btnAdd">등  록</button>
				<button type="button" id="btnBack">취  소</button>
				</p>
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

</div>