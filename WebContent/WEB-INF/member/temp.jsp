<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		<c:choose>
			<c:when test="${!empty resID}">
				var id = "${resID}";	
				id = id.substr(0, (id.length/2));
				for(var i=0;i<(id.length/2);i++){
					id += "*";
				}
					
				alert("귀하의 아이디는 "+id+"입니다.");
				
				location.href="login.do";
			</c:when>
			<c:when test="${!empty resPW}">
				var pw = "${resPW}";
				pw = pw.substr(0, (pw.length/2));
				for(var i=0;i<(pw.length/2);i++){
					pw += "*";
				}
				alert("귀하의 비밀번호는 "+pw+"입니다.");
				
				location.href="login.do";
				
			</c:when>
			<c:otherwise>
				alert("일치하는 정보가 존재하지 않습니다.");
				
				location.href="login.do";
			</c:otherwise>
		</c:choose>	
	});
</script>
</head>
<body>

</body>
</html>