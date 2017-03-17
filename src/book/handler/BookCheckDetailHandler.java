package book.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class BookCheckDetailHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String bkNo = req.getParameter("bkNo");
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			BookDao bDao = BookDao.getInstance();
			Book book = bDao.selectByNo(conn, bkNo);
			req.setAttribute("book", book);
			return "index.jsp?page=/WEB-INF/book/bk_detail&menu=/WEB-INF/book/bk_menu";
		}finally {
			JdbcUtil.close(conn);
		}
	}

}
