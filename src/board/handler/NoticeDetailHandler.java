package board.handler;

import java.sql.Connection;

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

public class NoticeDetailHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			String nNo = req.getParameter("no");
			String index = req.getParameter("page");
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				NoticeDao nDao = NoticeDao.getInstance();
				Notice notice = null;
				LoginMemberInfo writer = (LoginMemberInfo) req.getSession(false).getAttribute("user_info");
				if(writer == null || writer.getIsMng()==false){
					notice = nDao.updateReadCntNotice(conn, Integer.parseInt(nNo));
				}else if(writer != null && writer.getIsMng()==true){
					notice = nDao.selectNoticeByNo(conn, Integer.parseInt(nNo));
				}
				if(notice == null){
					res.sendRedirect("notice.do?page=1");
				}else{
					NoticeDetailDao ndDao = NoticeDetailDao.getInstance();
					NoticeDetail detail = ndDao.selectDetailByNo(conn, notice.getNo());
					System.out.println("detail : "+detail.getDetail());
					detail.setDetail(detail.getDetail().replaceAll("`", "'"));
					detail.setDetail(detail.getDetail().replaceAll("\r\n", "<br>"));
					detail.setDetail(detail.getDetail().replaceAll("\u0020", "&nbsp;"));
					req.setAttribute("index", index);
					req.setAttribute("notice", notice);
					req.setAttribute("detail", detail);
					return "index.jsp?page=/WEB-INF/board/bd_notice_detail&menu=/WEB-INF/board/board_menu";
				}
			}finally {
				JdbcUtil.close(conn);
			}
		}else if(req.getMethod().equalsIgnoreCase("post")){//리스트 상단 화면에 보이는 공지사항
			String nNo = req.getParameter("nNo");
			
			if(nNo.equals("")||nNo==null){
				res.sendRedirect("notice.do?page=1");
			}
			
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				NoticeDao nDao = NoticeDao.getInstance();
				conn.setAutoCommit(false);
				Notice notice = nDao.selectNoticeByNo(conn, Integer.parseInt(nNo));
				if(notice.isState()==false){
					notice.setState(true);
				}else{
					notice.setState(false);
				}
				nDao.updateNotice(conn, notice);
				
				res.sendRedirect("notice.do?page=1");
				conn.commit();
			}catch (Exception e) {
				conn.rollback();
			}finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
