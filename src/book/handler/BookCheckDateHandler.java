package book.handler;

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
import structure.model.Structure;

public class BookCheckDateHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("post")){
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			String strNo = req.getParameter("strNo");
			
			Structure str = new Structure();
			str.setNo(Integer.parseInt(strNo));
			Date start = Book.dateFormat.parse(startDate);
			Date end = Book.dateFormat.parse(endDate);
			
			Book book = new Book();
			book.setStartDate(start);
			book.setEndDate(end);
			book.setStr(str);
			
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				BookDao bDao = BookDao.getInstance();
				boolean result = bDao.checkBookDate(conn, book);
				
				ObjectMapper om= new ObjectMapper();
				String json = om.writeValueAsString(result);
				// json �߽�
				res.setContentType("application/json;charset=utf-8");
				PrintWriter pw = res.getWriter();
				pw.print(json);
				pw.flush();
			}finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
