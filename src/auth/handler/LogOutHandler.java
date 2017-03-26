package auth.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import mvc.controller.CommandHandler;

public class LogOutHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		req.removeAttribute("user_info");
		HttpSession session = req.getSession(false); // 세션값이 없으면 null 값을 반환
		if(session != null){ // 세션의 데이터가 없을경우
			
			session.invalidate(); // 세션 삭제
			
			
		}
		return "index.jsp";
	}

}
