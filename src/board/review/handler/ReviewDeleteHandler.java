package board.review.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.review.model.Review;
import board.review.model.ReviewDao;
import board.review.model.Review_DetailDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import mvc.controller.CommandHandler;

public class ReviewDeleteHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			ReviewDao rev_dao=ReviewDao.getInstance();
			Review_DetailDao detail_dao =Review_DetailDao.getInstance();
			int no =Integer.parseInt(req.getParameter("no")); // 선택된 게시물의 번호
			conn.setAutoCommit(false);
			rev_dao.delete(conn, no);
			detail_dao.delete(conn, no);
			conn.commit();
		}finally{
			JdbcUtil.close(conn);
		}
		res.sendRedirect("review.do");
		return null;
	}

}
