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
	.room_menu{text-align: center; margin-bottom:20px;}
	.room_menu li{display: inline-block;}
	.bigImage{text-align: center; padding-top:20px; margin-bottom:30px;}
	.bigImage img{min-width:610px; max-width:610px; max-height:410px; min-height:410px;box-shadow: 0px 0px 10px 5px gray;}
	#room_name .go_book{width:130px;}
	.str_people_btn{text-align: center;}
	.str_people_btn a{margin:5px 10px; padding:3px 0; border-bottom:3px solid rgba(0,0,0,0);}
	#furniture{font-weight: bold;}
</style>
<script>
	/* bxSlider 적용 함수 */
	function gallery(showImages){
		var errCount = 0;
		
		$(".bigImage").html("<img src='"+showImages[0]+"'>"); //큰이미지
		
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
		
		$(".bigImage img").on("error",function(){ //로드가 안되면 이미지없다고 뿌리기
			$(this).attr("src", "image/no_image.jpg");
			$("#slideGallery").html("");
		});
	
	}
	
	/* 방 정보 테이블 생성 */
	function roomInfoTable(data){
		//table 생성
		var table = "<tr>";
			table += "<th rowspan='2'>규모</th>";
			table += "<th rowspan='2'>수용인원</th>";
			table += "<th rowspan='2'>사용기간</th>";
			table += "<th colspan='2'>객실요금</th>";
			table += "<th rowspan='2'>비고</th>";
		table += "</tr>";
		table += "<tr>";
			table += "<td>성수기</td>";
			table += "<td>비수기</td>";
		table += "</tr>";
		table += "<tr>";
			table += "<td>"+data.width+"</td>";
			table += "<td>"+data.people+"인실</td>";
			table += "<td>1일</td>";
			table += "<td>"+data.originPriceToString+"</td>";
			table += "<td>"+data.lessPriceToString+"</td>";
			table += "<td>무료 주차</td>";
		table += "</tr>";
		
		$(".room_info_area #room_name h2 span").html(data.nameById +" 이용안내");
		$(".room_info_area > table").html(table);
		$(".room_info_area #furniture").html(data.option);
	}//roomInfoTable
	
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
				
				//table 생성 함수 호출
				roomInfoTable(data);
				
				//수용 인원 button 활성화 호출
				if (data.people == 4) {
					$("#forest_btn").find("a").eq(0).css({"border-color":"#dd7028", "font-weight":"bold"});
					$("#sanrim_btn").find("a").eq(0).css({"border-color":"#dd7028", "font-weight":"bold"});
				}
				if(data.people == 6){
					$("#forest_btn").find("a").eq(1).css({"border-color":"#dd7028", "font-weight":"bold"});
				}
				if(data.people == 8){
					$("#forest_btn").find("a").eq(2).css({"border-color":"#dd7028", "font-weight":"bold"});
					$("#sanrim_btn").find("a").eq(1).css({"border-color":"#dd7028", "font-weight":"bold"});
				}
				if(data.peope == 17){
					$("#sanrim_btn").find("a").eq(2).css({"border-color":"#dd7028", "font-weight":"bold"});
				}
			}
		});
	}
	$(function(){
		var str_no = 0; //방 번호
		
		if (str_no == 0) {
			getStructures("${firstRoomNo}"); //첫번째 시설 번호 넘겨줌
		}
		
		$(".room_menu li button").each(function(i, obj) {
			$(obj).click(function() {
				str_no = $(obj).find("span").text(); //시설번호
				
				getStructures(str_no);
			});
		});
		
		$(document).on("click", ".sliderImages .slide img", function() {
			var thisImg = $(this).attr("src");
			
			$(".bigImage").find("img").fadeOut(300, function() {
				$(".bigImage").find("img").attr("src", thisImg);
			}).fadeIn(300);
		});
	});
</script>
</head>
<body>

