package member.handler;

import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class JoinHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
		
			return "/WEB-INF/member/join.jsp";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			String id = req.getParameter("id");
			
			Member mem = new Member(0, req.getParameter("id"),
					req.getParameter("password"),
					req.getParameter("name"),
					req.getParameter("email"),
					req.getParameter("tel"),false
					);								
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				MemberDao dao = MemberDao.getInstance();
				dao.insert(conn,mem);
				
			}finally{
				JdbcUtil.close(conn);
			}
			return "/WEB-INF/member/joinSuccess.jsp";
		}
		return null;
	}

	
}
