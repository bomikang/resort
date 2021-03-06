package member.handler;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
			return "index.jsp?page=/WEB-INF/member/join&menu=/WEB-INF/member/mem_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			String id = req.getParameter("id");
			String tel1 = req.getParameter("bkTel1");
			String tel2 = req.getParameter("bkTel2");
			String tel3 = req.getParameter("bkTel3");
			//mem_no,mem_id,mem_pwd,mem_name,mem_mail,mem_tel,mem_regdate,mem_outdate,mem_ismng
			Date nowTime = new Date();
			Member mem = new Member(0,
					req.getParameter("id"),
					req.getParameter("password"),
					req.getParameter("name"),
					req.getParameter("email"),
					tel1+"-"+tel2+"-"+tel3,
					nowTime,
					null,
					false
					);								
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				MemberDao dao = MemberDao.getInstance();
				dao.insert(conn,mem);
				
			}finally{
				JdbcUtil.close(conn);
			}
			req.setAttribute("memId", id);
			return "index.jsp?page=/WEB-INF/member/joinSuccess&menu=/WEB-INF/member/mem_menu";
			
		}
		return null;
	}

	
}
