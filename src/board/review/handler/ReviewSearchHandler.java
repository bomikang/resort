package board.review.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.review.model.ListPageCount;
import board.review.model.Review;
import board.review.model.ReviewDao;
import board.review.model.Review_Detail;
import board.review.model.Review_DetailDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import mvc.controller.CommandHandler;

public class ReviewSearchHandler implements CommandHandler{
	 int size = 10;
	 List<Review> content = null;
	 int total = 0;
	 int pageno = 1;
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String selectVal = req.getParameter("select");
			String writeVal = req.getParameter("write");
			String pageNoVal = req.getParameter("pageNo"); // 클릭한 해당 페이지 위치	
			
			ReviewDao rev_dao = ReviewDao.getInstance();
			if(pageNoVal != null){
				pageno = Integer.parseInt(pageNoVal);
			}
			if(selectVal.equals("title")){
				total = rev_dao.searchCnt_Title(conn, writeVal);
				content = rev_dao.searchTitleOrderBy(conn, writeVal, (pageno-1),size); 
						
			}else if(selectVal.equals("name")){
				total = rev_dao.searchCnt_name(conn, writeVal);
				content = rev_dao.searchNameOrderBy(conn, writeVal, (pageno-1),size); 
				
			}else if(selectVal.equals("number")){
				total = rev_dao.searchCnt_number(conn, Integer.parseInt(writeVal));
				content = rev_dao.searchNumberOrderBy(conn, Integer.parseInt(writeVal), (pageno-1),size); 
				
			}
			ListPageCount paging = new ListPageCount(total,
					pageno, 
					size, 
					content);
			
			req.setAttribute("cntPage", paging);
			
		}finally{
			JdbcUtil.close(conn);
		}
		return "index.jsp?page=/WEB-INF/board/review_searchlist&menu=/WEB-INF/board/board_menu";
	}

}
