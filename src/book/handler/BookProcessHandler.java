package book.handler;

import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.model.Book;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class BookProcessHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			int strNo = Integer.parseInt(req.getParameter("strNo"));
			long date = Long.parseLong(req.getParameter("date"));
			
			System.out.println("book_process_strNo : "+strNo);
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				StructureDao sDao = StructureDao.getInstance();
				Structure str = sDao.getStructureByNo(conn, strNo);
				if(str == null){
					req.setAttribute("noStr", true);
					return "index.jsp?page=/WEB-INF/book/bk_now&menu=/WEB-INF/book/bk_menu";
				}else{					
					req.setAttribute("startDate", date);
					req.setAttribute("str", str);
				}
			}finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/book/bk_book&menu=/WEB-INF/book/bk_menu";
		}
		return null;
	}

}
