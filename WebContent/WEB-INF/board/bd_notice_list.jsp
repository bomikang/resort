<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	#notice_list #page_index{text-align: center;}
	#notice_list .realNotice td{background: rgba(255,255,0,0.5);}
	#notice_list .toNoticeDetail{width: 100%; overflow: hidden; white-space: nowrap; color:black; text-decoration: none;}
	
	form[name='searchNotice'] label{color:#43493e;}
	#srcText{width:200px; height:17px; position:relative; top:-2px;}
	#btnSearch{width:50px; background:#43493e; padding:5px 0;}
	#btnSearch:HOVER{color:#43493e; border-color:#43493e; background:#fff;}
</style>
<script>
	$(function(){
		setPageIndex();	//페이지 인덱스 보여주는 함수(수정 완전 필요!)
		$(".pageIndex").click(function(){
			<c:if test="${!empty key }">
				var key = "${key}";
				var index = $(this).text();
				var srcCon = $("select[name='srcCon']").val();
				$("#index").val(index);
				$("#srcText").val(key);
				
				$("form[name='searchNotice']").attr("action","notice.do");
				$("form[name='searchNotice']").submit();
				return false;
			</c:if>
		});
		$("#btnSearch").click(function(){
			var key = $("#srcText").val();
			var paramKey = "${key}";
			var index = "";
			var srcCon = $("select[name='srcCon']").val();
			<c:if test="${empty key }">
				index="1";
			</c:if>
			<c:if test="${!empty key }">
				if(paramKey==key){
					index="${index.getNowIndex() }";
				}else{
					index="1";
				}
			</c:if>
			$("#index").val(index);
			
			$("form[name='searchNotice']").attr("action","notice.do");
		});
		
		$("#btnAdd").click(function(){
			<c:if test="${!empty user_info }">		
				<c:if test="${user_info.isMng==true }">
					location.href="noticeinsert.do";
				</c:if>
				<c:if test="${user_info.isMng==false }">
					alert("관리자만 접근할 수 있습니다.");
					location.href="login.do?category=notice";
				</c:if>
			</c:if>	
		});
		
		$("#btnRmv").click(function(){
			var chcList = new Array();
			$(".noticeCheck").each(function(i, obj) {
				if($(obj).prop("checked")==true){
					chcList.push($(obj).val());
				}
			});
			if(chcList.length==0){
				alert("선택된 항목이 없습니다.");
			}else{
				if(confirm("정말 삭제 하시겠습니까?")){
					var numbers = chcList.join(",");
					var url = "noticedelete.do?page=${index.nowIndex }&numbers="+numbers;
					location.href=url;			
				}else{
					return false;
				}
			}
		});
		
	});//ready
	
	function setPageIndex(){
		/* Page 하단에 index 표시(10개 단위로 끊어 표시 ) */
		<c:if test="${!empty index }">
			var indexForm = "<a class='pageIndex paging_btn' href='notice.do?page=1' title='첫 페이지'><img src='image/paging_left2.png'/></a>";
			if( ${index.getStart()} > 10 ){
				indexForm += "<a class='pageIndex paging_btn' href='notice.do?page=${index.getStart()-10}' title='이전 10페이지'><img src='image/paging_left1.png'/></a>";
			}else{
				indexForm += "<a class='paging_btn'><img src='image/paging_left1.png'/></a>";
			}
			
			for(var i = ${index.getStart()}; i <= ${index.getEnd()}; i++){
				if(i==1){
					if(i == ${index.getNowIndex() }){
						indexForm += "<b><a class='paging_btn_num'>"+i+"</a></b>";
					}else{
						indexForm += "<a class='pageIndex paging_btn_num' href='notice.do?page="+i+"'>"+i+"</a>";	
					}
				}else if(i>1){
					if(i == ${index.getNowIndex() }){
						indexForm += " | <b><a class='paging_btn_num'>"+i+"</a></b>";
					}else{
						indexForm += " | <a class='pageIndex paging_btn_num' href='notice.do?page="+i+"'>"+i+"</a>";
					}
				}
			}
			
			
			if(${index.getEnd()} < ${index.getMaxIndex()}){
				indexForm += "<a class='pageIndex paging_btn' href='notice.do?page=${index.getStart()+10}' title='다음 10페이지'><img src='image/paging_right1.png'/></a>";
			}else{
				indexForm += "<a class='paging_btn'><img src='image/paging_right1.png'/></a>";
			}
			
			indexForm += "<a class='pageIndex paging_btn' href='notice.do?page=${index.getMaxIndex()}' title='마지막 페이지'><img src='image/paging_right2.png'/></a>";
			
			$("#page_index").html(indexForm);
		</c:if>
	}
	
	
</script>
<div id="notice_list">
	<h2>공지사항 List</h2>
	<table border="1">
		<tr>
			<c:if test="${!empty user_info }">		
				<c:if test="${user_info.isMng==true }">
					<th></th>
				</c:if>
			</c:if>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회</th>
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
					<td class='nTitle'><a href="noticedetail.do?page=${index.getNowIndex() }&no=${real.no }" class="toNoticeDetail">${real.title }</a></td>
					<td>${real.writer.my_name }</td>
					<td>${real.regDateForm }</td>
					<td>${real.readCnt }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${!empty nList }">
			<c:forEach items="${nList }" var="notc">
				<tr>
					<c:if test="${!empty user_info }">		
						<c:if test="${user_info.isMng==true }">
							<th><input type="checkbox" name="delNo" value="${notc.no }" class="noticeCheck"></th>
						</c:if>
					</c:if>
					<td>${notc.no }</td>
					<td class='nTitle'><a href="noticedetail.do?page=${index.getNowIndex() }&no=${notc.no }" class="toNoticeDetail">${notc.title }</a></td>
					<td>${notc.writer.my_name }</td>
					<td>${notc.regDateForm }</td>
					<td>${notc.readCnt }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty nList}">
			<c:if test="${!empty user_info }">		
				<c:if test="${user_info.isMng==true }">
					<tr><td colspan="6">공지사항이 없습니다.</td></tr>
				</c:if>
				<c:if test="${user_info.isMng==false }">
					<tr><td colspan="5">공지사항이 없습니다.</td></tr>
				</c:if>
			</c:if>
		</c:if>
	</table>
	<!-- 조건별 검색 -->
	<p id="page_search">
		<form action="" method="get" name="searchNotice">
			<p class="act_btn_area">
				<input type="hidden" name="page" value="" id="index">
				<label for="">검색 :</label> 
				<select name="srcCon">
					<option value="byTitle">제목</option>
				</select>
				<input type="text" size="10" name="key" id="srcText" value="${key }">
				<input type="submit" id="btnSearch" value="검색">
			</p>
		</form>
	</p>
	
	<c:if test="${!empty user_info}">
		<c:if test="${user_info.isMng==true}">
			<p class="act_btn_area">
				<button id="btnAdd">글 등록</button>
				<button id="btnRmv">글 삭제</button>
			</p>
		</c:if>
	</c:if>
	
	<p id="page_index">
	</p>
	<!-- <form action="faqdelete.do" method="post" id="delete">
		<input type="hidden" name="numbers" id="numbers">
	</form>	 -->
</div>