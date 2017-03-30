<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style>
	.error, .error_reg{display : none; color:red; font-size: 12px;}
	
	input[type='text']{width:400px;}
	p{width:490px !important;}
</style>
<script>
	//정규표현식 검사 함수
	function checkRegExr(objInput){
		var reg = "";
		
		//호수 : 영문, 한글, 숫자, 특수문자(-, _)만 20글자까지 입력가능
		//수용인원 : 1~99까지
		//가격 : 10000~9999999까지
		switch (objInput.attr("name")) {
		case "name": reg = /^[a-zA-z0-9가-힣\s-_]{1,20}$/; break;
		case "people": reg = /^[1-9]{1}[0-9]{0,1}$/; break;
		case "price": reg = /^[1-9]{1}[0-9]{4,6}$/; break;
		}
		
		if ( !reg.test( objInput.val() ) ) {
			objInput.next().next(".error_reg").css("display","block");
			return false;
		}
		return true;
	}

	$(function(){
		//저장된 시설구분 체크 해놓기(이미 선택이 되어있기 때문에 체크여부를 확인 할 필요가 없음)
		switch ("${structure.id}") {
		case "1": $("input[name='strId']").eq(0).prop("checked", true); break;
		case "2": $("input[name='strId']").eq(1).prop("checked", true); break;
		case "3": $("input[name='strId']").eq(2).prop("checked", true); break;
		case "4": $("input[name='strId']").eq(3).prop("checked", true); break;
		}
		
		$("#btnUpdateStr").click(function() {
			var strIdText = "";
			
			$("input[type='radio']").each(function(i, obj) {
				if ($(obj).prop("checked") == true) {
					switch ($(obj).val()) {
					case "1": strIdText = "숲속의집"; break;
					case "2": strIdText = "산림휴양관"; break;
					case "3": strIdText = "캐라반"; break;
					case "4": strIdText = "돔하우스"; break;
					}
				}
			});
			
			/* 1. 공란 존재 여부 */
			/* if( !checkInputEmpty($("input[type='text']"))){
				return false;
			}
			 */
			/* 2. 정규표현식 검사 */
			if ( !checkRegExr($("input[name='name']"))
				|| !checkRegExr($("input[name='people']"))
				|| !checkRegExr($("input[name='price']"))) {
				return false;
			}
			
			/* 3. 대표이미지 또는 내부이미지 둘 중 하나라도 선택된 것이 없으면 return false */
			var repImage = document.getElementById("repImage"); //대표이미지
			var innerImage = document.getElementById("innerImage"); //내부이미지
			
			if (repImage.files.length == 0 || innerImage.files.length == 0) {
				$(".error").last().css("display", "block");
				return false;
			}
			
			//form enctype='multipart/form-data'의 경우 input type이 file인 parameter를 들고오지 못하기 때문에
			//hidden으로 parameter를 심어 준다.
			
			var setDbImage = repImage.files.item(0).name; //대표이미지 이름 + 내부이미지들
			
			for (var i = 0; i < innerImage.files.length; ++i) {
			  var name = innerImage.files.item(i).name;
			  setDbImage += "/"+name; //구분자는 슬러시
			}
			
			$("#setDbImage").val(setDbImage); //hidden에 심음
			
			/* 4. 모든 것이 완벽하게 들어갔을 시 confirm창 띄워 확인 */
			var name = $("#name").val();
			var check = confirm(strIdText +"의 "+name+" 을(를) 수정하시겠습니까?");
			 
			if ( check ) {
				$("form[name='f1']").attr("action", "structureUpdate.do");
			}else{
				return false;
			}
		});
		
		$("#btnDeleteStr").click(function() {
			var check = confirm("정말 삭제하시겠습니까?");
			 
			if ( check ) {
				$("form[name='f1']").attr("enctype", "");
				$("form[name='f1']").attr("action", "structureDelete.do");
			}else{
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="intro_padding">
	<h2>시설등록</h2>
	<hr />
	<form action="structureUpdate.do" method="post" enctype="multipart/form-data" name="f1">
		<fieldset>
			<p>
				<label for="">시설 이름 : </label> 
				
				<input type="radio" name="strId" value="1" />숲속의집
				<input type="radio" name="strId" value="2" />산림휴양관
				<input type="radio" name="strId" value="3" />캐라반
				<input type="radio" name="strId" value="4" />돔하우스
				<span class="error">시설 이름을 선택해주세요.</span>
			</p>
			
			<p>
				<label for="">호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;수 : </label>
				<input type="text" name="name" id="name" value="${structure.name}"required="required"/>
				<span class="error">호수를 입력해주세요.</span>
				<span class="error_reg">호수는 영문, 숫자, 한글, 특수문자(-, _)만 입력 가능합니다.(최대 20글자)</span>
			</p>
			
			<p>
				<label for="">수용 인원 : </label>
				<input type="text" name="people" placeholder="ex) 4" value="${structure.people}"required="required"/>
				<span class="error">수용 인원을 입력해주세요.</span>
				<span class="error_reg">수용 인원은 1~99까지 숫자만 입력 가능합니다.</span>
			</p>
			
			<p>
				<label for="">가&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격 : </label>
				<input type="text" name="price" placeholder="ex) 90000" value="${structure.price}"required="required"/>
				<span class="error">가격을 입력해주세요.</span>
				<span class="error_reg">가격은 10000~9999999까지 숫자만 입력 가능합니다.</span>
			</p>
			
			<p>
				<label for="">옵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;션 : </label>
				<input type="text" name="option" value="${structure.option}" required="required"/>
				<span class="error">옵션을 입력해주세요.</span>
			</p>
			
			<p>
				<label for="">대표 사진 : </label>
				<input type="file" name="repImage" id="repImage"/>
			</p>
			
			<p>
				<label for="">내부 사진 : </label>
				<input type="file" name="innerImage" id="innerImage" multiple/><br>
				<span class="error">대표사진과 내부사진을 등록해주세요!</span>
			</p>
			<p class='act_btn_area'>
				<input type="hidden" name="setDbImage" id="setDbImage" /><!-- db전달용 -->
				<input type="hidden" name="strNo" value="${structure.no}"/>
				<input type="submit" value="수정" id="btnUpdateStr"/>
				<input type="submit" value="삭제" id="btnDeleteStr" />
				<input type="button" value="돌아가기" onclick="location.replace('structureList.do')"/><!-- 취소하면 리스트로 -->
			</p>
		</fieldset>
	</form>
</div>	
</body>
</html>