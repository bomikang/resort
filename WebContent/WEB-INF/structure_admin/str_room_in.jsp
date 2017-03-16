<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	$(function(){
		$("#btnAddStr").click(function() {
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
			
			//confirm확인
			var name = $("#name").val();
			var check = confirm(name +" 을 등록하시겠습니까?");
			
			if (check == false) {
				return false;
			}
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
			</p>
			
			<p>
				<label for="">수용 인원</label><br />
				<input type="text" name="people" placeholder="ex) 4"/>
			</p>
			
			<p>
				<label for="">가격</label><br />
				<input type="text" name="price" placeholder="ex) 90000"/>
			</p>
			
			<p>
				<label for="">옵션</label><br />
				<input type="text" name="option" />
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
				<input type="submit" value="등록하기" id="btnAddStr"/>
			</p>
		</fieldset>
	</form>
</body>
</html>