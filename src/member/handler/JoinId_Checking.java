package member.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			String result="";
			String checkId = req.getParameter("id");
			Connection conn = null;
			try{
				conn=ConnectionProvider.getConnection();   
				MemberDao dao = MemberDao.getInstance();  
				List<Member> mem = dao.listNo(conn);
				if(mem.isEmpty()){
					result ="ok"; 			
				}
				final String reg_uid = "^[a-z0-9_]{5,12}$";  // 정규표현식
				Pattern patt = Pattern.compile(reg_uid);	// 정규표현식 패턴 적용
				Matcher match = patt.matcher(checkId); 		// 가져온 ID 패턴과 매치
				for(int i=0;i<mem.size();i++){
					
					if(mem.get(i).getId().equals(checkId)){
						result = "no";				// input 창의 아이디와 DB 아이디 비교 후 동일하면 "no" 값 반환
						break;
					}else if(!mem.get(i).getId().equals(checkId)){
						result = "ok";				// input 창의 아이디와 DB 아이디 비교 후 다르면 "OK" 값 반환
					}	
				}
				if(match.find()==false){
					
					result = "noID";
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
