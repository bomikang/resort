package board.review.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import board.review.model.Review;
import board.review.model.ReviewDao;
import board.review.model.Review_Detail;
import board.review.model.Review_DetailDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import mvc.controller.CommandHandler;

public class ReviewDetailList implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			LoginMemberInfo userinfo = (LoginMemberInfo) req.getSession(false).getAttribute("myinfo");
			
			ReviewDao rev_dao = ReviewDao.getInstance();
			Review_DetailDao detail_dao = Review_DetailDao.getInstance();
			
		//	Review_Detail detail = detail_dao.selectByNo(conn, userinfo.getMy_no());
			//Review rev_list= rev_dao.selectByNo(conn,userinfo.getMy_no());
		//	req.setAttribute("rev_list",detail );
		//req.setAttribute("rev_detail", rev_list);
		}finally{
			JdbcUtil.close(conn);
		}
		return "/WEB-INF/view/articleread.jsp";
	}

}