<div class="way_top">
	<c:if test="${empty param.houseId}">
		<h3>숲속의 집<br /><span>홈 > 시설현황 > 숲속의 집</span></h3>
	</c:if>
	<c:if test="${param.houseId == 1}">
		<h3>숲속의 집<br /><span>홈 > 시설현황 > 숲속의 집</span></h3>
	</c:if>
	<c:if test="${param.houseId == 2}">
		<h3>산림휴양관<br /><span>홈 > 시설현황 > 산림휴양관</span></h3>
	</c:if>
	<c:if test="${param.houseId == 3}">
		<h3>캐라반<br /><span>홈 > 시설현황 > 캐라반</span></h3>
	</c:if>
	<c:if test="${param.houseId == 4}">
		<h3>돔하우스<br /><span>홈 > 시설현황 > 돔하우스</span></h3>
	</c:if>
</div>
<div class="intro_padding">
	<c:if test="${strList.size() == 0}">
		<p>등록된 방이 없습니다</p>
	</c:if>
	<c:if test="${strList.size() > 0}">
		<c:if test="${param.houseId == 1 or param.houseId == null}"> <!-- 숲속의 집 -->
			<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>숲속의 집</h2>
			<p class='str_people_btn' id='forest_btn'>
				<a href="structure.do?people=4">4인실</a>
				<a href="structure.do?people=6">6인실</a>
				<a href="structure.do?people=8">8인실</a>
			</p>
			<ul class="room_menu">
				<c:forEach var="rooms" items="${strList}">
					<li><button>${rooms.name}<span class="str_no" style="display:none;">${rooms.no}</span></button></li>
				</c:forEach>
			</ul>
			<hr />
		</c:if>
		<c:if test="${param.houseId == 2}">
			<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>산림휴양관</h2>
			<p class='str_people_btn' id='sanrim_btn'>
				<a href="structure.do?people=4&houseId=2" class='structure_people_btn'>4인실</a>
				<a href="structure.do?people=8&houseId=2" class='structure_people_btn'>8인실</a>
				<a href="structure.do?people=17&houseId=2" class='structure_people_btn'>17인실</a>
			</p>
		</c:if>
		<c:if test="${param.houseId == 3}">
			<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>캐라반</h2>
		</c:if>
		<c:if test="${param.houseId == 4}">
			<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>돔하우스</h2>
		</c:if>
		<p class="bigImage">
			<!-- bigImage -->
		</p>
		<p id="slideGallery">
			<!-- gallery -->
		</p>
		<div class="room_info_area">
			<div id="room_name">
				<h2>
					<img src="image/icon_flower_orange.png" class='icon_flower'/>
					<span><!-- OOOO 이용안내 --></span>
		
					<c:if test="${!empty param.houseId}">
						<a href="book.do?id=${param.houseId}" class="moving_btn go_book">예약하러 가기</a>
					</c:if>
					<c:if test="${empty param.houseId}">
						<a href="book.do?id=1" class="moving_btn go_book">예약하러 가기</a>
					</c:if>
				</h2>
			</div>
			
			<table></table>
			
			<h2><img src="image/icon_flower_orange.png" class='icon_flower'/>내부 시설 안내</h2>
			<ul class="inner_content">
				<li><p id="furniture"></p></li>
				<li>
					<p id="furniture_notice">
					※ 구비물품은 기준인원에 맞추어 구비되어 있으며 추가지급은 안됩니다. <br />
					※ 최대인원을 초과하여서는 입실이 안되며, 애완동물(애완견) 동반시 이용 불가합니다. <br /> 
     				&nbsp;&nbsp;&nbsp;이와 같은 사유로 객실을 이용하지 못할 경우 사용자 과실로 처리되며, 환불기준에 의거하여 환불되오니 이용에 착오없으시기 바랍니다.
			</p></li>
			</ul>
			
			
		</div>
	</c:if>
</div>
</body>
</html>