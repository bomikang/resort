package board.qna.handler;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class QnaInsertHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			return "index.jsp?page=/WEB-INF/board/qna_insert&menu=/WEB-INF/board/board_menu";
		}else{
			Connection con = null;
			int qnaNo = 0; //qna_detail페이지로 보내주기 위한 변수
			
			try {
				con = ConnectionProvider.getConnection();
				
				String title = req.getParameter("title");
				String content = req.getParameter("content");
				int article = 0; //원게시물 번호 <= 관리자일 때 파라미터 받아 옴
				
				//LoginMemberInfo memInfo = null;
				LoginMemberInfo memInfo = (LoginMemberInfo) req.getSession().getAttribute("user_info");
				MemberDao memberDao = MemberDao.getInstance();
				
				if(memInfo.getIsMng() == true){ //관리자
					article = Integer.parseInt(req.getParameter("article"));
				}
				
				Member member = memberDao.selectByNo(con, memInfo.getMy_no());
				
				QnaDao qnaDao = QnaDao.getInstance();
				Qna qna = new Qna(0, member, title, new Date(), article, content, false);
				qnaDao.insertQna(con, qna);
				
				//detail로 넘겨줄 때 필요한 게시물 번호
				//일반회원일 때는 마지막 게시물 번호가 방금등록한 번호임
				//관리자일 때는 받아온 article번호를 넘겨줌
				if (article == 0) qnaNo = qnaDao.getLastQnaNo(con);
				else qnaNo = article;
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			return "qnadetail.do?qnano="+qnaNo;
		}
		
	}

}
