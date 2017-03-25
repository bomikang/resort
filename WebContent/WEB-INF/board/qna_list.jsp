<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	function createQnaTable(data){
		var table = "<tr>";
			table += "<th>번호</th><th>제목</th><th>등록일</th><th>작성자</th><th>답변여부</th>";
			table += "</tr>";
			for (var i = 0; i < data.length; i++) {
				table += "<tr>";
				table += "<td>"+ data[i].no +"</td>"; //번호
				table += "<td><a href='qnadetail.do?qnano="+ data[i].no +"'>"+ data[i].title +"</a></td>"; //제목
				table += "<td>"+ data[i].regDateNoTimeForm +"</td>"; //등록일
				table += "<td>"+ data[i].member.id +"</td>";
				table += "<td>"+ data[i].stringReply +"</td>";
				table += "</tr>";
			}
		$("#qna_table").html(table);
	}//createQnaTable(data)

	function getQnaList(checkReply){
		$.ajax({
			url:"qna.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"checkReply":checkReply},
			success:function(data){
				createQnaTable(data);
			}
		});
	}//getQnaList(checkReply)

	$(function(){
		//답변 미완료 목록 매개변수 : incomplete
		//답변 완료 목록 매개변수 : complete
		//게시글 전체 보기 매개변수 : all
		/* 답변 미완료 목록 */
		$("#incomp_list").click(function() {
			getQnaList("incomplete");
		});
		/* 답변 완료 목록 */
		$("#comp_list").click(function() {
			getQnaList("complete");
		});
		/* 게시글 전체 보기 */
		$("#all_list").click(function() {
			getQnaList("all");
		});
		
	});
</script>
</head>
<body>
	<!-- 손님 -->
	<c:if test="${empty user_info}">
		<script>
			location.href="login.do";
		</script>
	</c:if>
	
	<c:if test="${!empty user_info}">
		<!-- 일반회원 -->
		<c:if test="${user_info.isMng == false }">
			<a href="qnainsert.do">게시글 등록</a>
			<c:if test="${qnaList.size() == 0}">
				<p>등록된 게시물이 없습니다</p>
			</c:if>
			<c:if test="${qnaList.size() > 0}">
				<table style="width:500px; text-align:center;">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>등록일</th>
						<th>답변여부</th>
					</tr>
					<c:forEach var="list" items="${qnaList}">
						<tr>
							<td>${list.no}</td>
							<td><a href="qnadetail.do?qnano=${list.no}">${list.title}</a></td>
							<td>${list.regDateNoTimeForm}</td>
							<td>${list.stringReply}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</c:if>
		
		<!-- 관리자 -->
		<c:if test="${user_info.isMng == true }">
			<!-- 밑 세 개 버튼만 ajax로 끌고 옴 -->
			<a href="#" id="incomp_list" style="border:1px solid black;">답변 미완료 목록</a>
			<a href="#" id="comp_list" style="border:1px solid black;">답변 완료 목록</a>
			<a href="#" id="all_list" style="border:1px solid black;">전체 목록</a>
			
			<c:if test="${qnaList.size() == 0}">
				<p>등록된 게시물이 없습니다</p>
			</c:if>
			<c:if test="${qnaList.size() > 0}">
				<table id="qna_table" style="width:500px; text-align:center;">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>등록일</th>
						<th>작성자</th>
						<th>답변여부</th>
					</tr>
					<c:forEach var="list" items="${qnaList}">
						<tr>
							<td>${list.no}</td>
							<td><a href="qnadetail.do?qnano=${list.no}">${list.title}</a></td>
							<td>${list.regDateNoTimeForm}</td>
							<td>${list.member.id}</td>
							<td>${list.stringReply}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</c:if>
	</c:if>
</body>
</html>