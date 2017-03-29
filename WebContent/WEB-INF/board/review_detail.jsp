<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.getSession().getAttribute("user_info");
%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	#repDelBtn{
	color:blue;
	}
	#top_table tr th:NTH-CHILD(1), #rep_table tr td:NTH-CHILD(1){width:130px;}
	#top_table tr th:NTH-CHILD(2), #rep_table tr td:NTH-CHILD(2){width:700px;}
	
</style>
<script type="text/javascript">
	var rep_no=0;
	var temp = 0;
	$(function(){
		$.ajax({
			url:"rev_reply.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"rev_no":$("#rev_no").val()},//게시글의 번호
			success:function(res){							
				console.log(res);
				var table = $("#rep_table");
				$(res.data).each(function(i, obj) {
					
					
					var tr = $("<tr>");//<tr></tr>
					var td = $("<td>").html(obj.rep_name);
					var td2 = $("<td>").html(obj.rep_detail);
					var td3 = $("<td>").html(obj.rep_regdate+"<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'>");
					tr.append(td).append(td2).append(td3);/* .append("<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'></button>"); */<!--.append("<button>수정");-->//<tr><td>rep_no</td></tr>
					rep_no= Number(obj.rep_no);
					table.append(tr);
				});
			/* made by yujin 오늘 저녁 본인이 작성한 댓글만 삭제가능하도록 하는거 관련한 수정사항이 git에 없어서 혹시나 하는 마음으로 코드만 작성합니다. 테스트도 없어요.
				만약 수정이 되어있다면 이 부분은 가차없이 삭제해 주세요. - 말도없이 주석 달아서 죄송합니다.ㅜㅜ
				댓글 테이블 구성 부분 - 삭제 버튼 내 댓글 등록 회원의 멤버 번호를 가지고 있는 hidden type의 input 태그 삽입
				var tr = $("<tr>");//<tr></tr>
				var td = $("<td>").html(obj.rep_name);
				var td2 = $("<td>").html(obj.rep_detail);
				var td3 = $("<td>").html(obj.rep_regdate+"<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'><input type='hidden' value='"+obj.rep_mem+"' class='mem_no'></button>");
				tr.append(td).append(td2).append(td3);
				rep_no= Number(obj.rep_no);
				table.append(tr);
				ready 내 제이쿼리 부분 - 로그인 정보(user_info)가 비어있지 않고(=로그인 된 상태), 각각 버튼 내 mem_no의 value값과 로그인 정보의 회원번호를 비교해 같지 않을 경우 button을 숨김
				<c:if test="${!empty user_info}">
					$(".repDelBtn").each(function(i,obj){
						var mem_no = ${user_info.my_no};
						if($(obj).find(".mem_no").val()!=mem_no){
							$(obj).css("display","none");
						}
					});
				</c:if>
				혹시나 이 부분이 제대로 적용되지 않을 시 setBtnDel()같이 함수로 만들어 댓글 리스트 불러올떄마다(ajax사용) 마지막으로 만든 메소드를 호출해서 사용하되
				만약.... 그래도 안되면 폐기처분 고고
			*/
			
			} 
		});	
		
		$("#replyBtn").click(function() {
			if($("#rep_write").val()==""){
				alert("댓글 내용을 입력해주세요.");
				return false;
			}else{
			$.ajax({
				url:"rev_replyInsert.do",
				type:"post",
				timeout:30000,
				dataType:"json",
				data:{"rev_no":$("#rev_no").val(),"rep_write":$("#rep_write").val()},
				success:function(res){							
					console.log(res);
					var table = $("#rep_table");
					table.empty();
					$(res.data).each(function(i, obj) {
						var tr = $("<tr>");//<tr></tr>
						var td = $("<td>").html(obj.rep_name);
						var td2 = $("<td>").html(obj.rep_detail);
						var td3 = $("<td>").html(obj.rep_regdate+"<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'>");
						tr.append(td).append(td2).append(td3)/* .append("<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'></button>"); */<!--.append("<button>수정");-->//<tr><td>rep_no</td></tr>
						table.append(tr);
						$("#rep_write").val("");
					});			
				} 
			});
		}
	});
		$(document).on("click", ".repDelBtn", function() {
			var rep_no = $(this).find(".rep_no").val();
			
			$.ajax({
				url:"rev_replyDelete.do",
				type:"post",
				timeout:30,
				dataType:"json",
				data:{"rep_no":rep_no,"rev_no":$("#rev_no").val()},
				success:function(res){
					console.log(res);
					var table = $("#rep_table");
					table.empty();
					$(res.data).each(function(i, obj) {						
						var tr = $("<tr>");//<tr></tr>
						var td = $("<td>").html(obj.rep_name);
						var td2 = $("<td>").html(obj.rep_detail);
						var td3 = $("<td>").html(obj.rep_regdate+"<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'>");
						tr.append(td).append(td2).append(td3);<!--.append("<button>수정");-->//<tr><td>rep_no</td></tr>
						table.append(tr);
					
					});	
				} 
			});
		});
	});
	
</script>
</head>
<body>	
	<table>
	<tr>
		<td> ${rev_list.rev_title }</td>
	</tr>		
	<tr>
		<td>${rev_list.rev_name }</td>
	</tr>
	<tr>
		<td>${rev_detail.rev_detail }</td>
	</tr>	
</table>
	<c:if test="${user_info.my_name.equals(rev_list.rev_name)}">
		<span class="ok"><a href="rev_delete.do?no=${rev_list.rev_no }" id="delete">삭제</a></span>
		<span class="ok"><a href="rev_update.do?no=${rev_list.rev_no }">글 수정</a></span>
	</c:if>
	<hr>
	<input type="hidden" name="rev_no" id="rev_no" value="${rev_list.rev_no }">
	<p>[나도 한마디]</p>
	<textarea rows="7" cols="100" placeholder="무단광고,이유 없는 악플 등은 삭제될 수 있습니다." id="rep_write" name="rep_write" required="required"></textarea>
	<button id="replyBtn">등록</button>

		<table id="top_table">
			<tr>
				<th>아이디</th>
				<th>내용</th>
				<th>날짜</th>
			</tr>	
		</table>
		<table id="rep_table">
			
		</table>
	

</body>
</html>