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
				
				/*공지 리스트 */
				List<Notice> nList = nDao.selectAllNotice(conn, Integer.parseInt(index));
				req.setAttribute("nList", nList);
				
				/*중요공지 리스트*/
				List<Notice> rnList = new ArrayList<>();
				
				System.out.println("rnList : true");
				rnList = nDao.selectRealNotice(conn);
			
				req.setAttribute("rnList", rnList);
				
				int maxIndex = nDao.getMaxIndex(conn);
				
				IndexOfPage indexObj = new IndexOfPage(maxIndex, Integer.parseInt(index));
				System.out.println("start : "+indexObj.getStart());
				System.out.println("end : "+indexObj.getEnd());
				
				req.setAttribute("index", indexObj);
			}finally {
				JdbcUtil.close(conn);
			}
			
			return "index.jsp?page=/WEB-INF/board/bd_notice_list&menu=/WEB-INF/board/board_menu";
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