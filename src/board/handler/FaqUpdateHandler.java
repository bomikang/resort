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

public class FaqUpdateHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			String fNo = req.getParameter("fNo");
			if(fNo != null){
				Connection conn = null;
				try{
					conn = ConnectionProvider.getConnection();
					FaqDao fDao = FaqDao.getInstance();
					Faq faq = fDao.selectFaqByNo(conn, Integer.parseInt(fNo));
					req.setAttribute("faq", faq);
					req.setAttribute("type", "update");
				}finally {
					JdbcUtil.close(conn);
				}
			}else{
				res.sendRedirect("faq.do");
			}
			return "index.jsp?page=/WEB-INF/board/faq_detail&menu=/WEB-INF/board/board_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){			
			LoginMemberInfo admin = (LoginMemberInfo) req.getSession().getAttribute("user_info");
			if(admin != null && admin.getIsMng() == true){
				String title = req.getParameter("title");
				String detail = req.getParameter("detail");
				String fNo = req.getParameter("fNo");
				
				Connection conn = null;
				try{
					conn = ConnectionProvider.getConnection();
					FaqDao fDao = FaqDao.getInstance();
					conn.setAutoCommit(false);
					Faq faq = new Faq(admin, title, detail, new Date(System.currentTimeMillis()));
					faq.setNo(Integer.parseInt(fNo));
					fDao.updateFaq(conn, faq);
					conn.commit();
					req.setAttribute("returnTo", "faq.do");
					req.setAttribute("alertText", "성공적으로 수정되었습니다.");
				}catch (Exception e) {
					conn.rollback();
					req.setAttribute("returnTo", ("faqupdate.do?fNo="+fNo));
					req.setAttribute("alertText", "오류가 발생하여 수정되지 못했습니다.");
				}finally {
					JdbcUtil.close(conn);
				}				
			}
			return "/WEB-INF/board/alert.jsp";
		}
		return null;		
	}

}
