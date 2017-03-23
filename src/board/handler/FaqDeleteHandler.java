package board.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.FaqDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class FaqDeleteHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("post")){
			String numbers = req.getParameter("numbers");
			String[] numList = numbers.split(",");
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				FaqDao fDao = FaqDao.getInstance();
				conn.setAutoCommit(false);
				fDao.deleteFaq(conn, numList);
				conn.commit();
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			}finally {
				JdbcUtil.close(conn);
			}
			
			res.sendRedirect("faq.do");
		}else if(req.getMethod().equalsIgnoreCase("get")){
			String numbers = req.getParameter("fNo");
			if(numbers==null||numbers==""){
				res.sendRedirect("faq.do");
			}
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				FaqDao fDao = FaqDao.getInstance();
				conn.setAutoCommit(false);
				fDao.deleteFaq(conn, Integer.parseInt(numbers));
				conn.commit();
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			}finally {
				JdbcUtil.close(conn);
			}
			
			res.sendRedirect("faq.do");
		}
		return null;
	}

}
