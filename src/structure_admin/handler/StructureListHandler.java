package structure_admin.handler;

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

public class StructureListHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection con = null;
			
			try {
				con = ConnectionProvider.getConnection();
				
				StructureDao dao = StructureDao.getInstance();
				List<Structure> list = dao.selectAllStructure(con);
				
				req.setAttribute("strList", list);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			return "index.jsp?page=/WEB-INF/structure_admin/str_room_li&menu=/WEB-INF/structure/str_menu";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			Connection con = null;
			
			try {
				con = ConnectionProvider.getConnection();
				
				StructureDao dao = StructureDao.getInstance();
				List<Structure> list = dao.selectAllStructure(con);
				
				//json
				ObjectMapper om = new ObjectMapper();
				String json = om.writeValueAsString(list);
				
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
