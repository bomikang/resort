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
			Qna qna = dao.getQnaByNo(con, qnaNo); //회원의 게시글

			if (qna.getContent() != null && !qna.getContent().equals("") ) {
				qna.setContent(qna.getContent().replaceAll("`", "'"));
				qna.setContent(qna.getContent().replaceAll("\r\n", "<br>"));
				qna.setContent(qna.getContent().replaceAll("u0020", "&nbsp;"));
			}
			req.setAttribute("qna", qna);

			
			Qna qnaFromAdmin = dao.getQnaFromAdmin(con, qnaNo); //관리자 게시글
			if (qnaFromAdmin != null) {

				if (qnaFromAdmin.getContent() != null && !qna.getContent().equals("")) {
					qnaFromAdmin.setContent(qnaFromAdmin.getContent().replaceAll("`", "'"));
					qnaFromAdmin.setContent(qnaFromAdmin.getContent().replaceAll("\r\n", "<br>"));
					qnaFromAdmin.setContent(qnaFromAdmin.getContent().replaceAll("u0020", "&nbsp;"));
				}

				req.setAttribute("qnaAdmin", qnaFromAdmin); //관리자 게시글이 존재하면 심어줌
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(con);
		}
		return "index.jsp?page=/WEB-INF/board/qna_detail&menu=/WEB-INF/board/board_menu";
	}
}
