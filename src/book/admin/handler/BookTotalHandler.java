package book.admin.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import book.admin.handler.BookListHandler;
import book.model.Book;
import book.model.BookDao;
import jdbc.ConnectionProvider;
import jdbc.IndexOfPage;
import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class BookTotalHandler implements CommandHandler {
	String[] stateList = {"입금대기","입금완료","예약취소","예약종료"};
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		/*LoginMemberInfo myinfo = (LoginMemberInfo) req.getSession(false).getAttribute("user_info");
		if(myinfo==null){
			return "login.do";
		}*/
		if(req.getMethod().equalsIgnoreCase("get")){	
			/*if(myinfo != null){*/
				Connection conn = null;
				List<Book> bList = null;
				try{
					conn = ConnectionProvider.getConnection();
									
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
					yList.sort(BookListHandler.reverse);
					mList.sort(null);
					
					StructureDao sDao = StructureDao.getInstance();
					List<Structure> strList = new ArrayList<>();
					List<Integer> idList = sDao.selectDistinctId(conn);
					for(int i=0;i<idList.size();i++){					
						Structure str = new Structure();
						str.setId(idList.get(i));
						strList.add(str);
					}
					
					req.setAttribute("strId", strList);					
					req.setAttribute("years", yList);
					req.setAttribute("months", mList);
				}finally {
					JdbcUtil.close(conn);
				}
				return "index.jsp?page=/WEB-INF/book_admin/bk_total&menu=/WEB-INF/book/bk_menu";	
			/*}*/
		}else if(req.getMethod().equalsIgnoreCase("post")){
			/*예약상태 조건*/
			
			String condition = "withCon";
			String strId = req.getParameter("strId");
			if(strId==null){
				strId="1";
			}else if(strId.isEmpty()){
				strId="1";
			}
			String year = req.getParameter("year");
			System.out.println("year : "+year);
			String month = req.getParameter("month");
			System.out.println("month : "+month);
			
			if(year.isEmpty()||month.isEmpty()){
				Calendar cal = Calendar.getInstance();
				year = cal.get(Calendar.YEAR)+"";
				month = (cal.get(Calendar.MONTH))+"";
			}
			
			String start = getDateForm(year, month, "start");
			System.out.println("BookTotal start : "+start);
			String end = getDateForm(year, month, "end");
			System.out.println("BookTotal end : "+end);
			int index = -1;
			Connection conn = null;
			try{
				conn= ConnectionProvider.getConnection();
				
				StructureDao sDao = StructureDao.getInstance();
				BookDao bDao = BookDao.getInstance();
				
				List<Integer> idList = sDao.selectDistinctId(conn);
				HashMap<String, List<List<TotalBook>>> totalMap = new HashMap<>();//시설 아이디별
					
				List<List<TotalBook>> strMap = new ArrayList<>();//시설별
					
				List<Structure> strList = sDao.selectAllStructureById(conn, Integer.parseInt(strId));
				for(Structure str:strList){
					List<TotalBook> tbList = new ArrayList<>();
					int allCnt = 0;
					int allPrice = 0;
					for(String s : stateList){
						String[] state = {s};
						List<Book> bList = bDao.selectAllWithCondition(conn, start, end, Integer.parseInt(strId), str.getNo(), null, state, index, condition);
						int totalCnt = bList.size();
						int totalPrice = 0;
						for(Book b:bList){
							totalPrice += b.getPrice();
						}
						TotalBook tBook = new TotalBook(str.getName(),s, totalCnt, totalPrice);
						tbList.add(tBook);
						allCnt += totalCnt;
						allPrice += totalPrice;
					}
					TotalBook tBook = new TotalBook(str.getName(),"total", allCnt, allPrice);
					tbList.add(tBook);
					strMap.add(tbList);
				}
				Structure temp = new Structure();
				temp.setId(Integer.parseInt(strId));
				
				totalMap.put(temp.getNameById(), strMap);					

				//data�� json ���� ����
				ObjectMapper om= new ObjectMapper();
				// json �߽�
				String json = "["+om.writeValueAsString(year)+","+om.writeValueAsString(month)+","+om.writeValueAsString(totalMap)+","+om.writeValueAsString(temp.getNameById())+"]";			
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

	private String getDateForm(String year, String month, String string) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, (Integer.parseInt(month)-1));
		if(string.equals("start")){
			cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
		}else{
			cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
		}
		Date date = cal.getTime();
		return Book.dateFormat.format(date);
	}
	
	/* 시설별 월별 예약 현황 조회 위한 임시 클래스*/
	public class TotalBook{
		private String name;
		private String state;
		private int totalBook;
		private int totalPrice;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public int getTotalBook() {
			return totalBook;
		}
		public void setTotalBook(int totalBook) {
			this.totalBook = totalBook;
		}
		public int getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(int totalPrice) {
			this.totalPrice = totalPrice;
		}
		public TotalBook() {}
		
		public TotalBook(String name, String state, int totalBook, int totalPrice) {
			this.name = name;
			this.state = state;
			this.totalBook = totalBook;
			this.totalPrice = totalPrice;
		}
		public String getTotalPriceForm(){
			return String.format("%,d 원", this.totalPrice);
		}
		
	}
}
