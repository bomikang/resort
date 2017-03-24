package board.qna.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import board.qna.model.Qna;
import board.qna.model.QnaDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class QnaHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection con = null;
			
			try {
				con = ConnectionProvider.getConnection();
				
				LoginMemberInfo memInfo = (LoginMemberInfo) req.getSession().getAttribute("user_info");
				MemberDao memberDao = MemberDao.getInstance();
				QnaDao dao = QnaDao.getInstance();
				List<Qna> list = new ArrayList<>();
				
				if (memInfo.getIsMng() == false) { //일반회원
					Member member = memberDao.selectByNo(con, memInfo.getMy_no());
					list = dao.selectAllQnaByMember(con, member);
				}else if(memInfo.getIsMng() == true){ //관리자
					list = dao.incompleteReplyList(con);
				}
				
				req.setAttribute("qnaList", list);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			return "index.jsp?page=/WEB-INF/board/qna_list&menu=/WEB-INF/board/board_menu";
			
		}else{
			Connection con = null;
			
			String checkReply = req.getParameter("checkReply");
			
			try {
				con = ConnectionProvider.getConnection();
				
				QnaDao dao = QnaDao.getInstance();
				List<Qna> list = new ArrayList<>();
				
				if (checkReply.equals("incomplete")){ //답변 미완료
					list = dao.incompleteReplyList(con);
				}else if (checkReply.equals("complete")){ //답변 완료
					list = dao.completeReplyList(con);
				}else if (checkReply.equals("all")) { //게시글 전체
					list = dao.selectAllQnaExceptAdmin(con);
				}
				
				ObjectMapper om = new ObjectMapper();
				String json = om.writeValueAsString(list);
				
				res.setContentType("application/json;charset=utf-8");
				
				PrintWriter pw = res.getWriter();
				pw.print(json);
				pw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
		}
		return null;
	}

}
