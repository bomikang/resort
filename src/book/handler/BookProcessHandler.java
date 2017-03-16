package book.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;

public class BookProcessHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			
			return "index.jsp?page=/WEB-INF/book/bk_book&menu=/WEB-INF/book/bk_menu";
		}
		return null;
	}

}
