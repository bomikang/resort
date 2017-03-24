package board.qna.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.qna.model.Qna;
import board.qna.model.QnaDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class QnaDetailHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
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
		return "index.jsp?page=/WEB-INF/board/qna_detail&menu=/WEB-INF/board/board_menu";
	}
}
