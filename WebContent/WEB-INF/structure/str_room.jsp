<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("utf-8");
	/* 서버에 업로드 된 사진 불러오기  */
		//http://localhost:8080//resort/Structure_Images/xxx.png => jsp는 이렇게 가져오나 봄
		//request.getServerName() : localhost
		//request.getServerPort() : 8080
		//getServletContext().getContextPath() : /resort
	String path = getServletContext().getContextPath(); // => /resort
	String test = "http://"+ request.getServerName() +":"+ request.getServerPort() + "/" + path;
	
%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	$(function(){
		var images = "";
		var repImage = "";
		
		$.ajax({
			url:"structure.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"no":1},
			success:function(data){
				images = data.image;
				
				var oneImages = images.split("/"); //image쪼개기
				repImage = oneImages[0]; //대표이미지
				
				/* ********여기서부터가 중요********* */
				//http://localhost:8080//resort/Structure_Images/"+ repImage+ "
				//var test = "/<%=path%>"+"/Structure_images/logo2.png"
				$("#test").html("<img src='<%=test%>/Structure_Images/"+ repImage+ "'>");
			}
		});
		
		
		$("button").click(function() {
			$("#test").html($("#a").attr("src"));
		});
	});
</script>
</head>
<body>
	<c:if test="${strList.size() == 0}">
		<p>등록된 방이 없습니다</p>
	</c:if>
	<c:if test="${strList.size() > 0}">
		<ul>
			<c:forEach var="rooms" items="${strList}">
				<li>${rooms.name}</li>
			</c:forEach>
		</ul>
	</c:if>
	<p id="test">
		
	</p>
</body>
</html>