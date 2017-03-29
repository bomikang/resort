package auth.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class LoginSearchInform implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			String key = req.getParameter("key");
			if(key==null){
				key = "id";
			}else if(key.isEmpty()){
				key = "id";
			}
			req.setAttribute("key", key);
			return "index.jsp?page=/WEB-INF/member/mem_search&menu=/WEB-INF/member/mem_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			String id = req.getParameter("id");
			System.out.println("id : "+id);
			String name = req.getParameter("name");
			System.out.println("name : "+name);
			String tel1 = req.getParameter("bkTel1");
			String tel2 = req.getParameter("bkTel2");
			String tel3 = req.getParameter("bkTel3");
			String tel = tel1+"-"+tel2+"-"+tel3;
			System.out.println("tel : "+tel);
			if(id != null && id.isEmpty()){
				id = null;
			}
			System.out.println(id);
			
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				MemberDao mDao = MemberDao.getInstance();
				String result = "";
				List<Member> mList = mDao.selectByNameTelID(conn, id, name, tel);
				if(mList.isEmpty()){
					result="noMem";//없는 회원
				}else{
					for(Member m:mList){
						if(m.getOutDate()!=null){
							result="outMem";//탈퇴한 회원
						}else{
							result="success";
						}
					}
				}
				
				if(result.equals("success")){
					if(id != null){
						String password = mList.get(0).getPassword();
						if(password.length()<8){
							password = password.substring(0, (password.length()/2));
							for(int i=0;i<password.length();i++){
								password += "*";
							}
						}else{
							password = password.substring(0, (password.length()-4)) + "****";
						}
						req.setAttribute("alertText", "귀하의 비밀번호는 "+mList.get(0).getPassword()+"입니다.");
						
					}else{
						req.setAttribute("alertText", "귀하의 아이디는 "+mList.get(0).getId()+"입니다.");
						
					}
				}else{
					req.setAttribute("alertText", "탈퇴하였거나 존재하지 않는 정보입니다.");
				}
				
				req.setAttribute("returnTo", "login.do");
				return "/WEB-INF/board/alert.jsp";
			}finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
