/**
 * 
 */

$(function(){
	//$(".content_rep_img > div").animate({height:"120px", opacity:"0.3"}, "slow");
	//$(".content_rep_img > div").animate({width:"100%", opacity:"1"}, "slow");
	
	/*
	var sub_menu = new Array(4);
	sub_menu[0] = ["옥성원 소개글", "옥성원 갤러리", "찾아오시는 길"];
	sub_menu[1] = ["예약안내", "예약하기", "예약조회 및 취소"];
	sub_menu[2] = ["숙박시설", "주변시설", "전체 시설 조감도"];
	sub_menu[3] = ["공지사항", "자주 묻는 질문", "1:1 문의", "후기"];
	
	$("#menu_area ul li a").each(function(i, obj) {
		//show sub menus when mouse over
		$(obj).on("mouseover focus", function() {
			var sub_item = [];
			for (var j = 0; j < sub_menu[i].length; j++) {
				sub_item[j] = "<li><a href='#'>"+ sub_menu[i][j] +"</a></li>";
				
			}
			$("#menu_area ol").html(sub_item);
			$("#menu_area ol").stop(true).slideDown();
			$("#menu_area ol").on("mouseover focusin", function() {
				$("#menu_area ol").stop(true).slideDown();
			});
		});
		//hide sub menus when mouse out
		$(obj).on("mouseout blur", function() {
			$("#menu_area ol").stop(true).slideUp();
			$("#menu_area ol").on("mouseout focusout", function() {
				$("#menu_area ol").stop(true).slideUp();
			});
		});
	});
*/
});


//header fixed on top
$(window).scroll(function() {
	//$("#showScroll").html($(this).scrollTop() +", "+ $(".header_menu div #menu_area").width());
	if ($(this).scrollTop() > 201){
		$(".header_menu div").addClass("stickyHeader");
		$("#menu_area ul").finish().css("padding", "25px 0");
        $("#menu_area ul li a").finish().css("font-size", "20px");
        $("#menu_area ul li a").finish().css("top", "3px");
        $("#menu_area ul #logo").finish().css("background-size", "0px");
        $("#menu_area ul #logo a").finish().css("font-size", "45px");
        $("#menu_area ul #logo a").finish().css("top", "13px");
       
        
    }else if($(this).scrollTop() < 1){
		$('.header_menu div').removeClass("stickyHeader");
		$("#menu_area ul").finish().css("padding", "60px 0");
		$("#menu_area ul li a").finish().animate({"font-size":"30px"}, "slow");
		$("#menu_area ul li a").finish().animate({"top":"0px"}, "slow");
		$("#menu_area ul #logo a").finish().animate({"top":"18px"},"slow");
		$("#menu_area ul #logo a").finish().animate({"font-size":"90px"}, "slow");
		$("#menu_area ul #logo").finish().animate({"background-size":"90px"},"slow");
	}
	
	if ($(this).scrollTop() > 250) {
		$(".content div").find("nav").addClass("stickyMenu"); //좌측메뉴
	}
	if ($(this).scrollTop() < 250) {
		$(".content div").find("nav").removeClass("stickyMenu"); //좌측 메뉴
	}
});