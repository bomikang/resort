package auth.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class LoginHandler implements CommandHandler {
	private String returnTo = "";
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {		
		if (req.getMethod().equalsIgnoreCase("get")) {
			returnTo = req.getParameter("category");
			System.out.println("returnTo : "+returnTo);
			if(returnTo==null){
				returnTo = "index.jsp";
			}else if(returnTo.isEmpty()){
				returnTo = "index.jsp";
			}
			return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			System.out.println("returnTo : "+returnTo);
			String id = req.getParameter("id");
			String password = req.getParameter("password");
			
			Connection conn = null;
			MemberDao dao = MemberDao.getInstance();
			try{
				conn = ConnectionProvider.getConnection();
				Member member = dao.selectById(conn, id);
				Member no_member = dao.selectOutdateIs(conn, id);
				
				
				if(no_member == null){	 // 아이디가 없을경우
					req.setAttribute("notJoin", true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
				}else if(id==""){     // 아이디란이 공란일 경우
					req.setAttribute("outId", true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
				}
				
				if(password == ""){ // 패스워드 란이 공란일 경우
					req.setAttribute("outPass", true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu"; 
				}else if(!member.matchPassword(password)){ // 비밀번호가다를경우
					req.setAttribute("notPass",true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
				}	
				// 세션에 DATA 남기기위한 작업
				if(member.getIsMng().equals(true)){ // 관리자일 경우
					/**
					 * 유진작업 - 관리자일 경우 수행할 메소드(자동취소, 자동 완료) 2개 추가 - 문제발생 시 알려주세요! 
					 * */
					conn.setAutoCommit(false);
					BookDao bDao = BookDao.getInstance();
					bDao.autoBookCancel(conn);
					bDao.autoBookEnd(conn);
					conn.commit();
				}
				/*if(member.getIsMng().equals(false)){ // 일반회원일 경우
					req.setAttribute("user",true);
				}*/
				LoginMemberInfo myinfo = new LoginMemberInfo(
						member.getNo(),
						member.getId(),
						member.getName(),
						member.getMail(),
						member.getIsMng(),
						member.getTel());
				req.getSession().setAttribute("user_info",myinfo);
				String url = "";
				if(returnTo.equals("index.jsp")){
					url = returnTo;
				}else{
					url=returnTo+".do";
				}				
				res.sendRedirect(url);
				return null;
			}finally{
				JdbcUtil.close(conn);	
			}
		}
		return null;
	}

}
