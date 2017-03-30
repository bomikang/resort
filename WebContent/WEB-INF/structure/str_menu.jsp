<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href="structure.do?houseId=1" class='nav_hover_style'>숲속의 집</a>
<a href="structure.do?houseId=2" class='nav_hover_style'>산림휴양관</a>
<a href="structure.do?houseId=3" class='nav_hover_style'>캐라반</a>
<a href="structure.do?houseId=4" class='nav_hover_style'>돔하우스</a>
<a href="structureArround.do" class='nav_hover_style'>편의시설</a>
<a href="structureMap.do" class='nav_hover_style'>전체시설조감도</a>
<c:if test="${!empty user_info }">
	<c:if test="${user_info.isMng==true }">
		<hr />
		<a href="structureUpload.do" class='nav_hover_style'>시설등록</a>
		<a href="structureList.do" class='nav_hover_style'>시설 List</a>
	</c:if>	
</c:if>	
