package board.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.Notice;
import board.model.NoticeDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class NoticeHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			String index = req.getParameter("index");
			if(index == ""|| index == null){
				index="1";						
			}
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				NoticeDao nDao = NoticeDao.getInstance();
				
				/*공지 리스트 */
				List<Notice> nList = nDao.selectAllNotice(conn, Integer.parseInt(index));
				req.setAttribute("nList", nList);
				
				/*중요공지 리스트*/
				if(index=="1"){
					List<Notice> rnList = nDao.selectRealNotice(conn);
					req.setAttribute("rnList", rnList);
				}
				
				int maxIndex = nDao.getMaxIndex(conn);
				req.setAttribute("maxIndex", maxIndex);
			}finally {
				JdbcUtil.close(conn);
			}
			
			return "index.jsp?page=/WEB-INF/board/bd_notice_list&menu=/WEB-INF/board/board_menu";
		}
		return null;
	}

}
