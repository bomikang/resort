<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		
		
	}
	

	$(function(){
		$('.sliderImages').bxSlider({
			slideWidth : 200,
			minSlides : 1,
			maxSlides : 3,
			slideMargin : 10
		});
		
		$(document).on("click", ".sliderImages .slide img", function() {
			$("#bigImage img").attr("src", $(this).attr("src"));
		});
	});
</script>
</head>
<body>
	<p id="bigImage">
			<!-- bigImage -->
	</p>
	<div class="sliderImages">
		<div class="slide">
			<img src="image/arround/01market.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/02exercise.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/03square.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/04valley.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/05field.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/06pool.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/07kids_area.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/08pond.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/09mineral.jpg" alt="" />
			<span>aaa</span>
		</div>
		<div class="slide">
			<img src="image/arround/10soccer.jpg" alt="" />
			<span>aaa</span>
		</div>
	</div>
</body>
</html>