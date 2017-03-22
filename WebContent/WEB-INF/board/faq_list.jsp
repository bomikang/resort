<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- FAQ 메인 화면(가능하면 수정, 삭제도 이 화면에서 구성예정. 물론, 가능하다면...) -->
<%
/*캐시에 Data를 남기지 않는구문(로그아웃 이후 뒤로가기 Data기록 안남기기 위해 사용)  */
response.setHeader("cache-control","no-store");
response.setHeader("expires","0");
response.setHeader("pragma","no-cache");
%>
<style>
	#faqList .faqDetail{display: none; border:1px dotted orange;}
	#faqList .faqTitleDt{border:1px solid red; width:100%;}
	#faqList .faqTitleDt a{width:100%;padding:10px;}
</style>
<script>
	$(function(){
		$("#btnAdd").click(function(){
			<c:if test="${!empty admin}">
				location.href="faqinsert.do";
			</c:if>
		});
		
		$(document).on("click", ".faqCheck", function(){
			console.log($(this).val());
		});
		$(document).on("click", ".btnEdit", function(){
			var fNo = $(this).prev("input[type='hidden']").val();
			var url = "faqupdate.do?fNo="+fNo;
			location.href=url;
		});
		
		$(".faqTitle").click(function(){		
			$(this).parents("dt").next(".faqDetail").slideToggle();
		});
		
		$("#btnRmv").click(function(){
			var chcList = new Array();
			$(".faqCheck").each(function(i, obj) {
				if($(obj).prop("checked")==true){
					chcList.push($(obj).val());
				}
			});
			if(chcList.length==0){
				alert("선택된 항목이 없습니다.");
			}else{
				if(confirm("정말 삭제 하시겠습니까?")){
					var numbers = chcList.join(",");
					$("#numbers").val(numbers);
					$("#delete").submit();			
				}else{
					return false;
				}
			}
		});
	});
</script>
<div id="faqList">
	<h2>자주 묻는 질문</h2>
	<table border="1">
		<c:choose>
			<c:when test="${!empty fList }">
				<c:forEach items="${fList }" var="fItem">
					<tr>
						<td>
							<dl>
								<dt class="faqTitleDt">
									<c:if test="${!empty admin }">
										<input type="checkbox" name="faqCheck" value="${fItem.no }" class="faqCheck">
									</c:if>
									<a href="#" class="faqTitle">${fItem.title }</a>
								</dt>
								<dd class="faqDetail">
									<p>${fItem.detail }</p>
									<c:if test="${!empty admin }">
										<p><input type="hidden" name="fNo" value="${fItem.no }">
										<button class="btnEdit">수정</button></p>
									</c:if>	
								</dd>
							</dl>
						</td>
					</tr>
				</c:forEach>				
			</c:when>
			<c:otherwise>
				<tr><td>등록된 게시글이 존재하지 않습니다.</td></tr>
			</c:otherwise>
		</c:choose>
	</table>
	<c:if test="${!empty admin}">
		<button id="btnAdd">글 등록</button>
		<button id="btnRmv">글 삭제</button>
	</c:if>
	<form action="faqdelete.do" method="post" id="delete">
		<input type="hidden" name="numbers" id="numbers">
	</form>
</div>
