package board.qna.handler;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.qna.model.Qna;
import board.qna.model.QnaDao;
import board.qna.model.QnaDetailDao;
import book.model.Book;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class QnaInsertHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			return "index.jsp?page=/WEB-INF/board/qna_insert&menu=/WEB-INF/board/board_menu";
		}else{
			Connection con = null;
			
			try {
				con = ConnectionProvider.getConnection();
				
				String title = req.getParameter("title");
				String content = req.getParameter("content");
				
				/* 로그인 되어있는 사람의 상태가 관리자일 때와 일반 회원일 때로 구분하는 구문 필요 
				 * 우선 일반회원의 경우에만 완료.*/
				
				LoginMemberInfo myInfo = (LoginMemberInfo) req.getSession().getAttribute("myinfo");
				MemberDao memberDao = MemberDao.getInstance();
				Member member = memberDao.selectByNo(con, myInfo.getMy_no());
				
				QnaDao qnaDao = QnaDao.getInstance();
				QnaDetailDao qnaDetailDao = QnaDetailDao.getInstance();
				
				Qna qna = new Qna(0, member, title, new Date(), 0, content);
				
				qnaDao.insertQna(con, qna);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			return "index.jsp?page=/WEB-INF/board/qna_detail&menu=/WEB-INF/board/board_menu";
		}
		
	}

}
