package board.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;

public class NoticeInsertHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			return "index.jsp?page=/WEB-INF/board/bd_notice_insert&menu=/WEB-INF/board/board_menu"; 
		}
		return null;
	}

}
