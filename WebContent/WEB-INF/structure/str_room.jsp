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
	String fullPath = "http://"+ request.getServerName() +":"+ request.getServerPort() + "/" + path;
	
%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/jquery.bxslider.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.bxslider.min.js"></script>
<style>
	.room_menu li{display: inline-block; border:1px solid black;}
</style>
<script>
	function getStructures(str_no){
		$.ajax({
			url:"structure.do",
			type:"post",
			timeout:30000,
			dataType:"json",
			data:{"no":str_no},
			success:function(data){
				var images = data.image;
				
				var oneImages = images.split("/"); //image쪼개기
				
				/* ********여기서부터가 중요********* */
				//http://localhost:8080//resort/Structure_Images/"+ repImage+ "
				
				var showImages = new Array();
				
				for (var i = 0; i < oneImages.length; i++) {
					showImages[i] = "<%=fullPath%>/Structure_Images/"+oneImages[i];
				}
				//bxSlider 적용 함수 호출
				gallery(showImages);
			}
		});
	}
	
	/* bxSlider 적용 함수 */
	function gallery(showImages){
		$("#bigImage").html("<img src='"+showImages[0]+"'>"); //큰이미지
		
		var slideImage = "<div class='sliderImages'>";
		
		for (var i = 0; i < showImages.length; i++) {
			slideImage += "<div class='slide'>";
			slideImage += "<img src='"+showImages[i]+"'>";
			slideImage += "</div>";
		}
		
		$("#slideGallery").html(slideImage);
		
		$('.sliderImages').bxSlider({
			slideWidth : 200,
			minSlides : 1,
			maxSlides : 3,
			slideMargin : 10
		});
	}
	

	$(function(){
		var str_no = 0; //방 번호
		
		if (str_no == 0) {
			getStructures("${firstRoomNo}"); //첫번째 시설 번호 넘겨줌
		}
		
		$(".room_menu li a").each(function(i, obj) {
			$(obj).click(function() {
				str_no = $(obj).find("span").text();
				
				getStructures(str_no);
			});
		});
		
		$(document).on("click", ".sliderImages .slide img", function() {
			$("#bigImage img").attr("src", $(this).attr("src"));
		});
	});
</script>
</head>
<body>
	<c:if test="${strList.size() == 0}">
		<p>등록된 방이 없습니다</p>
	</c:if>
	<c:if test="${strList.size() > 0}">
		<c:if test="${param.houseId == 1 or param.houseId == null}"> <!-- 숲속의 집 -->
			<a href="structure.do?people=4">4인실</a>
			<a href="structure.do?people=6">6인실</a>
			<a href="structure.do?people=8">8인실</a>
			<ul class="room_menu">
				<c:forEach var="rooms" items="${strList}">
					<li><a href="#">${rooms.name}<span class="str_no" style="display:none;">${rooms.no}</span></a></li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${param.houseId == 2}">
			<a href="structure.do?people=4&houseId=2">4인실</a>
			<a href="structure.do?people=8&houseId=2">8인실</a>
			<a href="structure.do?people=12&houseId=2">12인실</a>
		</c:if>
		<p id="bigImage">
			<!-- bigImage -->
		</p>
		<p id="slideGallery">
			<!-- gallery -->
		</p>
	</c:if>
</body>
</html>