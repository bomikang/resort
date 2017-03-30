package board.handler;

import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.Faq;
import board.model.FaqDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import mvc.controller.CommandHandler;

public class FaqInsertHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			return "index.jsp?page=/WEB-INF/board/faq_detail&menu=/WEB-INF/board/board_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			
			LoginMemberInfo admin = (LoginMemberInfo) req.getSession().getAttribute("user_info");
			if(admin != null && admin.getIsMng() == true){
				String title = req.getParameter("title");
				String detail = req.getParameter("detail");
				detail = detail.replaceAll("'", "`");
				Connection conn = null;
				try{
					conn = ConnectionProvider.getConnection();
					FaqDao fDao = FaqDao.getInstance();
					conn.setAutoCommit(false);
					Faq faq = new Faq(admin, title, detail, new Date(System.currentTimeMillis()));
					fDao.insertIntoFaq(conn, faq);
					conn.commit();
					req.setAttribute("alertText", "등록에 성공하였습니다.");
				}catch (Exception e) {
					conn.rollback();
					req.setAttribute("alertText", "오류가 발생하여 등록되지 못했습니다.");
				}finally {
					req.setAttribute("returnTo", ("faq.do"));
					JdbcUtil.close(conn);
				}				
			}
			return "/WEB-INF/board/alert.jsp";
		}
		return null;
		
	}

}
