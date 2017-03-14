package structure.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.JdbcUtil;

public class StructureDao {
	private static final StructureDao instance = new StructureDao();
	
	private StructureDao() {}

	public static StructureDao getInstance() {
		return instance;
	}
	
	//insert
	public void insertStructure(Connection con, Structure str){
		PreparedStatement pstmt = null;
		
		//집이름, 수용인원, 가격, 옵션, 사진경로
		String sql = "insert into structure(str_name, str_people, str_price, str_option, str_image) values(?, ?, ?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, str.getName());
			pstmt.setInt(2, str.getPeople());
			pstmt.setInt(3, str.getPrice());
			pstmt.setString(4, str.getOption());
			pstmt.setString(5, str.getImage()); //multiple file
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmt);
		}
	}
}
