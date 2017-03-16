package auth.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class LoginHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			String id = req.getParameter("id");
			String password = req.getParameter("password");
			
			Connection conn = null;
			MemberDao dao = MemberDao.getInstance();
			try{
				conn = ConnectionProvider.getConnection();
				Member member = dao.selectById(conn, id);
				Member no_member = dao.selectOutdateIs(conn, id);
				
				
				if(no_member == null){	 // 그 아이디 없으면 
					req.setAttribute("notJoin", true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
				}
				if(!member.matchPassword(password)){ // 패스워드가 틀렸을경우
					req.setAttribute("notPass",true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
				}if(member.getPassword() == null){
					req.setAttribute("outPass", true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu"; 
				}
			
				
				LoginMemberInfo myinfo = new LoginMemberInfo(
						member.getNo(),
						member.getId(),
						member.getName(),
						member.getMail(),
						member.getIsMng());
				req.getSession().setAttribute("myinfo",myinfo);
				return "index.jsp";
			}finally{
				JdbcUtil.close(conn);	
			}
		}
		return null;
	}

}
