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
	#intro_gallery_top{text-align: center; font-family: "SeoulHangangM"; font-size:30px; color:#dd7028; margin:40px 0;}
	.room_menu li{display: inline-block; border:1px solid black;}
	.bigImage{text-align: center; padding-top:20px; margin-bottom:30px;}
	.bigImage img{min-width:610px; max-width:610px; max-height:410px; min-height:410px;box-shadow: 0px 0px 10px 5px gray;}
	.bigImage span{font-size:22px; color:#2e2e2e; display: block; padding-top:15px;}
	.slide span{display:block !important; width:100%; text-align: center; padding:10px 0 2px;}
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
			var thisImg = $(this).attr("src");
			var thisText = $(this).next("span").text();
			
			$(".bigImage").find("img").fadeOut(300, function() {
				$(".bigImage").find("img").attr("src", thisImg);
				$(".bigImage span").html(thisText);
			}).fadeIn(300);
		});
	});
</script>
</head>
<body>

<div class="way_top">
	<h3>편의시설<br /><span>홈 > 시설현황 > 편의시설</span></h3>
</div>
<div class="intro_padding">
	<h2 id='intro_gallery_top'>맑은 공기와 편안한 시설, 눈꽃자연휴양림에 있습니다</h2>
	
	<p class="bigImage">
		<img src="image/arround/01market.jpg" alt="" /><br />
		<span>매점</span>
	</p>
	
	<div class="sliderImages">
		<div class="slide">
			<img src="image/arround/01market.jpg" alt="" />
			<span>매점</span>
		</div>
		<div class="slide">
			<img src="image/arround/02exercise.jpg" alt="" />
			<span>운동기구</span>
		</div>
		<div class="slide">
			<img src="image/arround/03square.jpg" alt="" />
			<span>정자</span>
		</div>
		<div class="slide">
			<img src="image/arround/04valley.jpg" alt="" />
			<span>계곡</span>
		</div>
		<div class="slide">
			<img src="image/arround/05field.jpg" alt="" />
			<span>운동장</span>
		</div>
		<div class="slide">
			<img src="image/arround/06pool.jpg" alt="" />
			<span>수영장</span>
		</div>
		<div class="slide">
			<img src="image/arround/07kids_area.jpg" alt="" />
			<span>어린이 놀이터</span>
		</div>
		<div class="slide">
			<img src="image/arround/08pond.jpg" alt="" />
			<span>호수</span>
		</div>
		<div class="slide">
			<img src="image/arround/09mineral.jpg" alt="" />
			<span>약수터</span>
		</div>
		<div class="slide">
			<img src="image/arround/10soccer.jpg" alt="" />
			<span>족구장</span>
		</div>
	</div>
</div>
</body>
</html>