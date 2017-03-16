package structure_admin.handler;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import mvc.controller.CommandHandler;
import structure.model.Structure;
import structure.model.StructureDao;

public class StructureUpdateHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			int no = Integer.parseInt(req.getParameter("no"));
			
			Connection con = null;
			
			try {
				con = ConnectionProvider.getConnection();
				
				StructureDao dao = StructureDao.getInstance();
				Structure structure = dao.getStructureByNo(con, no);
				
				req.setAttribute("structure", structure);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				JdbcUtil.close(con);
			}
			return "index.jsp?page=/WEB-INF/structure_admin/str_room_de&menu=/WEB-INF/structure/str_menu";
		} else {
			postProcess(req, res);
			return "structureList.do";
		}
	}

	private void postProcess(HttpServletRequest req, HttpServletResponse res) {
		String uploadPath = req.getRealPath("Structure_Images"); //파일 들어갈 폴더 명(프로젝트 안 upload폴더) => 서버에 저장
		
		File dir = new File(uploadPath);
		if(dir.exists() == false){
			dir.mkdirs();
		}
		
		Connection con = null;
		
		//upload시작
		try{
			con = ConnectionProvider.getConnection();
			
			int size = 1024 * 1024 * 10; //10MB
			
			//MultipartRequest multi = new MultipartRequest(req, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
			/*new DefaultFileRenamePolicy() => 원래 이름이 bongi1 라면, 존재하는 파일이 있을 때 이름이 변경되어 bongi2 로 들어감*/ 
			
			MultipartRequest multi = new MultipartRequest(req, uploadPath, size, "utf-8"); //덮어쓰기
			
			Enumeration files = multi.getFileNames(); //실제 upload된 파일 정보(req.getParameter("file1")
			ArrayList<String> arrFile = new ArrayList<>();
			
			while (files.hasMoreElements()) {
				String keyFile = (String) files.nextElement();
				String fileName = multi.getFilesystemName(keyFile);
				String originFileName = multi.getOriginalFileName(keyFile);
				
				if (fileName != null && !(fileName.isEmpty())) {
					arrFile.add(fileName);
				}
			}
			System.out.println(uploadPath);
			
			/* =====DB 저장 시작===== */
			int no = Integer.parseInt(multi.getParameter("strNo"));
			int id = Integer.parseInt(multi.getParameter("strId")); //시설 구분
			String name = multi.getParameter("name"); //호수 또는 방이름
			int people = Integer.parseInt(multi.getParameter("people"));
			int price = Integer.parseInt(multi.getParameter("price"));
			String option = multi.getParameter("option");
			String setDbImage = multi.getParameter("setDbImage");
			
			Structure str = new Structure(no, id, name, people, price, option, setDbImage);
			
			StructureDao dao = StructureDao.getInstance();
			dao.updateStructure(con, str);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			JdbcUtil.close(con);			
		}
	}

}
