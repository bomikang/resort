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
				int article = 0;
				
				/*관리자일 때만 넘어오는 article 파라미터 존재*/
				if (req.getParameter("article") != null) {
					article = Integer.parseInt(req.getParameter("article"));
				}
				
				QnaDao dao = QnaDao.getInstance();
				Qna qna = dao.getQnaByNo(con, qnaNo);
				
				dao.deleteQnaWhenUser(con, qna);
				
				//관리자는 삭제 후 회원 게시물 detail로 감
				if (article != 0) { 
					qnaNo = article;
					return "qnadetail.do?qnano="+qnaNo;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("ㅎㅎ");
		}
		
		
		return "qna.do";
	}

}
