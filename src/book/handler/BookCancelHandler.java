package book.handler;

import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class BookCancelHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("post")){
			String bkNo = req.getParameter("bkNo");
			System.out.println(bkNo);
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				BookDao bDao = BookDao.getInstance();
				Book book = new Book();
				book.setNo(bkNo);
				book.setCancelDate(new Date(System.currentTimeMillis()));
				bDao.updateCancelDate(conn, book);
				conn.commit();
				res.sendRedirect("bookcheck.do");
				return null;
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			}finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
