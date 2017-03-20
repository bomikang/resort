package book.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class BookHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")){
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				StructureDao sDao = StructureDao.getInstance();				
				List<Integer> idList = sDao.selectDistinctId(conn);
				List<Structure> strList = new ArrayList<>();
				
				for(int i=0;i<idList.size();i++){					
					Structure str = new Structure();
					str.setId(idList.get(i));
					strList.add(str);
				}
				req.setAttribute("strId", strList);
				
			}finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/book/bk_now&menu=/WEB-INF/book/bk_menu";
		
		}else if(req.getMethod().equalsIgnoreCase("post")){
			String dateText = req.getParameter("date");
			int strId = Integer.parseInt(req.getParameter("strId"));
			System.out.println("strId : " + strId);
			Date date = new Date();
			if(dateText!=null){
				long time = Long.parseLong(req.getParameter("date"));
				date.setTime(time);
			}
			
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				BookDao bDao = BookDao.getInstance();
				StructureDao sDao = StructureDao.getInstance();				
				List<Structure> sList = sDao.selectAllStructureById(conn, strId);
				List<List<Book>> bookList = new ArrayList<>();
				for(int i=0;i<sList.size();i++){
					List<Book>bList = bDao.selectThisMonthByStr(conn, date, sList.get(i).getNo(), strId);
					if(!bList.isEmpty()){
						bookList.add(bList);
					}
				}
				
				req.setAttribute("sList", sList);
				req.setAttribute("bList", bookList);
				
				//data�� json ���� ����
				ObjectMapper om= new ObjectMapper();
				String json = "["+om.writeValueAsString(sList)+","+ om.writeValueAsString(bookList)+"]";
				// json �߽�
				res.setContentType("application/json;charset=utf-8");
				PrintWriter pw = res.getWriter();
				pw.print(json);
				pw.flush();
				
			}finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

}
