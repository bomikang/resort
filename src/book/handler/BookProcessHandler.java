package book.handler;

import java.sql.Connection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import member.model.MemberDao;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class BookProcessHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println();
		LoginMemberInfo myInfo = (LoginMemberInfo) req.getSession().getAttribute("myinfo");
		if(myInfo==null){
			res.sendRedirect("login.do");
		}
		if(req.getMethod().equalsIgnoreCase("get")){			
			int strNo = Integer.parseInt(req.getParameter("strNo"));
			long date = Long.parseLong(req.getParameter("date"));
			
			System.out.println("book_process_strNo : "+strNo);
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				BookDao bDao = BookDao.getInstance();
				Member mem = new Member(myInfo.getMy_id());
				mem.setNo(myInfo.getMy_no());
				Integer count = bDao.selectCountByMember(conn, mem);
				if(count>=10){
					req.setAttribute("noCount", true);
					return "index.jsp?page=/WEB-INF/book/bk_now&menu=/WEB-INF/book/bk_menu";
				}else{
					req.setAttribute("noCount", false);
				}
				StructureDao sDao = StructureDao.getInstance();
				Structure str = sDao.getStructureByNo(conn, strNo);
				if(str == null){
					req.setAttribute("noStr", true);
					return "index.jsp?page=/WEB-INF/book/bk_now&menu=/WEB-INF/book/bk_menu";
				}else{					
					req.setAttribute("startDate", date);
					req.setAttribute("str", str);
				}
			}finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/book/bk_book&menu=/WEB-INF/book/bk_menu";
		
		}else if(req.getMethod().equalsIgnoreCase("post")){
			int strNo = Integer.parseInt(req.getParameter("strNo"));
			int memNo = Integer.parseInt(req.getParameter("memNo"));
			String startDate = req.getParameter("start");
			System.out.println(startDate);
			String endDate = req.getParameter("end");
			System.out.println(endDate);
			String tel1 = req.getParameter("bkTel1");
			String tel2 = req.getParameter("bkTel2");
			String tel3 = req.getParameter("bkTel3");
			
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				StructureDao sDao = StructureDao.getInstance();
				MemberDao mDao = MemberDao.getInstance();
				
				Structure str= sDao.getStructureByNo(conn, strNo);
				Member mem = mDao.selectByNo(conn, memNo);
				if(str==null||mem==null){
					return null;
				}
				System.out.println(1);
				Book book = new Book();
				
				Date regDate = new Date(System.currentTimeMillis());
				book.setRegDate(regDate);
				System.out.println(book.getNoForm());
				book.setNo(book.getNoForm());				
				book.setStr(str);
				book.setMem(mem);
				book.setStartDate(Book.dateFormat.parse(startDate));
				book.setEndDate(Book.dateFormat.parse(endDate));
				book.setState("입금대기");
				book.setTel(tel1+"-"+tel2+"-"+tel3);
				
				BookDao bDao = BookDao.getInstance();
				bDao.insertBook(conn, book);
				conn.commit();
				req.setAttribute("result", true);
				req.setAttribute("book", book);
				String 	url= "bookcheckdetail.do?bkNo="+book.getNo()+"&pageId=process";
				res.sendRedirect(url);
				return null;
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println(2);
				conn.rollback();
				req.setAttribute("result", false);
				return "/WEB-INF/book/bk_price.jsp";
			}finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
