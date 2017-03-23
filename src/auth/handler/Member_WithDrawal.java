package auth.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;


import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class Member_WithDrawal implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("post")){
			Connection conn = null;
			
		
			try{
				conn=ConnectionProvider.getConnection();   
				LoginMemberInfo userInfo = (LoginMemberInfo)req.getSession().getAttribute("myinfo");	
				MemberDao dao = MemberDao.getInstance();
				Member mInfo=dao.selectByNo(conn, userInfo.getMy_no()); // 해당 아이디 개인정보
				Date nowTime = new Date();
				Member memberDrawal = new Member(mInfo.getNo(),			
												mInfo.getId(),
												mInfo.getPassword(),
												mInfo.getName(),
												mInfo.getMail(),
												mInfo.getTel(),
												mInfo.getRegDate(),
												nowTime,mInfo.getIsMng());
				Member member_NoDrawal = new Member(mInfo.getNo(),			
												 mInfo.getId(),
												 mInfo.getPassword(),
												 mInfo.getName(),
												 mInfo.getMail(),
												 mInfo.getTel(),
												 mInfo.getRegDate(),
												 null,mInfo.getIsMng());
				String result = "";
				if(memberDrawal.getOutDate()!=null){
					result = "ok";
					dao.member_WithDrawal(conn, memberDrawal);
					
					
					//json 사용 시 필요구문
					ObjectMapper om= new ObjectMapper();
					String json = om.writeValueAsString(result);	//  JSP화면 JSON 데이터 result값 반환 
					HttpSession session = req.getSession(false); // 세션값이 없으면 null 값을 반환	
					session.invalidate();
					res.setContentType("application/json;charset=utf-8");
					PrintWriter pw = res.getWriter();
					pw.print(json);
					pw.flush();
				}
				if(member_NoDrawal.getOutDate()==null){
					result = "no";
				}	
			
				}finally{
					JdbcUtil.close(conn);
				}
			}
			return null;
			
		}

	

}
