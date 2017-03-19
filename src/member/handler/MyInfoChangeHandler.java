package member.handler;

import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javafx.scene.control.Alert;
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
						
						if(member.getId()!=null){
							dao.updateInFo(conn, member);
							
							Member mem = dao.selectById(conn, member.getId());
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
							
							req.setAttribute("ok",true);
						}
						
						
						conn.commit();
						return "index.jsp";
				}finally{
					JdbcUtil.close(conn);
				}
			}
			return null;
			
		}

	

}
