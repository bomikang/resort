package structure.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;

public class StructureDao {
	private static final StructureDao instance = new StructureDao();
	
	private StructureDao() {}

	public static StructureDao getInstance() {
		return instance;
	}
	
	/* insert structure*/
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
	
	/* select all structure */
	public List<Structure> selectAllStructure(Connection con){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Structure> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement("select * from structure");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt("str_no");
				String name = rs.getString("str_name");
				int people = rs.getInt("str_people");
				int price = rs.getInt("str_price");
				String option = rs.getString("str_option");
				String image = rs.getString("str_image");
				
				Structure structure = new Structure(no, name, people, price, option, image); 
				
				list.add(structure);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return list;
	}
	
	/* get structure by no */
	public Structure getStructureByNo(Connection con, int strNo){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Structure structure = null;
		
		try {
			pstmt = con.prepareStatement("select * from structure where str_no = ?");
			pstmt.setInt(1, strNo);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int no = rs.getInt("str_no");
				String name = rs.getString("str_name");
				int people = rs.getInt("str_people");
				int price = rs.getInt("str_price");
				String option = rs.getString("str_option");
				String image = rs.getString("str_image");
				
				structure = new Structure(no, name, people, price, option, image);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return structure;
	}
}
