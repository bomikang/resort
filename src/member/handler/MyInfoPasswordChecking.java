package member.handler;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class MyInfoPasswordChecking implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			return "index.jsp?page=/WEB-INF/member/myinfo&menu=/WEB-INF/member/mem_menu";
		} else if (req.getMethod().equalsIgnoreCase("post")) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				String origin_pwd = req.getParameter("origin_pwd");
				MemberDao dao = MemberDao.getInstance();
				LoginMemberInfo userInfo = (LoginMemberInfo) req.getSession().getAttribute("user_info");
				Member memberinfo = dao.selectByNo(conn, userInfo.getMy_no());
				String result = "";
				System.out.println(origin_pwd);
				if(!origin_pwd.equals(memberinfo.getPassword())){
					result = "no";
					ObjectMapper om = new ObjectMapper();
					String json = om.writeValueAsString(result);
					res.setContentType("application/json;charset=utf-8");
					PrintWriter pw = res.getWriter();
					pw.print(json);
					pw.flush();
				}else{
					result = "ok";
					ObjectMapper om = new ObjectMapper();
					String json = om.writeValueAsString(result);	
					res.setContentType("application/json;charset=utf-8");
					PrintWriter pw = res.getWriter();
					pw.print(json);
					pw.flush();
				}
			} finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
