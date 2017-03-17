<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style>
	.error{ display : none; color:red; padding:0; margin:0 0 0 90px; font-size: 12px;}
</style>
<script>
	$(function(){
		$("#btnAddStr").click(function() {
			if( !checkInputEmpty($("input[type='text']"))){
				alert("ㅎ");
				return false;
			}
			
			var strIdText = "";
			
			switch ($("input[name='strId']").val()) {
			case "1": strIdText = "숲속의집"; break;
			case "2": strIdText = "산림휴양관"; break;
			case "3": strIdText = "캐라반"; break;
			case "4": strIdText = "돔하우스"; break;
			}
			
			//confirm확인
			var name = $("#name").val();
			
			var check = confirm(strIdText +"의 "+name+" 을(를) 등록하시겠습니까?");
			 
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
	<form action="structureUpload.do" method="post" enctype="multipart/form-data" name="f1">
		<fieldset>
			<legend>숙박 시설 등록</legend>
			
			<p>
				<label for="">시설 이름</label><br />
				<input type="radio" name="strId" value="1" />숲속의집
				<input type="radio" name="strId" value="2" />산림휴양관
				<input type="radio" name="strId" value="3" />캐라반
				<input type="radio" name="strId" value="4" />돔하우스
			</p>
			
			<p>
				<label for="">호수(숲속의 집은 방 이름을 입력해주세요.)</label><br />
				<input type="text" name="name" id="name"/>
				<span class="error">호수를 입력해주세요.</span>
			</p>
			
			<p>
				<label for="">수용 인원</label><br />
				<input type="text" name="people" placeholder="ex) 4"/>
				<span class="error">수용 인원을 입력해주세요.</span>
			</p>
			
			<p>
				<label for="">가격</label><br />
				<input type="text" name="price" placeholder="ex) 90000"/>
				<span class="error">가격을 입력해주세요.</span>
			</p>
			
			<p>
				<label for="">옵션</label><br />
				<input type="text" name="option" />
				<span class="error">옵션을 입력해주세요.</span>
			</p>
			
			<p>
				<label for="">대표사진</label><br />
				<input type="file" name="repImage" id="repImage"/>
			</p>
			
			<p>
				<label for="">내부사진</label><br/>
				<input type="file" name="innerImage" id="innerImage" multiple/><br>
			</p>
			<p>
				<input type="hidden" name="setDbImage" id="setDbImage" /><!-- db전달용 -->
				<input type="submit" value="등록" id="btnAddStr"/>
				<input type="button" value="취소" onclick="location.replace('structureList.do')"/><!-- 취소하면 리스트로 --><!-- 취소하면 리스트로 -->
			</p>
		</fieldset>
	</form>
</body>
</html>