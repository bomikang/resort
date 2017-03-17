package member.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class JoinId_Checking implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("post")){
			String checkId = req.getParameter("id");
			Connection conn = null;
			try{
				conn=ConnectionProvider.getConnection();   
				MemberDao dao = MemberDao.getInstance();  
				List<Member> mem = dao.listNo(conn);
				String ok ="ok";
				String no ="no";
				String result="";
				for(int i=0;i<mem.size();i++){
					System.out.println(mem.get(i).getId());
					if(mem.get(i).getId().equals(checkId)){
						result = "no";				// input 창의 아이디와 DB 아이디 비교 후 동일하면 "no" 값 반환
						break;
					}else{
						result = "ok";				// input 창의 아이디와 DB 아이디 비교 후 다르면 "OK" 값 반환
						
					}	
				}
				
				//json 사용 시 필요구문
				ObjectMapper om= new ObjectMapper();
				String json = om.writeValueAsString(result);	//  JSP화면 JSON 데이터 result값 반환 
				
				res.setContentType("application/json;charset=utf-8");
				PrintWriter pw = res.getWriter();
				pw.print(json);
				pw.flush();
			}finally{
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

	
	
}
