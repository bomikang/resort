package structure_admin.handler;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class StructureListHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
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
		
		System.out.println("왜 안됌");
		return "index.jsp?page=/WEB-INF/structure_admin/str_room_li&menu=/WEB-INF/structure/str_menu";
	}

}
