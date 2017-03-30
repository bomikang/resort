<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.getSession().getAttribute("user_info");

%>

<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#repDelBtn {color: blue;}
#top_table tr th:NTH-CHILD(1), #rep_table tr td:NTH-CHILD(1) {width: 130px;}
#top_table tr th:NTH-CHILD(2), #rep_table tr td:NTH-CHILD(2) {width: 700px;}
</style>
<script language=javascript>
var userinfo2 = "<%= request.getSession().getAttribute("user_info") %>";
</script>
<script type="text/javascript">
 document.write(userinfo2); 
 
	var rep_no = 0;
	var temp = 0;
	$(function() {
		$.ajax({
		url : "rev_reply.do",
		type : "post",
		timeout : 30000,
		dataType : "json",
		data : {
			"rev_no" : $("#rev_no").val()
		},//게시글의 번호
		success : function(res) {
			console.log(res);
			var table = $("#rep_table");
			$(res.data)
					.each(function(i, obj) {
						
						var tr = $("<tr>");//<tr></tr>
						var td = $("<td>").html(obj.rep_name);
						var td2 = $("<td>").html(obj.rep_detail);
						var td3 = $("<td>").html(obj.rep_regdate + "<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'>");
						tr.append(td).append(td2).append(td3);/* .append("<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'></button>"); */
						<!--.append("<button>수정");
						-->//<tr><td>rep_no</td></tr>
						rep_no = Number(obj.rep_no);
						table.append(tr);
					});
		}
		});

		$("#replyBtn").click(function() {
			if ($("#rep_write").val() == "") {
				alert("댓글 내용을 입력해주세요.");
				return false;
			} else {
				$.ajax({
				url : "rev_replyInsert.do",
				type : "post",
				timeout : 30000,
				dataType : "json",
				data : {
				"rev_no" : $("#rev_no").val(),
				"rep_write" : $("#rep_write").val()
				},
				success : function(res) {
					console.log(res);
					var table = $("#rep_table");
					table.empty();
					$(res.data).each(function(i, obj) {
						var tr = $("<tr>");//<tr></tr>
						var td = $("<td>").html(obj.rep_name);
						var td2 = $("<td>").html(obj.rep_detail);
						var td3 = $("<td>")
								.html(obj.rep_regdate + "<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'>");
						tr.append(td).append(td2).append(td3)/* .append("<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'></button>"); */
						//<tr><td>rep_no</td></tr>
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
			url : "rev_replyDelete.do",
			type : "post",
			timeout : 30,
			dataType : "json",
			data : {
			"rep_no" : rep_no,
			"rev_no" : $("#rev_no").val()
			},
			success : function(res) {
				console.log(res);
				var table = $("#rep_table");
				table.empty();
				$(res.data).each(function(i, obj) {
					var tr = $("<tr>");//<tr></tr>
					var td = $("<td>").html(obj.rep_name);
					var td2 = $("<td>").html(obj.rep_detail);
					var td3 = $("<td>")
							.html(obj.rep_regdate + "<button class='repDelBtn'>삭제<input type='hidden' value='"+obj.rep_no+"' class='rep_no'>");
					tr.append(td).append(td2).append(td3);
					//<tr><td>rep_no</td></tr>
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
			<td>${rev_list.rev_title }</td>
		</tr>
		<tr>
			<td>${rev_list.rev_name }</td>
		</tr>
		<tr>
			<td>${rev_detail.rev_detail }</td>
		</tr>
	</table>
	<c:if test="${user_info.my_name.equals(rev_list.rev_name)}">
		<span class="ok"><a href="rev_delete.do?no=${rev_list.rev_no }"
			id="delete">삭제</a></span>
		<span class="ok"><a href="rev_update.do?no=${rev_list.rev_no }">글
				수정</a></span>
	</c:if>
	<hr>
	<input type="hidden" name="rev_no" id="rev_no"
		value="${rev_list.rev_no }">
	<p>[나도 한마디]</p>
	<textarea rows="7" cols="100"
		placeholder="무단광고,이유 없는 악플 등은 삭제될 수 있습니다." id="rep_write"
		name="rep_write" required="required"></textarea>
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