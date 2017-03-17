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

public class MyInfoChangeHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
			if(req.getMethod().equalsIgnoreCase("get")){
				return "index.jsp?page=/WEB-INF/member/myinfo&menu=/WEB-INF/member/mem_menu";
			}else if(req.getMethod().equalsIgnoreCase("post")){
				
				LoginMemberInfo userInfo = (LoginMemberInfo)req.getSession().getAttribute("myinfo");
				Connection conn = null;
				try{
					String name = req.getParameter("name");
					String id = req.getParameter("id");
					String pwd = req.getParameter("password");
					String mail = req.getParameter("mail");
					String tel = req.getParameter("tel");
					conn = ConnectionProvider.getConnection();
					conn.setAutoCommit(false);
					System.out.println(id);
					MemberDao dao = MemberDao.getInstance();
					Member memberinfo=dao.selectByNo(conn, userInfo.getMy_no());
					
						Member member= new Member(
								userInfo.getMy_no(),
								userInfo.getMy_id(),
								memberinfo.getPassword(),
								name,
								userInfo.getMy_mail(),
								userInfo.getMy_mail(),
								null,null,null);
						
						dao.updateInFo(conn, member);
						conn.commit();
				return "index.jsp?page=/WEB-INF/member/myinfo&menu=/WEB-INF/member/mem_menu";
				}finally{
					JdbcUtil.close(conn);
				}
			}
			return null;
			
		}

	

}
