<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <% String imagePath = (String) request.getAttribute("imagePath"); %> --%>

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
				//$("#test").html(data.name +"<br>이미지"+ images);
				
				var oneImages = images.split("/"); //image쪼개기
				repImage = oneImages[0]; //대표이미지
				alert(repImage);
				
				/* 원래 path : D:\workspace\.... => 변경 D:/workspace/... */
				var imagePath = "${imagePath}"+"/";
				
				//$("#test").html("<img src='"+ imagePath + repImage+"'>");
			}
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
		<%-- ${imagePath} --%>
		<img src="D:/workspace/workspace_jsp/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/resort/Structure_Images/grass_01_4.jpg" alt="" />
	</p>
</body>
</html>