package structure_admin.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;
import structure.model.StructureDao;

public class StructureDeleteHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("post")) {

			Connection con = null;
			
			int strNo = Integer.parseInt(req.getParameter("strNo"));
			
			try {
				con = ConnectionProvider.getConnection();
				
				StructureDao dao = StructureDao.getInstance();
				dao.deletetStructure(con, strNo);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
		}
		return "index.jsp?page=/WEB-INF/structure_admin/str_room_li&menu=/WEB-INF/structure/str_menu";
	}

}
