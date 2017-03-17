package auth.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
				
				
				if(no_member == null){	 // �� ���̵� ������ 
					req.setAttribute("notJoin", true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
				}else if(id==""){     // ���̵� ������ ���
					req.setAttribute("outId", true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
				}
				
				if(password == ""){ // ��й�ȣ ������ ��� 
					req.setAttribute("outPass", true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu"; 
				}else if(!member.matchPassword(password)){ // �н����尡 Ʋ�������
					req.setAttribute("notPass",true);
					return "index.jsp?page=/WEB-INF/member/login&menu=/WEB-INF/member/mem_menu";
				}
				
				// ���� ������ Data �÷�����
				LoginMemberInfo myinfo = new LoginMemberInfo(
						member.getNo(),
						member.getId(),
						member.getName(),
						member.getMail(),
						member.getIsMng());
				if(myinfo.getIsMng().equals(true)){ // ������ �� ���
					req.getSession().setAttribute("admin",myinfo);
				}
				
				if(myinfo.getIsMng().equals(false)){ // �Ϲ�ȸ�� �� ���
					req.getSession().setAttribute("myinfo",myinfo);
				}
				
				return "index.jsp";
			}finally{
				JdbcUtil.close(conn);	
			}
		}
		return null;
	}

}
