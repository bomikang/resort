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
			
			//mem_no,mem_id,mem_pwd,mem_name,mem_mail,mem_tel,mem_regdate,mem_outdate,mem_ismng
			Date nowTime = new Date();
			Member mem = new Member(0,
					req.getParameter("id"),
					req.getParameter("password"),
					req.getParameter("name"),
					req.getParameter("email"),
					req.getParameter("tel"),
					nowTime,
					null,
					false
					);								
			Connection conn = null;
			try{
				
				
				
				conn = ConnectionProvider.getConnection();
				MemberDao dao = MemberDao.getInstance();
				
				
				
				final String regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
				
				Pattern patt = Pattern.compile(regex);
				Matcher match = patt.matcher(id);
				if(match.find()==false){
					req.setAttribute("noId", true);
					return "index.jsp?page=/WEB-INF/member/join&menu=/WEB-INF/member/mem_menu";
				}else if(match.find()==true){
					
				}
				dao.insert(conn,mem);
				
			}finally{
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/member/joinSuccess&menu=/WEB-INF/member/mem_menu";
			
		}
		return null;
	}

	
}
