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
import jdbc.IndexOfPage;
import jdbc.JdbcUtil;
import member.model.Member;
import member.model.MemberDao;
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
			try{
				conn = ConnectionProvider.getConnection();
				/* 시설 구분 리스트 */
				StructureDao sDao = StructureDao.getInstance();
				List<Structure> strList = new ArrayList<>();
				List<Integer> idList = sDao.selectDistinctId(conn);
				for(int i=0;i<idList.size();i++){					
					Structure str = new Structure();
					str.setId(idList.get(i));
					strList.add(str);
				}
				req.setAttribute("strId", strList);
			}finally {
				JdbcUtil.close(conn);
			}
			return "index.jsp?page=/WEB-INF/book_admin/bk_list&menu=/WEB-INF/book/bk_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			/* bk_list.jsp 내에서 조건 선택하여 조회버튼 눌렀을때 실행 */
			if(req.getParameter("type").equalsIgnoreCase("setTable")){
				
				String state = req.getParameter("cdState");
				String[] states = state.split(",");
				System.out.println(states[0]);
				System.out.println(req.getParameter("strId"));
				
				String strId = req.getParameter("strId");
				if(strId.isEmpty()){
					strId="0";
				}
				
				String sNo = req.getParameter("strNo");
				if(sNo.isEmpty()){
					sNo="0";
				}
				String index = req.getParameter("index");
				if(index==null){
					index="1";
				}else if(index.equals("")){
					index="1";
				}
				String condition = req.getParameter("condition");
				String start = req.getParameter("start");
				System.out.println("start : "+start);
				String end = req.getParameter("end");
				System.out.println("end : "+end);
				if(start.isEmpty()||end.isEmpty()){
					Date cal = new Date();
					start = Book.dateFormat.format(cal);
					end = Book.dateFormat.format(cal);
				}
				String memName = req.getParameter("memName");
				System.out.println("memNamae : "+memName);
				if(memName==""){
					memName=null;
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
					int maxIndex = 1;
					if(!condition.equals("all")){
						bList =bDao.selectAllWithCondition(conn, start, end, Integer.parseInt(strId), Integer.parseInt(sNo),memName,states, Integer.parseInt(index));
						maxIndex = bDao.getMaxIndex(conn, start, end, Integer.parseInt(strId), Integer.parseInt(sNo), memName, states, condition);
					}else{
						bList = bDao.selectAll(conn, memName, Integer.parseInt(index));
						maxIndex = bDao.getMaxIndex(conn, start, end, Integer.parseInt(strId), Integer.parseInt(sNo), memName, states, condition);
					}
					req.setAttribute("bList", bList);
					IndexOfPage indexs = new IndexOfPage(maxIndex, Integer.parseInt(index));
					//data�� json ���� ����
					ObjectMapper om= new ObjectMapper();
					String json = "["+om.writeValueAsString(str.getNameById())+","+om.writeValueAsString(bList)+","+om.writeValueAsString(indexs)+"]";
					// json �߽�
					res.setContentType("application/json;charset=utf-8");
					PrintWriter pw = res.getWriter();
					pw.print(json);
					pw.flush();
				}finally {
					JdbcUtil.close(conn);
				}				
				return null;
				
			}else if(req.getParameter("type").equalsIgnoreCase("strList")){
				/* 시설 구분 선택 시 세부 시설명 Setting하기 위해 사용 */
				String strId = req.getParameter("strId");
				
				if(strId==null){
					strId="0";
				}
				if(strId != "0"){
					Connection conn = null;
					try{
						conn = ConnectionProvider.getConnection();
						StructureDao sDao = StructureDao.getInstance();
						List<Structure> strList = sDao.selectAllStructureById(conn, Integer.parseInt(strId));
						
						//data�� json ���� ����
						ObjectMapper om= new ObjectMapper();
						String json = "["+om.writeValueAsString(strList)+"]";
						// json �߽�
						res.setContentType("application/json;charset=utf-8");
						PrintWriter pw = res.getWriter();
						pw.print(json);
						pw.flush();
					}finally {
						JdbcUtil.close(conn);
					}
				}
			}else if(req.getParameter("type").equalsIgnoreCase("mem")){
				/* member 이름 클릭 시 그 회원의 전체 내역을 조회하여 보여주기 위해 */
				Connection conn = null;
				try{
					conn = ConnectionProvider.getConnection();
					String bkMem = req.getParameter("bkMem");
					if(bkMem != null){
						MemberDao mDao = MemberDao.getInstance();
						Member mem = mDao.selectByNo(conn, Integer.parseInt(bkMem));
						if(mem != null){//Member Table 내 물리 삭제 대비
							BookDao bDao = BookDao.getInstance();							
							List<Book> bList = bDao.selectByMember(conn, mem);
							
							//data�� json ���� ����
							ObjectMapper om= new ObjectMapper();
							String json = "["+om.writeValueAsString(mem.getName())+","+om.writeValueAsString(bList)+"]";
							// json �߽�
							res.setContentType("application/json;charset=utf-8");
							PrintWriter pw = res.getWriter();
							pw.print(json);
							pw.flush();
						}
						
					}
					
				}finally {
					JdbcUtil.close(conn);
				}
			}
		}
		return null;
	}
}
