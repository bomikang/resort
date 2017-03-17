package auth.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.controller.CommandHandler;

public class LogOutHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		//getSession(true) : ������ �̹� �ִ� Ȯ���� �Ͽ�, 
							//�̹� �ִٸ� �� ������ ��ȯ��Ű�� , ������ ���ο� ������ �����Ѵ�.
		//getSession(false) : ������ �ִٸ� �� ������ ����������
		//						������ �������� �ʴ´ٸ� null�� �����Ѵ�.
		HttpSession session = req.getSession(false);
		if(session != null){
			
			session.invalidate(); // session ����
			res.setHeader("Cache-Control","no-store");
			res.setHeader("Pragma", "no-cache");
			res.setDateHeader("Expires", 0);
			if(req.getProtocol().equals("HTTP/1.1"))
				res.setHeader("Cache-Control", "no-cache");	
			
			
		}
		return "index.jsp";
	}

}
