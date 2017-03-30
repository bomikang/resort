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
				req.setAttribute("alertText", "성공적으로 취소되었습니다.");
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				req.setAttribute("alertText", "오류가 발생되어 수정에 실패하였습니다.");
			}finally {
				JdbcUtil.close(conn);
			}
		}
		req.setAttribute("returnTo", ("bookcheck.do"));
		return "/WEB-INF/board/alert.jsp";
	}

}
