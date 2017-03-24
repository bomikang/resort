package book.handler;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
					yList.sort(BookListHandler.reverse);
					mList.sort(null);
					
					Member mem = new Member(myinfo.getMy_id());
					mem.setNo(myinfo.getMy_no());
					bList = bDao.selectByMember(conn, mem);
					
					req.setAttribute("bList", bList);
					req.setAttribute("years", yList);
					req.setAttribute("months", mList);
				}finally {
					JdbcUtil.close(conn);
				}
				return "index.jsp?page=/WEB-INF/book/bk_check&menu=/WEB-INF/book/bk_menu";	
			}
		}else if(req.getMethod().equalsIgnoreCase("post")){
			/*예약상태 조건*/
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
				yList.sort(BookListHandler.reverse);
				mList.sort(null);
				
				List<Book>bList = null;
				System.out.println("condition : "+condition);
				if(!condition.equalsIgnoreCase("all")){
					bList =bDao.selectByMemberWithCon(conn, myinfo.getMy_no(), Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(strId), states);
				}else{
					Member mem = new Member(myinfo.getMy_id());
					mem.setNo(myinfo.getMy_no());
					
					bList = bDao.selectByMember(conn, mem);
				}
				req.setAttribute("bList", bList);
				
				//data�� json ���� ����
				ObjectMapper om= new ObjectMapper();
				// json �߽�
				String json = "";				
				if(!condition.equals("all")){
					if(Integer.parseInt(strId)==0){
						json = "["+om.writeValueAsString("전체 시설")+","+om.writeValueAsString(bList)+"]";
					}else{
						json = "["+om.writeValueAsString(str.getNameById())+","+om.writeValueAsString(bList)+"]";
					}
				}else{//전체 검색일 경우
					json = "["+om.writeValueAsString("전체 보기")+","+om.writeValueAsString(bList)+"]";
				}				
				
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
