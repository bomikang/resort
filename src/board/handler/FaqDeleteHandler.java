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
				req.setAttribute("returnTo", "faq.do");
				req.setAttribute("alertText", "성공적으로 삭제되었습니다.");
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				req.setAttribute("returnTo", "faq.do");
				req.setAttribute("alertText", "오류가 발생되어 삭제되지 못했습니다.");
			}finally {
				JdbcUtil.close(conn);
			}
			
			return "/WEB-INF/board/alert.jsp";
		}else if(req.getMethod().equalsIgnoreCase("get")){
			String numbers = req.getParameter("fNo");
			if(numbers==null||numbers==""){
				req.setAttribute("returnTo", "faq.do");
				req.setAttribute("alertText", "오류가 발생되어 삭제되지 못했습니다.");
			}
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				FaqDao fDao = FaqDao.getInstance();
				conn.setAutoCommit(false);
				fDao.deleteFaq(conn, Integer.parseInt(numbers));
				conn.commit();
				req.setAttribute("returnTo", "faq.do");
				req.setAttribute("alertText", "성공적으로 삭제되었습니다.");
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				req.setAttribute("returnTo", "faq.do");
				req.setAttribute("alertText", "오류가 발생되어 삭제되지 못했습니다.");
			}finally {
				JdbcUtil.close(conn);
			}
			
			return "/WEB-INF/board/alert.jsp";
		}
		return null;
	}

}
