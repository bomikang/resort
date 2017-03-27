<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style>
	#show_image{width:50%; text-align: center;}
	#show_image img{width:600px; box-shadow: 0px 0px 10px 5px gray;}
	.resort_gallery{width:50%; border:1px solid black; text-align: left;}
	.resort_gallery li{ display: inline-block; margin:10px; opacity:0.5; text-align: center;}
	.resort_gallery li img{width:200px; height:140px; border:3px solid black;  border-radius: 10px;}
</style>
<script>
	$(function(){
		$(".resort_gallery").find("li").each(function(i, obj) {
			$(obj).mouseover(function() {
				$(this).css("opacity", "1");
			});
			
			$(obj).mouseout(function() {
				$(this).css("opacity", "0.5");
			});
			
			$(obj).click(function() {
				var thisImg = $(this).find("img").attr("src");
				var thisDesc = $(this).find("span").text();
				
				$("#show_image").find("img").fadeOut(300, function() {
					$("#show_image").find("img").attr("src", thisImg);
					$("#show_image").find("p").text(thisDesc);
				}).fadeIn(300);
			});
			
		});
	});
</script>
<body>
	<div id="show_image">
		<img src="image/resortgallery/01waterfall.jpg" alt="" />
		<p>계곡</p>
	</div>
	<ul class="resort_gallery">
		<li><img src="image/resortgallery/01waterfall.jpg"><br><span>계곡</span></li>
		<li><img src="image/resortgallery/02playforest.jpg"><br><span>어린이 놀이숲</span></li>
		<li><img src="image/resortgallery/03foresthouse.jpg"><br><span>숲속의 집 전경</span></li>
		<li><img src="image/resortgallery/04forestroad.jpg"><br><span>산책로1</span></li>
		<li><img src="image/resortgallery/05forestroad2.jpg"><br><span>산책로2</span></li>
		<li><img src="image/resortgallery/06sanlim.jpg"><br><span>산림휴양관 복도</span></li>
		<li><img src="image/resortgallery/07entry.jpg"><br><span>옥성자연휴양림 입구</span></li>
		<li><img src="image/resortgallery/08greengrass.jpg"><br><span>잔디광장</span></li>
		<li><img src="image/resortgallery/09greengrass2.jpg"><br><span>잔디광장2</span></li>
	</ul>
</body>
</html>