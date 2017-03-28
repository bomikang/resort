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
			LoginMemberInfo userinfo = (LoginMemberInfo) req.getSession(false).getAttribute("user_info");
			ReviewDao rev_dao = ReviewDao.getInstance();
			Review_DetailDao detail_dao = Review_DetailDao.getInstance();
			int no = Integer.parseInt(req.getParameter("no"));
			System.out.println(no);
			Review rev_list= rev_dao.selectByNo(conn,no); // 화면에서 받은 매개변수로..
			Review_Detail detail = detail_dao.selectByNo(conn,no);//  게시물번호 입력해야됨 
			// 현재 사용자 == 게시물을 올린 사용자
			if(userinfo.getMy_no()==rev_list.getRev_mem()){
				req.setAttribute("ok", true);
			}else{
				req.setAttribute("no", true);
			}
			req.setAttribute("rev_list",rev_list );
			req.setAttribute("rev_detail", detail);
		}finally{
			JdbcUtil.close(conn);
		}
		return "index.jsp?page=/WEB-INF/board/review_detail&menu=/WEB-INF/board/board_menu";
	}

}
