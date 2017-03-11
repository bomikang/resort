<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Connection con = null;
		try{
			String jdbcDriver = "jdbc:apache:commons:dbcp:resort";
			con = DriverManager.getConnection(jdbcDriver);
			if(con != null){
				out.print("커넥션 풀에 연결되었음");
			}
		}finally{
			if(con != null){
				con.close();
			}
		}
	%>
</body>
</html>