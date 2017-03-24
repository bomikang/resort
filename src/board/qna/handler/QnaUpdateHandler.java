package board.qna.handler;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.qna.model.Qna;
import board.qna.model.QnaDao;
import book.model.Book;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class QnaUpdateHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection con = null;

			try {
				con = ConnectionProvider.getConnection();

				int qnaNo = Integer.parseInt(req.getParameter("qnano"));

				QnaDao dao = QnaDao.getInstance();
				Qna qna = dao.getQnaByNo(con, qnaNo);

				req.setAttribute("qna", qna);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcUtil.close(con);
			}
			
			return "index.jsp?page=/WEB-INF/board/qna_update&menu=/WEB-INF/board/board_menu";
		}else{
			Connection con = null;
			
			int qnaNo = Integer.parseInt(req.getParameter("qnano"));
			
			try {
				con = ConnectionProvider.getConnection();
			
				int article = 0;
				String title = req.getParameter("title");
				String content = req.getParameter("content");
				
				/*관리자일 때만 넘어오는 article 파라미터 존재*/
				if (req.getParameter("article") != null) {
					article = Integer.parseInt(req.getParameter("article"));
				}
				
				QnaDao qnaDao = QnaDao.getInstance();
				Qna qna = qnaDao.getQnaByNo(con, qnaNo);
				qna.setTitle(title);
				qna.setContent(content);
				
				qnaDao.updateQna(con, qna);
				
				if (article != 0) qnaNo = article; //관리자는 답변을 달았던 회원의 게시물을 보여줌	
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			return "qnadetail.do?qnano="+qnaNo;
		}
	}
}
