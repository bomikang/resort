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
	#intro_gallery_top{text-align: center; font-family: "SeoulHangangM"; font-size:30px; color:#2e2e2e;}
	#show_image{width:100%; text-align: center; margin:30px 0;}
	#show_image img{width:600px; box-shadow: 0px 0px 5px 3px black;}
	#show_image p{font-size:22px; color:#2e2e2e;}
	.resort_gallery{width:100%; text-align: center; margin:0 auto;}
	.resort_gallery li{ display: inline-block; margin:10px; opacity:0.7; text-align: center;}
	.resort_gallery li img{width:200px; height:140px; border:2px dotted black;  border-radius: 10px;}
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

<div class="way_top">
	<h2>눈꽃자연휴양림 갤러리<span>홈 > 휴양림 소개 > 눈꽃자연휴양림 갤러리</span></h2>
</div>

<div class='intro_padding'>
	<h1 id='intro_gallery_top'>
		<img src="image/icon_flower_orange.png" class='icon_flower'/>
		눈꽃자연휴양림의 다양한 곳을 감상해보세요
		<img src="image/icon_flower_orange.png" class='icon_flower'/>
	</h1>
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
</div>
</body>
</html>