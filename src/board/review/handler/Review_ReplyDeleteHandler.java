package board.review.handler;

import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import board.review.model.Reply;
import board.review.model.ReplyDao;
import board.review.model.Review;
import board.review.model.ReviewDao;
import board.review.model.Review_Detail;
import board.review.model.Review_DetailDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class Review_ReplyDeleteHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 if(req.getMethod().equalsIgnoreCase("post")){
			Connection conn = null;	
			try{
				
				conn = ConnectionProvider.getConnection();
				ReplyDao repdao = ReplyDao.getInstance();
				int rep_no = Integer.parseInt(req.getParameter("rep_no"));
				int rev_no = Integer.parseInt(req.getParameter("rev_no"));
				LoginMemberInfo userInfo = (LoginMemberInfo)req.getSession(false).getAttribute("user_info");
				conn.setAutoCommit(false);
				repdao.delete(conn, rep_no);
				List<Reply> rep_list=repdao.rep_list(conn, rev_no); // 해당 게시글의 댓글
				HashMap<String, List<Reply>> map = new HashMap<>();
				map.put("data", rep_list);	
				conn.commit();
				//json 사용 시 필요구문
				ObjectMapper om= new ObjectMapper();
				String json = om.writeValueAsString(map);	//  JSP화면 JSON 데이터 result값 반환 
				res.setContentType("application/json;charset=utf-8");
				PrintWriter pw = res.getWriter();
				pw.print(om.writeValueAsString(map));
				pw.flush();
			}finally {
				JdbcUtil.close(conn);
			}	
		}
		
		return null;
		
	}

}
