package book.admin.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.Member;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class BookListHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			Connection conn = null;
			List<Book> bList = null;
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
				bList = bDao.selectAll(conn);
				req.setAttribute("bList", bList);
			}finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/book_admin/bk_list&menu=/WEB-INF/book/bk_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			
			String state = req.getParameter("cdState");
			String[] states = state.split(",");
			System.out.println(states[0]);
			System.out.println(req.getParameter("strId"));
			String strId = req.getParameter("strId");
			if(strId.isEmpty()){
				strId="1";
			}
			String condition = req.getParameter("condition");
			String year = req.getParameter("year");
			String month = req.getParameter("month");
			if(year.isEmpty()||month.isEmpty()){
				Calendar cal = Calendar.getInstance();
				year = cal.get(Calendar.YEAR)+"";
				month = (cal.get(Calendar.MONTH)+1)+"";
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
				
				Structure str = new Structure();
				str.setId(Integer.parseInt(strId));
				BookDao bDao = BookDao.getInstance();
				List<Book>bList = null;
				if(!condition.equals("all")){
					bList =bDao.selectAllWithCondition(conn, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(strId), states);
				}else{
					bList = bDao.selectAll(conn);
				}
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
