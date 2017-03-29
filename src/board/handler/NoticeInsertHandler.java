package board.handler;

import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.Notice;
import board.model.NoticeDao;
import board.model.NoticeDetail;
import board.model.NoticeDetailDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import mvc.controller.CommandHandler;

public class NoticeInsertHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			req.setAttribute("type", "insert");
			return "index.jsp?page=/WEB-INF/board/bd_notice_insert&menu=/WEB-INF/board/board_menu"; 
		}else if(req.getMethod().equalsIgnoreCase("post")){
			LoginMemberInfo writer = (LoginMemberInfo) req.getSession(false).getAttribute("user_info");
			if(writer != null && writer.getIsMng()==true){
				String title = req.getParameter("title");
				String detail = req.getParameter("detail");
				
				detail = detail.replaceAll("'", "`");// 특수문자(' -> `)처리
				String real = req.getParameter("real");
				System.out.println("detail : "+detail);
				System.out.println("real : "+real);
				boolean realNotice = false;
				
				if(real !=null && real.equalsIgnoreCase("on")){
					realNotice = true;
				}
				
				Connection conn = null;
				
				try{
					conn = ConnectionProvider.getConnection();
					NoticeDao nDao = NoticeDao.getInstance();
					NoticeDetailDao ndDao = NoticeDetailDao.getInstance();
					conn.setAutoCommit(false);
					
					Notice notice = new Notice(writer, title, new Date(System.currentTimeMillis()), 0, realNotice);
					int nNo = nDao.insertNotice(conn, notice);
					
					if(nNo != -1){
						NoticeDetail noticeDetail = new NoticeDetail(nNo, detail);
						ndDao.insertNotice(conn, noticeDetail);
					}else if(nNo == -1){
						throw new Exception();
					}
					
					conn.commit();
					res.sendRedirect("notice.do?page=1");
				}catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}finally {
					JdbcUtil.close(conn);
				}
			}
		}
		return null;
	}

}
