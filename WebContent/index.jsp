<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String includePage = request.getParameter("page"); /* ?page=XXXX */
    	String includeMenu = request.getParameter("menu");
    	
    	if(includeMenu == null) includeMenu = "/WEB-INF/introduce/intro_menu";
    	if(includePage == null) includePage = "/WEB-INF/introduce/intro_main";
    %>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/common.css" />
<title>Insert title here</title>
</head>
<body>
	<div class="wrapper">
		<div class="login_area">
			<div>
				<p><b>곽문한</b>님 옥성원에 오신걸 환영합니다</p>
				<a href="login.do">로그인</a>
				<a href="join.do">회원가입</a>
			</div>
		</div>
		<header class="header_menu">
			<div>
				<div id="logo_area">
					<a href="index.jsp"><img src="image/logo.png" alt="" /></a>
				</div>
				<div>
					<a href="introduce.do">휴양림소개</a>
					<a href="book.do">예약안내</a>
					<a href="structure.do">시설현황</a>
					<a href="board.do">자유게시판</a>
				</div>
			</div>
		</header>
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