package introduce.handler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class IntroduceHandler implements CommandHandler{
	private static final String VIEW_FORDER = "/WEB-INF/introduce/";

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			Connection conn = null;
			List<Structure> sList = new ArrayList<>();
			try{
				conn = ConnectionProvider.getConnection();
				StructureDao sDao = StructureDao.getInstance();
				List<Integer> idList = sDao.selectDistinctId(conn);
				for(Integer id:idList){
					List<Structure> strList = sDao.selectAllStructureById(conn, id);
					sList.add(new Structure(0, id, null, strList.size(), 0, null, null));
					System.out.println(id+" : "+strList.size());
				}
				
				req.setAttribute("sList", sList);
			}finally {
				JdbcUtil.close(conn);
			}
			
			return "index.jsp?page=/WEB-INF/introduce/intro_main&menu=/WEB-INF/introduce/intro_menu";
		}
		return null;
	}

}
