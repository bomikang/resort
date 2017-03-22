package board.qna.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.qna.model.Qna;
import board.qna.model.QnaDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;

public class QnaHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection con = null;
			
			try {
				con = ConnectionProvider.getConnection();
				
				LoginMemberInfo memInfo = null;
				
				
				if (req.getSession().getAttribute("myinfo") != null) {
					memInfo = (LoginMemberInfo) req.getSession().getAttribute("myinfo");
					
					MemberDao memberDao = MemberDao.getInstance();
					Member member = memberDao.selectByNo(con, memInfo.getMy_no());
					
					QnaDao dao = QnaDao.getInstance();
					List<Qna> list = dao.selectAllQnaByMember(con, member);
					
					req.setAttribute("qnaList", list);
				}else if(req.getSession().getAttribute("admin") != null){
					memInfo = (LoginMemberInfo) req.getSession().getAttribute("admin");
					
					MemberDao memberDao = MemberDao.getInstance();
					Member member = memberDao.selectByNo(con, memInfo.getMy_no());
					
					QnaDao dao = QnaDao.getInstance();
					List<Qna> list = dao.selectAllQnaExceptAdmin(con);
					
					req.setAttribute("qnaList", list);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			return "index.jsp?page=/WEB-INF/board/qna_list&menu=/WEB-INF/board/board_menu";
		}
		return null;
	}

}
