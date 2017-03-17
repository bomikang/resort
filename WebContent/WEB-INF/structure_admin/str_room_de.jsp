<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
/*캐시에 Data를 남기지 않는구문(로그아웃 이후 뒤로가기 Data기록 안남기기 위해 사용)  */
response.setHeader("cache-control","no-store");
response.setHeader("expires","0");
response.setHeader("pragma","no-cache");

%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	$(function(){
		//저장된 시설구분 체크 해놓기
		switch ("${structure.id}") {
		case "1": $("input[name='strId']").eq(0).prop("checked", true); break;
		case "2": $("input[name='strId']").eq(1).prop("checked", true); break;
		case "3": $("input[name='strId']").eq(2).prop("checked", true); break;
		case "4": $("input[name='strId']").eq(3).prop("checked", true); break;
		}
		
		$("#btnUpdateStr").click(function() {
			var strIdText = "";
			
			switch ($("input[name='strId']").val()) {
			case "1": strIdText = "숲속의집"; break;
			case "2": strIdText = "산림휴양관"; break;
			case "3": strIdText = "캐라반"; break;
			case "4": strIdText = "돔하우스"; break;
			}
			
			//confirm확인
			var name = $("#name").val();
			
			var check = confirm(strIdText +"의 "+name+" 을(를) 수정하시겠습니까?");
			 
			if (check == false) {
				return false;
			}
			
			var repImage = document.getElementById("repImage"); //대표이미지
			var innerImage = document.getElementById("innerImage"); //내부이미지
			
			/* form enctype='multipart/form-data'의 경우 input type이 file인 parameter를 들고오지 못하기 때문에
			hidden으로 parameter를 심어 준다. */
			
			var setDbImage = repImage.files.item(0).name; //대표이미지 이름 + 내부이미지들
			
			for (var i = 0; i < innerImage.files.length; ++i) {
			  var name = innerImage.files.item(i).name;
			  setDbImage += "/"+name; //구분자는 슬러시
			}
			
			$("#setDbImage").val(setDbImage); //hidden에 심음
		});
	});
</script>
</head>
<body>
	<form action="structureUpdate.do" method="post" enctype="multipart/form-data" name="f1">
		<fieldset>
			<legend>숙박 시설 수정</legend>
			
			<p>
				<label for="">시설 이름</label><br />
				
				<input type="radio" name="strId" value="1" />숲속의집
				<input type="radio" name="strId" value="2" />산림휴양관
				<input type="radio" name="strId" value="3" />캐라반
				<input type="radio" name="strId" value="4" />돔하우스
			</p>
			
			<p>
				<label for="">호수(숲속의 집은 방 이름을 입력해주세요.)</label><br />
				<input type="text" name="name" id="name" value="${structure.name}"/>
			</p>
			
			<p>
				<label for="">수용 인원</label><br />
				<input type="text" name="people" placeholder="ex) 4" value="${structure.people}"/>
			</p>
			
			<p>
				<label for="">가격</label><br />
				<input type="text" name="price" placeholder="ex) 90000" value="${structure.price}"/>
			</p>
			
			<p>
				<label for="">옵션</label><br />
				<input type="text" name="option" value="${structure.option}" />
			</p>
			
			<p>
				<label for="">대표사진</label><br />
				<input type="file" name="repImage" id="repImage"/>
			</p>
			
			<p>
				<label for="">내부사진</label><br />
				<input type="file" name="innerImage" id="innerImage" multiple/><br>
			</p>
			<p>
				<input type="hidden" name="setDbImage" id="setDbImage" /><!-- db전달용 -->
				<input type="hidden" name="strNo" value="${structure.no}"/>
				<input type="submit" value="수정" id="btnUpdateStr"/>
				<input type="button" value="돌아가기" onclick="location.replace('structureList.do')"/><!-- 취소하면 리스트로 -->
			</p>
		</fieldset>
	</form>
</body>
</html>