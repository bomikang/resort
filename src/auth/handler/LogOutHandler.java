package auth.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.controller.CommandHandler;

public class LogOutHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		//getSession(true) : 세션이 이미 있는 확인을 하여, 
							//이미 있다면 그 세션을 반환시키고 , 없으면 새로운 세션을 생성한다.
		//getSession(false) : 세션이 있다면 그 세션을 리턴하지만
		//						세션이 존재하지 않는다면 null을 리턴한다.
		HttpSession session = req.getSession(false);
		if(session != null){
			
			session.invalidate(); // session 삭제
			res.setHeader("Cache-Control","no-store");
			res.setHeader("Pragma", "no-cache");
			res.setDateHeader("Expires", 0);
			if(req.getProtocol().equals("HTTP/1.1"))
				res.setHeader("Cache-Control", "no-cache");	
			
			
		}
		return "index.jsp";
	}

}
