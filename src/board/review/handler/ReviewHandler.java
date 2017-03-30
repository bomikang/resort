package board.review.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.Notice;
import board.model.NoticeDao;
import board.review.model.ListPageCount;
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
				String pageNoVal = req.getParameter("pageNo"); // 클릭한 해당 페이지 위치	
				ReviewDao dao = ReviewDao.getInstance();
				int pageno = 1;
				if(pageNoVal != null){
					pageno = Integer.parseInt(pageNoVal);
				}
				ListPageCount listPage = dao.getArticlePage(pageno);
				req.setAttribute("cntPage", listPage);
				/*중요공지 리스트*/
				NoticeDao nDao = NoticeDao.getInstance();
				List<Notice> rnList = new ArrayList<>();
				
				System.out.println("rnList : true");
				rnList = nDao.selectRealNotice(conn);
			
				req.setAttribute("rnList", rnList);
			} finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/board/review_list&menu=/WEB-INF/board/board_menu";
		}
		return null;

	}
	
}