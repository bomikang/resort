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
			
			try {
				con = ConnectionProvider.getConnection();
				
				int strNo = Integer.parseInt(req.getParameter("strNo"));
				
				StructureDao dao = StructureDao.getInstance();
				dao.deletetStructure(con, strNo);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			
			return "structureList.do";
		}
		return null;
	}

}
