package board.review.handler;

import java.io.Writer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import board.review.model.Review;
import board.review.model.ReviewDao;
import board.review.model.Review_Deatail;
import board.review.model.Review_DetailDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import mvc.controller.CommandHandler;

public class ReviewInsertHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		if(req.getMethod().equalsIgnoreCase("get")){
			return "index.jsp?page=/WEB-INF/board/review_insert&menu=/WEB-INF/board/board_menu";					
		}else if(req.getMethod().equalsIgnoreCase("post")){
			//처리
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			LoginMemberInfo userInfo = (LoginMemberInfo)req.getSession(false).getAttribute("myinfo");
			Connection conn = null;
			
			try{
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				
				Date date = new Date();
				
				Review rev = new Review(0,
										userInfo.getMy_no(),
										title,
										userInfo.getMy_name(),
										date,
										0);
				ReviewDao dao = ReviewDao.getInstance();
				dao.insert(conn,rev);
				int number = 0;
				Review_DetailDao detailDao = Review_DetailDao.getInstance();
				List<Review> rev_no= dao.listAll(conn);
				for(int i=0;i<rev_no.size();i++){
					number =rev_no.get(i).getRev_no();
				}
				Review_Deatail detail = new Review_Deatail(number, content);
				detailDao.insert(conn, detail);
				
				conn.commit();
				
				
			}finally {
				JdbcUtil.close(conn);
			}
			return "/WEB-INF/view/newArticleSucess.jsp";
		}
		return null;
	}

}
