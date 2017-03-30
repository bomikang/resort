<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#notice_table{margin-top:20px;}
	#notice_detail #notice_table td{text-align: left; padding-left:10px;} 
	#notice_detail #notice_table #td_writed_date{border-right:none; text-align: right; width:70%;}
	#notice_detail #notice_table #td_writed_man{border-left:none; text-align: right; padding-right: 15px; width:20%;}
	#notice_detail #detail{height: 300px; vertical-align: top;}
	#notice_detail .nTitle{width:100%; word-break:break-all; text-align: center; padding-left: 70px;padding-right: 70px;}
	#notice_detail .nDetail{width:90%; margin:15px auto; white-space: normal; word-break:break-all;}
	#notice_detail	#noticeIcon img{width:20px; float: left;}
	#noticeIcon{float: left; width:25px; margin-left: 10px;margin-right: 10px;}
</style>
<script>
	$(function(){
		$(".nDetail").html("${detail.detail }");
	
		$("#btnBack").click(function(){
			var returnTo = "${returnTo}";
			if(returnTo != null){
				location.href="review.do?page=1";
			}else{
				location.href="notice.do?page=${index}";
			}
		});
		$("#btnUpd").click(function(){
			location.href="noticeupdate.do?page=${index}&no=${notice.no }";
		});
		$("#btnDel").click(function(){
			if(confirm("정말 삭제하시겠습니까?")){
				location.href="noticedelete.do?page=${index}&numbers=${notice.no }";
			}
		});
		$("#btnReal").click(function(){
			<c:if test="${notice.isState()==true }">
				if(confirm("공지를 해지하실 경우 공지 목록 상단에 노출되지 않습니다. 계속 진행하시겠습니까?")){
					$("form[name='updState']").submit();
				}
			</c:if>
			<c:if test="${notice.isState()==false }">
				if(confirm("이 글을 공지로 지정하실 경우 공지 목록 상단에 고정됩니다. 공지로 지정하시겠습니까?")){
					$("form[name='updState']").submit();
				}
			</c:if>			
		});
	});//ready
</script>
<div class="way_top">
	<h3>공지사항<br /><span>홈 > 자유게시판 > 공지사항</span></h3>
</div>
<div id="notice_detail" class='intro_padding'>
	<h2><img alt="" src="image/Notice.png" id="noticeIcon">공지사항</h2>
	<table id="notice_table">
		<c:if test="${!empty notice }">
			<c:if test="${!empty detail }">
				<tr>
					<th colspan="2" class="nTitle">
						
						<c:if test="${notice.isState()==true }">
							<b id="noticeIcon"><img alt="" src="image/Notice.png"></b>
						</c:if>
						${notice.title }
						
					</th>
				</tr>
				<tr>
					<td id="td_writed_date">작성일자 : ${notice.regDateTimeForm }</td>
					<td id="td_writed_man">작성자 : ${notice.writer.my_name } / 조회수 : ${notice.readCnt }</td>
				</tr>
				<tr>
					<td colspan="2" id="detail">
						<p class="nDetail"></p>
						<!-- 공지사항 내용부분 -->
					</td>
				</tr>
			</c:if>
		</c:if>	
	</table>
	<p id="btns" class='act_btn_area'>
		<c:if test="${!empty user_info }">
			<c:if test="${user_info.isMng==true }">
				<button id="btnUpd">수정</button>
				<button id="btnDel">삭제</button>
				<c:if test="${notice.isState()==true }">
					<button id="btnReal">공지해제</button>
				</c:if>
				<c:if test="${notice.isState()==false }">
					<button id="btnReal">공지등록</button>	
				</c:if>		
			</c:if>
		</c:if>
		<button id="btnBack">돌아가기</button>
	</p>
	<form action="noticedetail.do" method="post" hidden="hidden" name="updState">
		<input type="hidden" name="nNo" value="${notice.no }">
	</form>
</div>