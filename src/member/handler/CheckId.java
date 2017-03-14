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

public class CheckId implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				MemberDao dao = MemberDao.getInstance();
				String id = req.getParameter("id"); // JSP 에서 LOCATION으로 던져준 변수 
				System.out.println("들어오는 아이디값 : "+id);
				List<Member> idList = dao.listNo(conn);
				
				for(int i=0;i<idList.size();i++){
					System.out.println(idList.get(i).getId());
					if(idList.get(i).getId().equals(id)){
					req.setAttribute("error",true);
					}	
				}
			}finally{
				JdbcUtil.close(conn);
			}
			
			return "/WEB-INF/member/join.jsp";
		}else if(req.getMethod().equalsIgnoreCase("post")){
		
		
		
		return "/WEB-INF/member/join.jsp";
		
		
	 }
		return null;
	}
	
}
