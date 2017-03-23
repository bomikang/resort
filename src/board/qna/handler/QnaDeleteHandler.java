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
		Connection con = null;
		
		try {
			/*아직 회원 모드밖에 구현 안함*/
			con = ConnectionProvider.getConnection();
			
			int qnaNo = Integer.parseInt(req.getParameter("qnano"));
			
			QnaDao dao = QnaDao.getInstance();
			Qna qna = dao.getQnaByNo(con, qnaNo);
			
			dao.deleteQnaWhenUser(con, qna);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "qna.do";
	}

}
