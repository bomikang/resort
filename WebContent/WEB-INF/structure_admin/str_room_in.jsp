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
<script>
	//시설이름이 체크되어 있으면 그 value값을 리턴
	function returnCheckedStrName($radio){
		var strIdText = "";
		
		$radio.each(function(i, obj) {
			if ($(obj).prop("checked") == true) {
				switch ($(obj).val()) {
				case "1": strIdText = "숲속의집"; break;
				case "2": strIdText = "산림휴양관"; break;
				case "3": strIdText = "캐라반"; break;
				case "4": strIdText = "돔하우스"; break;
				}
			}
		});
		
		return strIdText;
	}
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
		$("#btnAddStr").click(function() {
			/* 1. 시설이름 선택 여부(선택되었다면 그  value값 반환) */
			if (returnCheckedStrName($("input[type='radio']")) == ""){
				$(".error").eq(0).css("display","block");
				return false;
			}
			var strIdText = returnCheckedStrName($("input[type='radio']"));
			
			/* 2. 공란 존재 여부 */
			if( !checkInputEmpty($("input[type='text']"))){
				return false;
			}
			
			/* 3. 정규표현식 검사 */
			if ( !checkRegExr($("input[name='name']"))
				|| !checkRegExr($("input[name='people']"))
				|| !checkRegExr($("input[name='price']"))) {
				return false;
			}
			
			/* 4. 대표이미지 또는 내부이미지 둘 중 하나라도 선택된 것이 없으면 return false */
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
			
			/* 5. 모든 것이 완벽하게 들어갔을 시 confirm창 띄워 확인 */
			var name = $("#name").val();
			var check = confirm(strIdText +"의 "+name+" 을(를) 등록하시겠습니까?");
			 
			if ( !check ) return false;
		});
		
	});
</script>
</head>
<body>
	<form action="structureUpload.do" method="post" enctype="multipart/form-data" name="f1">
		<fieldset>
			<p>
				<label for="">시설 이름</label><br />
				<input type="radio" name="strId" value="1" /><span>숲속의집</span>
				<input type="radio" name="strId" value="2" /><span>산림휴양관</span>
				<input type="radio" name="strId" value="3" /><span>캐라반</span>
				<input type="radio" name="strId" value="4" /><span>돔하우스</span>
				<span class="error">시설 이름을 선택해주세요.</span>
			</p>
			
			<p>
				<label for="">호수(방이름)</label><br />
				<input type="text" name="name" id="name"/>
				<span class="error">호수를 입력해주세요.</span>
				<span class="error_reg">호수는 영문, 숫자, 한글, 특수문자(-, _)만 입력 가능합니다.(최대 20글자)</span>
			</p>
			
			<p>
				<label for="">수용 인원</label><br />
				<input type="text" name="people" placeholder="ex) 4"/>
				<span class="error">수용 인원을 입력해주세요.</span>
				<span class="error_reg">수용 인원은 1~99까지 숫자만 입력 가능합니다.</span>
			</p>
			
			<p>
				<label for="">가격</label><br />
				<input type="text" name="price" placeholder="ex) 90000"/>
				<span class="error">가격을 입력해주세요.</span>
				<span class="error_reg">가격은 10000~9999999까지 숫자만 입력 가능합니다.</span>
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
				<span class="error">대표사진과 내부사진을 등록해주세요!</span>
			</p>
			<p>
				<input type="hidden" name="setDbImage" id="setDbImage" /><!-- db전달용 -->
				<input type="submit" value="등록" id="btnAddStr"/>
				<input type="button" value="취소" onclick="location.replace('structureList.do')"/><!-- 취소하면 리스트로 -->
			</p>
		</fieldset>
	</form>
</body>
</html>