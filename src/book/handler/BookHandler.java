package book.handler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;

public class BookHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			
			return "index.jsp?page=/WEB-INF/book/bk_now&menu=/WEB-INF/book/bk_menu";
		}
		return null;
	}

}
