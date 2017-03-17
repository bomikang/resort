package book.admin.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class BookListHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			Connection conn = null;
			List<Book> bList = null;
			try{
				conn = ConnectionProvider.getConnection();
				BookDao bDao = BookDao.getInstance();
				bList = bDao.selectAll(conn);
				req.setAttribute("bList", bList);
			}finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/book_admin/bk_list&menu=/WEB-INF/book/bk_menu";
		}
		return null;
	}

}
