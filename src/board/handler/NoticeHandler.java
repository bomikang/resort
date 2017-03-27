package board.handler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.Notice;
import board.model.NoticeDao;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;

public class NoticeHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			String index = req.getParameter("page");
			String condition = req.getParameter("srcCon");
			String srcText = req.getParameter("key");
			
			req.setAttribute("key", srcText);
			
			System.out.println("index : "+index);
			
			if(index==null){
				index="1";
			}else if(index.equals("")){
				index="1";
			}
			System.out.println("index : "+index);
			
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				NoticeDao nDao = NoticeDao.getInstance();
				int maxIndex = 0;
				if(srcText==null){
					/*공지 리스트 */
					List<Notice> nList = nDao.selectAllNotice(conn, Integer.parseInt(index));
					req.setAttribute("nList", nList);
					maxIndex = nDao.getMaxIndex(conn, null);
				}else{
					if(condition.equals("byTitle")){
						maxIndex = nDao.getMaxIndex(conn, srcText);
						if(Integer.parseInt(index)>maxIndex){
							index = "1";
						}
						List<Notice> nList = nDao.selectNoticeByTitle(conn, srcText, Integer.parseInt(index));
						if(!nList.isEmpty()){
							req.setAttribute("nList", nList);
						}
					}
					
				}
				/*중요공지 리스트*/
				List<Notice> rnList = new ArrayList<>();
				
				System.out.println("rnList : true");
				rnList = nDao.selectRealNotice(conn);
			
				req.setAttribute("rnList", rnList);
				
				IndexOfPage indexObj = new IndexOfPage(maxIndex, Integer.parseInt(index));
				System.out.println("start : "+indexObj.getStart());
				System.out.println("end : "+indexObj.getEnd());
				
				req.setAttribute("index", indexObj);
			}finally {
				JdbcUtil.close(conn);
			}
			
			return "index.jsp?page=/WEB-INF/board/bd_notice_list&menu=/WEB-INF/board/board_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			//조건별 검색위한 준비
			String condition = req.getParameter("srcCon");
			String srcText = req.getParameter("key");
			String index = req.getParameter("page");
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				NoticeDao nDao = NoticeDao.getInstance();
				
				/*중요공지 리스트*/
				List<Notice> rnList = new ArrayList<>();
				
				System.out.println("rnList : true");
				rnList = nDao.selectRealNotice(conn);
			
				req.setAttribute("rnList", rnList);
				
				
				if(condition.equals("byTitle")){
					List<Notice> nList = nDao.selectNoticeByTitle(conn, srcText, Integer.parseInt(index));
					req.setAttribute("nList", nList);
				}
				int maxIndex = nDao.getMaxIndex(conn, srcText);
				
				IndexOfPage indexObj = new IndexOfPage(maxIndex, Integer.parseInt(index));
				System.out.println("start : "+indexObj.getStart());
				System.out.println("end : "+indexObj.getEnd());
				
				req.setAttribute("index", indexObj);
				req.setAttribute("key", srcText);
				return "index.jsp?page=/WEB-INF/board/bd_notice_list&menu=/WEB-INF/board/board_menu";
			}finally {
				JdbcUtil.close(conn);
			}
		}
		return null;
	}

	/* 게시판 인덱스 구성 관련 임시 클래스 */
	public class IndexOfPage{
		int start;
		int end;
		int maxIndex;
		int nowIndex;
		
		public IndexOfPage() {}
		
		public IndexOfPage(int maxIndex, int nowIndex) {		
			this.maxIndex = maxIndex;
			this.nowIndex = nowIndex;
			this.start = getStartIndex(nowIndex);
			this.end = getEndIndex(nowIndex, maxIndex);
			System.out.println("start : "+start);
			System.out.println("end : "+end);
		}
		public int getEndIndex(int now, int max) {
			int endIndex = (int)((now/10)+1)*10;
			if(endIndex >= maxIndex){
				endIndex = maxIndex;
			}
			return endIndex;
		}

		public int getStartIndex(int now) {
			return (int)(now/10)*10+1;
		}

		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
		public int getMaxIndex() {
			return maxIndex;
		}
		public void setMaxIndex(int maxIndex) {
			this.maxIndex = maxIndex;
		}
		public int getNowIndex() {
			return nowIndex;
		}
		public void setNowIndex(int nowIndex) {
			this.nowIndex = nowIndex;
		}
	}
}