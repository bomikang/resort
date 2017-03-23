package book.admin.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

public class BookListHandler implements CommandHandler {
	public static Comparator<Integer> reverse = new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return -(o1-o2);
		}
	};
	
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
				/*년도, 월을 선택하는 comboBox setting*/
				Set<Integer> yearList = bDao.selectYearOfBook(conn);
				Set<Integer> monthList = bDao.selectMonthOfBook(conn);
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(System.currentTimeMillis()));
				if(yearList.isEmpty()){
					yearList.add(cal.get(Calendar.YEAR));
				}
				if(monthList.isEmpty()){
					monthList.add(cal.get(Calendar.MONTH)+1);
				}
				
				List<Integer> yList = new ArrayList<>();
				Iterator<Integer> yIter = yearList.iterator();
				
				while(yIter.hasNext()){
					yList.add(yIter.next());
				}
				List<Integer> mList = new ArrayList<>();
				Iterator<Integer> mIter = monthList.iterator();
				
				while(mIter.hasNext()){
					mList.add(mIter.next());
				}
				yList.sort(reverse);
				mList.sort(null);
				
				bList = bDao.selectAll(conn);
				req.setAttribute("bList", bList);
				req.setAttribute("years", yList);
				req.setAttribute("months", mList);
			}finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/book_admin/bk_list&menu=/WEB-INF/book/bk_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			/* bk_list.jsp 내에서 조건 선택하여 조회버튼 눌렀을때 실행 */
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
			System.out.println("year : "+year);
			String month = req.getParameter("month");
			System.out.println("month : "+month);
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
				Set<Integer> yearList = bDao.selectYearOfBook(conn);
				Set<Integer> monthList = bDao.selectMonthOfBook(conn);
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(System.currentTimeMillis()));
				if(yearList.isEmpty()){
					yearList.add(cal.get(Calendar.YEAR));
				}
				if(monthList.isEmpty()){
					monthList.add(cal.get(Calendar.MONTH));
				}
				
				List<Integer> yList = new ArrayList<>();
				Iterator<Integer> yIter = yearList.iterator();
				
				while(yIter.hasNext()){
					yList.add(yIter.next());
				}
				List<Integer> mList = new ArrayList<>();
				Iterator<Integer> mIter = monthList.iterator();
				
				while(mIter.hasNext()){
					mList.add(mIter.next());
				}
				yList.sort(reverse);
				mList.sort(null);
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
