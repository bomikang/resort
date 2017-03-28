package board.handler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.Notice;
import board.model.NoticeDao;
import jdbc.ConnectionProvider;
import jdbc.IndexOfPage;
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
}