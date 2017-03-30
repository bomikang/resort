<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(function(){
		$("#btnAdd").click(function(){
			console.log("등록 클릭");
		
			<c:if test="${!empty user_info}">
				<c:if test="${user_info.isMng == true}">
					if(confirm("공지글을 등록하시겠습니까?")){
						$("form[name='addNotice']").submit();
					}else{
						return false;
					}
				</c:if>
				<c:if test="${user_info.isMng == false}">
					alert("접근권한이 없습니다.");
					location.href="notice.do?page=1";
				</c:if>
			</c:if>
			<c:if test="${empty user_info}">
				alert("접근권한이 없습니다.");
				location.href="login.do?category=notice";
			</c:if>
		});
		
		$("#btnBack").click(function(){
			var url="";
			<c:if test="${!empty index}">
				url="noticedetail.do?page=${index}&no=${notice.no}";
			</c:if>
			
			<c:if test="${empty index}">
				url = "notice.do?page=1";
			</c:if>
			
			location.href = url;
		});
		
		$("#btnUpd").click(function(){
			<c:if test="${!empty user_info}">
				<c:if test="${user_info.isMng == true}">
					if(confirm("공지글을 수정하시겠습니까?")){
						$("form[name='updNotice']").submit();
					}else{
						return false;
					}
				</c:if>
				<c:if test="${user_info.isMng == false}">
					alert("접근권한이 없습니다.");
					location.href="notice.do?page=1";
				</c:if>
			</c:if>
			<c:if test="${empty user_info}">
				alert("접근권한이 없습니다.");
				location.href="login.do?category=notice";
			</c:if>
		});
		
		$("#btnDel").click(function(){
			<c:if test="${!empty user_info}">
				<c:if test="${user_info.isMng == true}">
					if(confirm("공지글을 삭제하시겠습니까?")){
						location.href="noticedetail.do?page=${index}&numbers=${notice.no}";
					}else{
						return false;
					}
				</c:if>
				<c:if test="${user_info.isMng == false}">
					alert("접근권한이 없습니다.");
					location.href="notice.do?page=1";
				</c:if>
			</c:if>
			<c:if test="${empty user_info}">
				alert("접근권한이 없습니다.");
				location.href="login.do?category=notice";
			</c:if>
		});
	});//ready
</script>
<div id="notice_insert" class='intro_padding'>
	
	<c:if test="${type=='insert' }">
		<h2>공지사항 등록</h2>
		<hr />
		<form action="noticeinsert.do" method="post" name="addNotice">
			<fieldset>
				<p>
					<label for="title">제목</label>
					<input type="text" required="required" name="title" id="title">
				</p>
				
				<p>
					<label for="detail">내용</label><br />
					<textarea rows="" cols="" name="detail" id="detail"></textarea>
				</p>
				<p>
					<label for="real">공지여부</label>
					<input type="checkbox" name="real" id="real">
				</p>
				<p class='act_btn_area'>
					<button type="button" id="btnAdd">등  록</button>
					<button type="button" id="btnBack">취  소</button>
				</p>
			</fieldset>
		</form>
	</c:if>
	<c:if test="${type=='update' }">
		<h2>공지사항 수정</h2>
		<hr />
		<form action="noticeupdate.do" method="post" name="updNotice">
			<fieldset>
				<input type="hidden" name="nNo" value="${notice.no }">
				<input type="hidden" name="index" value="${index}">
				<p>
					<label for="title">제목</label>
					<input type="text" required="required" name="title" id="title" value="${notice.title }">
				</p>
				<p>
					<label for="detail">내용</label>
					<textarea rows="" cols="" name="detail" id="detail">${detail.detail }</textarea>
				</p>
				<p>
					<label for="real">공지여부</label>
					<c:if test="${notice.isState()==true }">
						<input type="checkbox" name="real" id="real" checked="checked">
					</c:if>
					<c:if test="${notice.isState()==false }">
						<input type="checkbox" name="real" id="real">
					</c:if>
				</p>
				<p class='act_btn_area'>
					<button type="button" id="btnUpd">수  정</button>
					<button type="button" id="btnDel">삭  제</button>
					<button type="button" id="btnBack">취  소</button>
				</p>
			</fieldset>
		</form>
	</c:if>
</div>