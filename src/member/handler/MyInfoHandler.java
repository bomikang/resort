package member.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class MyInfoHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			MemberDao dao = MemberDao.getInstance();
			LoginMemberInfo userinfo = (LoginMemberInfo)req.getSession(false).getAttribute("myinfo");
			Member detailinfo = dao.selectByNo(conn, userinfo.getMy_no()); 
			req.setAttribute("info", detailinfo);
		}finally{
			JdbcUtil.close(conn);
		}
		return "index.jsp?page=/WEB-INF/member/myinfo&menu=/WEB-INF/member/mem_menu";
	}

}
