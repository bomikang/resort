package book.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class BookCheckHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LoginMemberInfo myinfo = (LoginMemberInfo) req.getSession().getAttribute("myinfo");
		if(myinfo==null){
			return "login.do";
		}
		if(req.getMethod().equalsIgnoreCase("get")){	
			if(myinfo != null){
				Connection conn = null;
				try{
					conn = ConnectionProvider.getConnection();
					StructureDao sDao = StructureDao.getInstance();
					List<Structure> strList = new ArrayList<>();
					List<Integer> idList = sDao.selectDistinctId(conn);
					for(int i=0;i<idList.size();i++){					
						Structure str = new Structure();
						str.setId(idList.get(i));
						strList.add(str);
					}
					req.setAttribute("strId", strList);
					
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
		}else if(req.getMethod().equalsIgnoreCase("post")){
			
			String state = req.getParameter("bkState");
			String[] states = state.split(",");
			System.out.println(states[0]);
			System.out.println(req.getParameter("strId"));
			String strId = req.getParameter("strId");
			if(strId.isEmpty()){
				strId="1";
			}
			Connection conn = null;
			try{
				conn= ConnectionProvider.getConnection();
				
				StructureDao sDao = StructureDao.getInstance();
				List<Structure> strList = new ArrayList<>();
				List<Integer> idList = sDao.selectDistinctId(conn);
				for(int i=0;i<idList.size();i++){					
					Structure str = new Structure();
					str.setId(idList.get(i));
					strList.add(str);
				}
				req.setAttribute("strId", strList);
				
				BookDao bDao = BookDao.getInstance();
				Book condition = new Book();
				Member mem = new Member(myinfo.getMy_id());
				mem.setNo(myinfo.getMy_no());
				condition.setMem(mem);
				Structure str = new Structure();
				str.setId(Integer.parseInt(strId));
				condition.setStr(str);
				List<Book>bList =bDao.selectByMember(conn, condition, states);
				req.setAttribute("bList", bList);
				
				//data�� json ���� ����
				ObjectMapper om= new ObjectMapper();
				String json = "["+om.writeValueAsString(str.getNameById())+","+om.writeValueAsString(bList)+"]";
				// json �߽�
				res.setContentType("application/json;charset=utf-8");
				PrintWriter pw = res.getWriter();
				pw.print(json);
				pw.flush();
			}finally {
				JdbcUtil.close(conn);
			}
			
			return null;
		}
		return null;
	}

}
