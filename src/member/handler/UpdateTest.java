package member.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class UpdateTest implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			return "index.jsp?page=/WEB-INF/member/myinfo&menu=/WEB-INF/member/mem_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			Connection conn = null;
			
		
			try{
				conn=ConnectionProvider.getConnection();   
				String name = req.getParameter("name");
				String id = req.getParameter("id");
				String pwd = req.getParameter("password");
				String mail = req.getParameter("mail");
				String tel = req.getParameter("tel");
			
				
				
				MemberDao dao = MemberDao.getInstance();
				LoginMemberInfo userInfo = (LoginMemberInfo)req.getSession().getAttribute("myinfo");
				Member memberinfo=dao.selectByNo(conn, userInfo.getMy_no());
				
					Member member_name= new Member(
							userInfo.getMy_no(),
							userInfo.getMy_id(),
							memberinfo.getPassword(),
							name,
							userInfo.getMy_mail(),
							userInfo.getMy_tel(),
							null,null,null);
					
					Member member_id= new Member(
							userInfo.getMy_no(),
							id,
							memberinfo.getPassword(),
							userInfo.getMy_name(),
							userInfo.getMy_mail(),
							userInfo.getMy_tel(),
							null,null,null);
					Member member_Pwd= new Member(
							userInfo.getMy_no(),
							userInfo.getMy_id(),
							pwd,
							userInfo.getMy_name(),
							userInfo.getMy_mail(),
							userInfo.getMy_tel(),
							null,null,null);
					
					Member member_mail= new Member(
							userInfo.getMy_no(),
							userInfo.getMy_id(),
							memberinfo.getPassword(),
							userInfo.getMy_name(),
							mail,
							userInfo.getMy_tel(),
							null,null,null);
					
					Member member_tel= new Member(
							userInfo.getMy_no(),
							userInfo.getMy_id(),
							memberinfo.getPassword(),
							userInfo.getMy_name(),
							userInfo.getMy_mail(),
							tel,
							null,null,null);
					
					
				
			
			
					String result="";
					if(!member_name.getName().equals("")){
						dao.updateInFo(conn, member_name);
						result = "ok";		
						Member mem = dao.selectById(conn, member_name.getId());
						LoginMemberInfo myinfo = new LoginMemberInfo(
								mem.getNo(),
								mem.getId(),
								mem.getName(),
								mem.getMail(),
								mem.getIsMng(),
								mem.getTel());
						if(myinfo.getIsMng().equals(true)){ // 관리자일 경우
							req.getSession().setAttribute("admin",myinfo);
						}
						
						if(myinfo.getIsMng().equals(false)){ // 일반회원일 경우
							req.getSession().setAttribute("myinfo",myinfo);
						}
						//json 사용 시 필요구문
						ObjectMapper om= new ObjectMapper();
						String json = om.writeValueAsString(result);	//  JSP화면 JSON 데이터 result값 반환 
						
						res.setContentType("application/json;charset=utf-8");
						PrintWriter pw = res.getWriter();
						pw.print(json);
						pw.flush();
					} 
					if(!member_id.getId().equals("")){
					 	result="ok";
						dao.updateInFo(conn, member_id);
						Member mem = dao.selectById(conn, member_name.getId());
						LoginMemberInfo myinfo = new LoginMemberInfo(
								mem.getNo(),
								mem.getId(),
								mem.getName(),
								mem.getMail(),
								mem.getIsMng(),
								mem.getTel());
						if(myinfo.getIsMng().equals(true)){ // 관리자일 경우
							req.getSession().setAttribute("admin",myinfo);
						}
						
						if(myinfo.getIsMng().equals(false)){ // 일반회원일 경우
							req.getSession().setAttribute("myinfo",myinfo);
						}
						//json 사용 시 필요구문
						ObjectMapper om= new ObjectMapper();
						String json = om.writeValueAsString(result);	//  JSP화면 JSON 데이터 result값 반환 
						
						res.setContentType("application/json;charset=utf-8");
						PrintWriter pw = res.getWriter();
						pw.print(json);
						pw.flush();
					}
					
					/*
					if(!member_Pwd.getPassword().equals("")){
						dao.updateInFo(conn, member_Pwd);
						result = "ok";		
						Member mem = dao.selectById(conn, member_Pwd.getId());
						LoginMemberInfo myinfo = new LoginMemberInfo(
								mem.getNo(),
								mem.getId(),
								mem.getName(),
								mem.getMail(),
								mem.getIsMng(),
								mem.getTel());
						if(myinfo.getIsMng().equals(true)){ // 관리자일 경우
							req.getSession().setAttribute("admin",myinfo);
						}
						
						if(myinfo.getIsMng().equals(false)){ // 일반회원일 경우
							req.getSession().setAttribute("myinfo",myinfo);
						}
						
					}
					
					if(!member_mail.getMail().equals("")){
						dao.updateInFo(conn, member_mail);
						result = "ok";		
						Member mem = dao.selectById(conn, member_mail.getId());
						LoginMemberInfo myinfo = new LoginMemberInfo(
								mem.getNo(),
								mem.getId(),
								mem.getName(),
								mem.getMail(),
								mem.getIsMng(),
								mem.getTel());
						if(myinfo.getIsMng().equals(true)){ // 관리자일 경우
							req.getSession().setAttribute("admin",myinfo);
						}
						
						if(myinfo.getIsMng().equals(false)){ // 일반회원일 경우
							req.getSession().setAttribute("myinfo",myinfo);
						}
						
					}
					if(!member_tel.getTel().equals("")){
						dao.updateInFo(conn, member_tel);
						result = "ok";		
						Member mem = dao.selectById(conn, member_tel.getId());
						LoginMemberInfo myinfo = new LoginMemberInfo(
								mem.getNo(),
								mem.getId(),
								mem.getName(),
								mem.getMail(),
								mem.getIsMng(),
								mem.getTel());
						if(myinfo.getIsMng().equals(true)){ // 관리자일 경우
							req.getSession().setAttribute("admin",myinfo);
						}
						
						if(myinfo.getIsMng().equals(false)){ // 일반회원일 경우
							req.getSession().setAttribute("myinfo",myinfo);
						}
						
					}
					*/
								
					
					
				
			}finally{
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

	
	
}
