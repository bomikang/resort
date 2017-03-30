<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.getSession().getAttribute("user_info");
%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	#write{width:200px; height:17px; position:relative; top:-2px;}
	#searchBtn{width:50px; background:#43493e; padding:5px 0;}
	#searchBtn:HOVER{color:#43493e; border-color:#43493e; background:#fff;}
	.btn_right{text-align: right;}
	.btn_right .style_from_input{display:inline-block; width:100px; margin-bottom:20px; text-align: center;}
	table th:NTH-CHILD(1){width:80px;}
	table th:NTH-CHILD(3){width:120px;}
	table th:NTH-CHILD(4){width:120px;}
	table th:NTH-CHILD(5){width:80px;}
	table td:NTH-CHILD(2){text-align: left; padding-left:15px;}
	
	#paging{text-align: center;}
	
	.intro_padding .realNotice td{background: rgba(255,255,0,0.5);}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
	$("#searchBtn").click(function(){
		var serVal= $("#search").val();
		var writeVal = $("#write").val(); 
		location.href = "rev_search.do?select="+serVal+"&write="+writeVal;
	});	
});			
</script>
</head>
<body>
<div class="way_top">
	<h3>후기<br /><span>홈 > 자유게시판 > 후기</span></h3>
</div>
<div class="intro_padding">
	<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>후기</h2>
	<table>
		<tr>
			<th><b>게시물 번호</b></th>
			<th><b>제목</b></th>
			<th><b>작성자</b></th>
			<th><b>작성날짜</b></th>
			<th><b>조회수</b></th>
		</tr>
		<c:if test="${!empty rnList }">
			<c:forEach items="${rnList }" var="real">
				<tr class="realNotice">
					<c:if test="${!empty user_info }">		
						<c:if test="${user_info.isMng==true }">
							<th></th>
						</c:if>
					</c:if>
					<td>공지</td>
					<td class='nTitle'><a href="noticedetail.do?page=${index.getNowIndex() }&no=${real.no }&category=review" class="toNoticeDetail">${real.title }</a></td>
					<td>${real.writer.my_name }</td>
					<td>${real.regDateForm }</td>
					<td>${real.readCnt }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${cntPage.hasNoArticles() }">
			<tr>
				<td colspan="5">게시글이 없습니다.</td>
			</tr>
		</c:if>	
		
		<c:forEach var="i"  items="${cntPage.content }">
			<tr>
				<td>${i.rev_no }</td>
				<td><a href="rev_detail.do?no=${i.rev_no }">${i.rev_title }(${i.rev_replycnt })</a></td>
				<td>${i.rev_name }</td>
				<td>${i.rev_regdate }</td>
				<td>${i.rev_readcnt }</td>
				
			</tr>
		</c:forEach>
		
	</table>
	<p id="paging">
			<c:if test="${cntPage.hasArticles() }">
				<c:if test="${cntPage.startPage > 5 }">
					<a href="review.do?pageNo=${cntPage.startPage -5 }" class='paging_btn'><img src="image/paging_left1.png" alt="" /></a>
				</c:if>
				<c:forEach var="pNo" begin="${cntPage.startPage}"
					end="${cntPage.endPage }">
					<a href="review.do?pageNo=${pNo }" class='paging_btn_num'>${pNo}</a>
				</c:forEach>
				<c:if test="${cntPage.endPage<cntPage.totalPages }">
					<a href="review.do?pageNo=${cntPage.startPage+5 }" class='paging_btn'><img src="image/paging_left2.png" alt="" /></a>
				</c:if>
			</c:if>
		</p>
	<p class="act_btn_area">
		<label for="">검색 :</label>
		<select id="search">
			<option value="title">제목</option>
			<option value="name">작성자</option>
			<option value="number">게시글번호</option>
			<option value="date">날짜</option>
		</select>
		<input type="text" id="write" required="required">
		<button id="searchBtn">검색</button>
	</p>
	
	<c:if test="${!empty user_info }">
		<p class='btn_right'><a href="rev_insert.do" class='style_from_input' id="btn_insert">게시글 등록</a></p>
	</c:if>	
</div>
</body>
</html>