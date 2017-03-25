package board.review.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.review.model.Review;
import board.review.model.ReviewDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import mvc.controller.CommandHandler;

public class ReviewHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				ReviewDao dao = ReviewDao.getInstance();
				LoginMemberInfo userInfo = (LoginMemberInfo)req.getSession().getAttribute("user_info");
				List<Review> rev_list = dao.listAll(conn);
				
				req.setAttribute("list", rev_list);
			} finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/board/review_list&menu=/WEB-INF/board/board_menu";
		}
		return null;

	}
}