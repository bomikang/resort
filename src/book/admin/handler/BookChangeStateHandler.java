package book.admin.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class BookChangeStateHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("post")){
			String bkNo = req.getParameter("bkNo");
			System.out.println("bkNo : "+bkNo);
			String state = req.getParameter("state");
			System.out.println("state : "+state);
			Book resBook = null;
			boolean result = false;
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				BookDao bDao = BookDao.getInstance();
				if(state.equals("예약취소")){
					Book book = new Book();
					book.setNo(bkNo);
					book.setState(state);
					book.setCancelDate(new Date());
					bDao.updateCancelDate(conn, book);
				}else{
					Book book = new Book();
					book.setNo(bkNo);
					book.setState(state);
					bDao.updateBookState(conn, book);
				}
				conn.commit();
				resBook = bDao.selectByNo(conn, bkNo);
				result = true;
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				result = false;
				
			}finally {
				ObjectMapper om= new ObjectMapper();
				String json = "";
				if(resBook != null){
					json = "["+om.writeValueAsString(result)+","+om.writeValueAsString(resBook)+"]";
				}else{
					json = "["+om.writeValueAsString(result)+"]";
				}
				res.setContentType("application/json;charset=utf-8");
				PrintWriter pw = res.getWriter();
				pw.print(json);
				pw.flush();
				System.out.println(json);
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
