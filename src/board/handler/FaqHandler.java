package board.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.Faq;
import board.model.FaqDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class FaqHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				FaqDao fDao = FaqDao.getInstance();
				List<Faq> fList = fDao.selectAllFaq(conn);
				req.setAttribute("fList", fList);
			}finally {
				JdbcUtil.close(conn);
			}
			
			
			return "index.jsp?page=/WEB-INF/board/faq_list&menu=/WEB-INF/board/board_menu";
		}
		return null;
	}

}
