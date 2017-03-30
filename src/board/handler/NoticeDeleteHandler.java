package board.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.NoticeDao;
import board.model.NoticeDetailDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class NoticeDeleteHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
			String index = req.getParameter("page");
			String numbers = req.getParameter("numbers");
			String[] numList = numbers.split(",");
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				NoticeDao nDao = NoticeDao.getInstance();
				NoticeDetailDao ndDao = NoticeDetailDao.getInstance();
				conn.setAutoCommit(false);
				int nRes = nDao.deleteNotices(conn, numList);
				int ndRes = ndDao.deleteNoticeDetails(conn, numList);
				if(nRes==ndRes){
					conn.commit();
					req.setAttribute("alertText", "성공적으로 삭제되었습니다.");
				}else{
					throw new Exception();
				}
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				req.setAttribute("alertText", "오류가 발생되어 삭제되지 못했습니다.");
			}finally {
				JdbcUtil.close(conn);
			}

			req.setAttribute("returnTo", ("notice.do?page="+index));
			return "/WEB-INF/board/alert.jsp";
			
	}

}
