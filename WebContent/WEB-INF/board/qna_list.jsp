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
	.pageBtnArea{display:inline-block;}
	#goFirst, #goLast{width:20px; background:none; border:none;}
	.paging_btn_num{background:none; border:none; width:18px;}
</style>
<script>
	function createQnaTable(data, $tableName){
		///////////////////////////////////////////////각 페이지 버튼 눌렀을 때 페이지 분리
		var listSize = data.length; //리스트사이즈
		
		/*ex) 리스트가 33개면 4페이지*/
		var btnPageNum = 1; // X페이지 숫자 버튼(1페이지부터 시작)
		if( listSize/10 != 0 ) btnPageNum = listSize/10; 
		if( listSize%10 != 0 ) btnPageNum = btnPageNum+1;  //나머지가 존재하면 1페이지 더하기
		btnPageNum = parseInt(btnPageNum);
		
		/* 페이지번호에 뿌리기 */
		var aBtn = "";
		for (var i = 0; i < btnPageNum; i++) {
			aBtn += "<button class='paging_btn_num'>"+ (i+1) +"</button>";
		}
		$(".pageBtnArea").html(aBtn);
		
		/* 기본 테이블 만들기 */
		var tableItem = "<tr>";
			tableItem += "<th>번호</th><th>제목</th><th>등록일</th><th>작성자</th><th>답변여부</th>";
			tableItem += "</tr>";
			if (listSize < 1) {
				tableItem += "<tr><td colspan='5'>게시물이 없습니다.</td></tr>";
			}
			else{
				for (var i = 0; i < listSize; i++) {
					if (i > 9) {
						break;
					}
					tableItem += "<tr>";
					tableItem += "<td>"+ data[i].no +"</td>"; //번호
					tableItem += "<td><a href='qnadetail.do?qnano="+ data[i].no +"'>"+ data[i].title +"</a></td>"; //제목
					tableItem += "<td>"+ data[i].regDateNoTimeForm +"</td>"; //등록일
					tableItem += "<td>"+ data[i].member.id +"</td>";
					tableItem += "<td>"+ data[i].stringReply +"</td>";
					tableItem += "</tr>";
				}
			}
		$($tableName).html(tableItem);
		
	}//createQnaTable
	
	function createTableByClickedNum(data, $tableName){
		var listSize = data.length; //리스트사이즈
		
		/* 페이지번호 누름 */
		$(".pageBtnArea").find("button").each(function(i, obj) {
			$(obj).click(function() {
				var aIndex = (i+1);
				var minSize = (aIndex * 10) - 10; //초기 리스트 0번째 부터
				var maxSize = (aIndex * 10); //[1] => 0~9번째, 총 10개
				
				//10개 미만 || 리스트개수보다 최대개수가 더 많으면 최대개수를 리스트개수로
				if( listSize<11 ) maxSize = listSize;
				else if( maxSize-listSize > 0 ) maxSize = listSize;
				
				var tableItem = "<tr>";
					tableItem += "<th>번호</th><th>제목</th><th>등록일</th><th>작성자</th><th>답변여부</th>";
					tableItem += "</tr>";
				if (listSize < 1) {
					tableItem += "<tr><td colspan='5'>게시물이 없습니다.</td></tr>";
				}else{
					for (var a = minSize; a < maxSize; a++) {
						tableItem += "<tr>";
						tableItem += "<td>"+ data[a].no +"</td>"; //번호
						tableItem += "<td><a href='qnadetail.do?qnano="+ data[a].no +"'>"+ data[a].title +"</a></td>"; //제목
						tableItem += "<td>"+ data[a].regDateNoTimeForm +"</td>"; //등록일
						tableItem += "<td>"+ data[a].member.id +"</td>";
						tableItem += "<td>"+ data[a].stringReply +"</td>";
						tableItem += "</tr>";
					}
				}
				$tableName.html(tableItem);
			});
			
		});
	}//createTableByClickedNum

	function getQnaList(checkReply, $tableName){
		$.ajax({
			url:"qna.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"checkReply":checkReply},
			success:function(data){
				createQnaTable(data, $tableName);
				createTableByClickedNum(data, $tableName);
			}
		});
	}//getQnaList

	$(function(){
		if($("#memNum").text() == "0") getQnaList("justList", $("#mem_table")); //리스트 읽어오기
		else  getQnaList("justList", $("#admin_table"));
		
		//답변 미완료 목록 매개변수 : incomplete
		//답변 완료 목록 매개변수 : complete
		//게시글 전체 보기 매개변수 : all
		$("#incomp_list").click(function() {
			getQnaList("incomplete", $("#admin_table"));
		});
		$("#comp_list").click(function() {
			getQnaList("complete", $("#admin_table"));
		});
		$("#all_list").click(function() {
			getQnaList("all", $("#admin_table"));
		});
		
		// <<버튼 클릭 / >>버튼 클릭
		$("#goFirst").click(function() {
			$(".pageBtnArea").find("button").eq(0).click();
		});
		$("#goLast").click(function(){
			$(".pageBtnArea").find("button").last().click();
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
				<span id="memNum" style="display:none;">0</span><!-- 일반 회원은 0번으로 보냄 <= ajax사용하기 위함 -->
				<table id="mem_table"></table>
			</c:if>
		</c:if>
		
		<!-- 관리자 -->
		<c:if test="${user_info.isMng == true }">
			<button id="incomp_list">답변 미완료 목록</button>
			<button id="comp_list">답변 완료 목록</button>
			<button id="all_list">전체 목록</button>
			
			<c:if test="${qnaList.size() == 0}">
				<p>등록된 게시물이 없습니다</p>
			</c:if>
			<c:if test="${qnaList.size() > 0}">
				<span id="memNum" style="display:none;">1</span><!-- 관리자는 1번으로 보냄 <= ajax사용하기 위함 -->
				<table id="admin_table"></table>
			</c:if>
		</c:if>
	</c:if>
	
	<div class="pageDivArea">
		<button id="goFirst"class='paging_btn'><img src="image/paging_left2.png" alt="" /></button>
		<div class='pageBtnArea'></div>
		<button id="goLast"class='paging_btn'><img src="image/paging_right2.png" alt="" /></button>
	</div>
</body>
</html>