package board.review.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.review.model.Review;
import board.review.model.ReviewDao;
import board.review.model.Review_Detail;
import board.review.model.Review_DetailDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class ReviewUpdateHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				int no = Integer.parseInt(req.getParameter("no"));
				ReviewDao dao1 = ReviewDao.getInstance();
				Review_DetailDao dao2 = Review_DetailDao.getInstance();
				Review rev = dao1.selectByNo(conn, no);
				Review_Detail rev_Detail = dao2.selectByNo(conn, no);
				req.setAttribute("rev", rev);
				req.setAttribute("rev_Detail", rev_Detail);
				return "index.jsp?page=/WEB-INF/board/review_update&menu=/WEB-INF/board/board_menu";
			} finally {
				JdbcUtil.close(conn);
			}

		} else if (req.getMethod().equalsIgnoreCase("post")) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				int no = Integer.parseInt(req.getParameter("no"));
				String title = req.getParameter("title");
				String content = req.getParameter("content");
				
				ReviewDao dao1 = ReviewDao.getInstance();
				Review_DetailDao dao2 = Review_DetailDao.getInstance();
				conn.setAutoCommit(false);
				Review rev = dao1.selectByNo(conn, no);
					rev.setRev_title(title);
				Review_Detail rev_Detail= new Review_Detail(no, content);
				
				dao1.update(conn, rev);
				dao2.update(conn, rev_Detail);
				System.out.println(rev_Detail.getRev_detail());
				conn.commit();
			} finally {
				JdbcUtil.close(conn);
			}
			res.sendRedirect("review.do");
		}
		return null;
	}
}
