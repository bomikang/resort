<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.pageBtnArea{display:inline-block;}
	#goFirst, #goLast{width:20px; background:none; border:none;}
	.paging_btn_num{background:none; border:none; width:18px;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	function createStructureTable(data){
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
			tableItem += "<th>시설번호</th><th>시설구분</th><th>호수(방이름)</th><th>수용인원</th><th>가격</th>";
			tableItem += "</tr>";
			if (listSize < 1) {
				tableItem += "<tr><td colspan='5'>등록된 시설이 없습니다</td></tr>";
			}else{
				for (var i = 0; i < listSize; i++) {
					if (i > 9) {
						break;
					}
					tableItem += "<tr>";
					tableItem += "<td>"+ data[i].no +"</td>"; 
					tableItem += "<td>"+ data[i].id +"<small>("+ data[i].nameById +")</small></td>";
					tableItem += "<td><a href='structureUpdate.do?no="+data[i].no+"'>"+ data[i].name +"</td>";
					tableItem += "<td>"+ data[i].people +"</td>";
					tableItem += "<td>"+ data[i].originPriceToString +"</td>";
					tableItem += "</tr>";
				}
			}
		$("#structureTable").html(tableItem);
		
	}//createStructureTable
	
	function createTableByClickedNum(data){
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
					tableItem += "<th>시설번호</th><th>시설구분</th><th>호수(방이름)</th><th>수용인원</th><th>가격</th>";
					tableItem += "</tr>";
				
				if (listSize < 1) {
					tableItem += "<tr><td colspan='5'>등록된 시설이 없습니다</td></tr>";
				}else{
					for (var a = minSize; a < maxSize; a++) {
						tableItem += "<tr>";
						tableItem += "<td>"+ data[a].no +"</td>";
						tableItem += "<td>"+ data[a].id +"<small>("+ data[a].nameById +")</small></td>";
						tableItem += "<td><a href='structureUpdate.do?no="+data[a].no+"'>"+ data[a].name +"</td>";
						tableItem += "<td>"+ data[a].people +"</td>";
						tableItem += "<td>"+ data[a].originPriceToString +"</td>";
						tableItem += "</tr>";
					}
				}
				$("#structureTable").html(tableItem);
			});
		});
	}//createTableByClickedNum

	function getStructureList(){
		$.ajax({
			url:"structureList.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			success:function(data){
				createStructureTable(data);
				createTableByClickedNum(data);
			}
		});
	}//getStructureList
	
	$(function(){
		getStructureList();
		
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
	<table id="structureTable"></table>
	
	<div class="pageDivArea">
		<button id="goFirst"class='paging_btn'><img src="image/paging_left2.png" alt="" /></button>
		<div class='pageBtnArea'></div>
		<button id="goLast"class='paging_btn'><img src="image/paging_right2.png" alt="" /></button>
	</div>
</body>
</html>