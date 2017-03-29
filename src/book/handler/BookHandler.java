package book.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			String strId = req.getParameter("id");
			if(strId==null){
				strId="1";
			}else if(strId.isEmpty()){
				strId="1";
			}
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				StructureDao sDao = StructureDao.getInstance();
				List<Integer> sList = sDao.selectDistinctId(conn);
				List<Structure> strList = new ArrayList<>();
				if(Integer.parseInt(strId)>sList.get(sList.size()-1)){
					strId=sList.get(sList.size()-1)+"";
				}
				for(Integer s:sList){
					Structure str = new Structure();
					str.setId(s);
					strList.add(str);
				}
				req.setAttribute("id", strId);
				req.setAttribute("strId", strList);
			}finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/book/bk_now&menu=/WEB-INF/book/bk_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			/* 화면 구성용 ajax시 호출 */
			String strId = req.getParameter("strId");
			String dateText = req.getParameter("date");
			Date date = new Date();
			
			if(strId==null){
				strId="1";
			}
			int houseId = Integer.parseInt(strId);
			
			if(dateText!=null){
				long time = Long.parseLong(req.getParameter("date"));
				date.setTime(time);
			}else if(dateText == null){
				date.setTime(System.currentTimeMillis());
			}
			
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				BookDao bDao = BookDao.getInstance();
				StructureDao sDao = StructureDao.getInstance();				
				List<Structure> sList = sDao.selectAllStructureById(conn, houseId);
				Map<String, List<ResultOfBook>> resultMap = new HashMap<>();
				
				for(int i=0;i<sList.size();i++){
					List<ResultOfBook> resList = new ArrayList<>();
					List<Book>bList = bDao.selectThisMonthByStr(conn, date, sList.get(i).getNo(), sList.get(i).getId());
					if(!bList.isEmpty()){
						for(Book b:bList){
							Calendar startCal = Calendar.getInstance();
							Calendar endCal = Calendar.getInstance();
							Calendar cmpCal = Calendar.getInstance();//넘어온 parameter값으로 만든 date객체(검색할 년, 월의 정보 담고있음)
							
							startCal.setTime(b.getStartDate());
							endCal.setTime(b.getEndDate());
							cmpCal.setTime(date);
							
							if(startCal.get(Calendar.YEAR)==endCal.get(Calendar.YEAR)){
								//1. 입실날짜와 퇴실날짜의 년도가 같은 경우(12월 말의 예약 대비)
								if(startCal.get(Calendar.MONTH)==endCal.get(Calendar.MONTH)){
									//1-1.입실날짜와 퇴실날짜의 월이 같은 경우(각 월말의 예약 대비)
									for(int k=startCal.get(Calendar.DAY_OF_MONTH); k < endCal.get(Calendar.DAY_OF_MONTH); k++){
										resList.add(new ResultOfBook(k, b.getState()));
									}
								}else{
									//1-2.입실날짜와 퇴실날짜의 월이 다른 경우(각 월말의 예약 대비)
									if(cmpCal.get(Calendar.MONTH)==startCal.get(Calendar.MONTH)){
										//1-2-1. 입실날짜와 퇴실날짜가 다르고 입실날짜가 검색하려는 월과 일치하는 경우(월말예약)
										for(int k=startCal.get(Calendar.DAY_OF_MONTH); k <= startCal.getMaximum(Calendar.DAY_OF_MONTH); k++){
											System.out.println("1-2 : "+k);
											resList.add(new ResultOfBook(k, b.getState()));
										}
									}else if(cmpCal.get(Calendar.MONTH)==endCal.get(Calendar.MONTH)){
										//1-2-2. 입실날짜와 퇴실날짜가 다르고 퇴실날짜가 검색하려는 월과 일치하는 경우(월말예약)
										for(int k=1; k < endCal.get(Calendar.DAY_OF_MONTH); k++){
											System.out.println("1-3 : "+k);
											resList.add(new ResultOfBook(k, b.getState()));
										}
									}
								}
							}else if(startCal.get(Calendar.YEAR)==cmpCal.get(Calendar.YEAR)){
								//2. 입실날짜와 퇴실날짜의 년도가 다르고 검색하려는 월과 입실혀는 월이 일치하는 경우(12월 말 -1월초 예약)
								for(int k=startCal.get(Calendar.DAY_OF_MONTH); k <= startCal.getMaximum(Calendar.DAY_OF_MONTH); k++){
									System.out.println("2 : "+k);
									resList.add(new ResultOfBook(k, b.getState()));
								}
							}else if(startCal.get(Calendar.YEAR)==cmpCal.get(Calendar.YEAR)){
								//3. 입실날짜와 퇴실날짜의 년도가 다르고 검색하려는 월과 퇴실하는 월이 일치하는 경우(12월 말 -1월초 예약)
								for(int k=1; k < endCal.get(Calendar.DAY_OF_MONTH); k++){
									System.out.println("3 : "+k);
									resList.add(new ResultOfBook(k, b.getState()));
								}
							}
						}
						resultMap.put(sList.get(i).getName(), resList);
					}
				}
				
				//data�� json ���� ����
				ObjectMapper om= new ObjectMapper();
				String json = "["+om.writeValueAsString(sList)+","+ om.writeValueAsString(resultMap)+"]";
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
	
	
	class ResultOfBook{
		/*임시 Class*/
		private int date;
		private String state;
		
		public int getDate() {
			return date;
		}
		public void setDate(int date) {
			this.date = date;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public ResultOfBook(int date, String state) {
			this.date = date;
			this.state = state;
		}
		public ResultOfBook() {}		
	}
}

