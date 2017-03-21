package book.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import mvc.controller.CommandHandler;

public class BookCheckHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			LoginMemberInfo myinfo = (LoginMemberInfo) req.getSession().getAttribute("myinfo");
			if(myinfo != null){
				Connection conn = null;
				try{
					conn = ConnectionProvider.getConnection();
					BookDao bDao = BookDao.getInstance();
					Member mem = new Member(myinfo.getMy_id());
					mem.setNo(myinfo.getMy_no());
					List<Book>bList =  bDao.selectByMember(conn, mem);
					req.setAttribute("bList", bList);
					
				}finally {
					JdbcUtil.close(conn);
				}
			}
			
			return "index.jsp?page=/WEB-INF/book/bk_check&menu=/WEB-INF/book/bk_menu";
		}
		return null;
	}

}
