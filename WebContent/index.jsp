<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   	String includePage = request.getParameter("page"); /* ?page=XXXX */
   	String includeMenu = request.getParameter("menu");
   	
   	if(includeMenu == null) includeMenu = "/WEB-INF/introduce/intro_menu";
   	if(includePage == null) includePage = "/WEB-INF/introduce/intro_main";
%>
<%--  <%
/*캐시에 Data를 남기지 않는구문(로그아웃 이후 뒤로가기 Data기록 안남기기 위해 사용)  */
response.setHeader("cache-control","no-store");
response.setHeader("expires","0");
response.setHeader("pragma","no-cache");

%> --%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/common.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jsForStyle.js"></script><!-- js for style -->

<style>
	.wrapper{ background:url("image/bg_img7_1.jpg") no-repeat fixed center center; background-size:100% !important;}
</style>
<title>Insert title here</title>
</head>		
<body>
	<div class="wrapper">
		<div class="login_area">
			<div>
			<c:if test="${empty user_info }">
				<p>옥성자연휴양림에 오신걸 환영합니다</p>
				<a href="login.do">로그인</a>
				<a href="join.do">회원가입</a>
			</c:if>	
			<c:if test="${!empty user_info }">
				<c:if test="${user_info.isMng==false }">
					<p><b>${user_info.my_name }</b> 옥성자연휴양림에 오신걸 환영합니다</p>
					<a href="logout.do" id="logout">로그아웃</a> 
					<!-- <button id="logout">로그아웃</button> -->
					<a href="myinfo.do">회원정보</a>
				</c:if>
				<c:if test="${user_info.isMng==true }">
					<p>관리자 모드 입니다 </p>
					<a href="logout.do" id="logout">로그아웃</a>
					<a href="myinfo.do">관리자페이지</a>
				</c:if>
			</c:if>
			<%-- <c:if test="${!empty admin}">
				<p><b>${admin_info.my_name }
				<a href="logout.do">로그아웃</a>
				<a href="myinfo.do">관리자페이지</a>
				
			</c:if> --%>
			
			</div>
		</div>
		<header class="header_menu" style="z-index:9999;">
			<div>
				<div id="logo_area">
					<a href="index.jsp"><img src="image/logo2.png" alt="" /></a>
				</div>
				<div id="menu_area">
					<ul>
						<li><a href="introduce.do">휴양림소개</a></li>
						<li><a href="book.do">예약안내</a></li>
						<li><a href="structure.do">시설현황</a></li>
						<li><a href="board.do">자유게시판</a></li>
					</ul>
					<ol><!-- sub menu -->
					</ol>
				</div>
			</div>
		</header>
		<div class="content_rep_img">
			<div>
				<img src="image/content_rep_img.png" alt="" />
			</div>
		</div>
		<section class="content">
			<div>
				<nav>
					<jsp:include page='<%= includeMenu+".jsp" %>'></jsp:include>
				</nav>
				<article>
					<jsp:include page='<%= includePage+".jsp" %>'></jsp:include>
				</article>
			</div>
		</section>
		<footer>
			<div>
				Copyright ⓒ  The Reason Why Bomi Is MoonHan All Right Reserved
			</div>
		</footer>
	</div>
</body>
</html>