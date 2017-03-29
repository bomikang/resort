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

public class NoticeUpdateHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			String index = req.getParameter("page");
			String nNo = req.getParameter("no");
			if(nNo != null){
				Connection conn = null;
				try{
					conn = ConnectionProvider.getConnection();
					NoticeDao nDao = NoticeDao.getInstance();
					NoticeDetailDao ndDao = NoticeDetailDao.getInstance();
					
					Notice notice = nDao.selectNoticeByNo(conn, Integer.parseInt(nNo));
					NoticeDetail detail = ndDao.selectDetailByNo(conn, Integer.parseInt(nNo));
					
					req.setAttribute("index", index);
					req.setAttribute("notice", notice);
					req.setAttribute("detail", detail);
					req.setAttribute("type", "update");
				}finally {
					JdbcUtil.close(conn);
				}
				return "index.jsp?page=/WEB-INF/board/bd_notice_insert&menu=/WEB-INF/board/board_menu";
			}else{
				String url="notice.do?page="+index;
				res.sendRedirect(url);
				return null;
			}			
		}else if(req.getMethod().equalsIgnoreCase("post")){
			
			LoginMemberInfo admin = (LoginMemberInfo) req.getSession().getAttribute("user_info");
			
			if(admin != null && admin.getIsMng() == true){
				String title = req.getParameter("title");
				String detail = req.getParameter("detail");
				String real = req.getParameter("real");
				System.out.println("real : "+real);
				String nNo = req.getParameter("nNo");
				System.out.println("nNo : "+nNo);
				boolean realNotice = false;
				if(real != null){
					if(real.equals("on")){
						realNotice = true;
					}
				}
				
				Connection conn = null;
				try{
					conn = ConnectionProvider.getConnection();
					NoticeDao nDao = NoticeDao.getInstance();
					NoticeDetailDao ndDao = NoticeDetailDao.getInstance();
					conn.setAutoCommit(false);
					Notice notice = new Notice();
					notice.setNo(Integer.parseInt(nNo));
					notice.setTitle(title);
					notice.setState(realNotice);
					NoticeDetail nDetail = new NoticeDetail(Integer.parseInt(nNo), detail);
					
					int nRes = nDao.updateNotice(conn, notice);
					int dRes = ndDao.updateNotice(conn, nDetail);
					
					if(nRes == dRes){
						conn.commit();
					}else{
						throw new Exception();
					}
				}catch (Exception e) {
					conn.rollback();
				}finally {
					JdbcUtil.close(conn);
				}				
			}
		}
		String index = req.getParameter("index");
		if(index == null||index.equals("")){
			index="1";
		}
		String url = "notice.do?page="+index;
		res.sendRedirect(url);
		return null;
	}
	
}
