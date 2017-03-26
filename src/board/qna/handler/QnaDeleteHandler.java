package board.qna.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.qna.model.Qna;
import board.qna.model.QnaDao;
import jdbc.ConnectionProvider;
import mvc.controller.CommandHandler;

public class QnaDeleteHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection con = null;
			
			try {
				/*아직 회원 모드밖에 구현 안함*/
				con = ConnectionProvider.getConnection();
				
				int qnaNo = Integer.parseInt(req.getParameter("qnano"));
				
				QnaDao dao = QnaDao.getInstance();
				Qna qna = dao.getQnaByNo(con, qnaNo);
				
				dao.deleteQnaWhenUser(con, qna);
				
				return "qna.do";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			/*관리자는 삭제할 때 메소드 방식이 post*/
			Connection con = null;
			
			try {
				con = ConnectionProvider.getConnection();
				
				int qnaNo = Integer.parseInt(req.getParameter("qnano"));
				int article = Integer.parseInt(req.getParameter("article"));
				
				QnaDao dao = QnaDao.getInstance();
				Qna qna = dao.getQnaByNo(con, qnaNo);
				
				dao.deleteQnaWhenUser(con, qna);
				
				return "qnadetail.do?qnano="+article;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
