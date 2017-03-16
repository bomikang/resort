package structure.handler;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class StructureHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection con = null;
			
			try {
				con = ConnectionProvider.getConnection();
				
				//수용인원
				int peopleCnt = 0;
				if (req.getParameter("people") != null) {
					peopleCnt = Integer.parseInt(req.getParameter("people"));
				}
				
				StructureDao dao = StructureDao.getInstance();
				List<Structure> list = dao.selectAllStrByNameAndPeople(con, peopleCnt); //수용인원만큼 가져옴
				
				req.setAttribute("strList", list);
				req.setAttribute("firstRoomNo", list.get(0).getNo());
				
				System.out.println(req.getRealPath("Structure_Images")); //absolute path
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			
			return "index.jsp?page=/WEB-INF/structure/str_room&menu=/WEB-INF/structure/str_menu";
		}else{
			Connection con = null;
			
			int strNo = Integer.parseInt(req.getParameter("no"));
			
			try {
				con = ConnectionProvider.getConnection();
				
				StructureDao dao = StructureDao.getInstance();
				Structure structure = dao.getStructureByNo(con, strNo);
				
				//밑 두 줄만 있으면 json형태로 바꿔줌
				ObjectMapper om = new ObjectMapper();
				String json = om.writeValueAsString(structure); //Map과 ArrayList도 json형태로 바꿀 수 있음
				
				res.setContentType("application/json;charset=utf-8");
				
				PrintWriter pw = res.getWriter();
				pw.print(json);
				pw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
		}
		
		return null;
	}

}
