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
		String sql = "insert into structure(str_id, str_name, str_people, str_price, str_option, str_image) values(?, ?, ?, ?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, str.getId());
			pstmt.setString(2, str.getName());
			pstmt.setInt(3, str.getPeople());
			pstmt.setInt(4, str.getPrice());
			pstmt.setString(5, str.getOption());
			pstmt.setString(6, str.getImage()); //multiple file
			
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
			pstmt = con.prepareStatement("select * from structure order by str_id asc ,str_no asc");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Structure structure = createStructure(rs);
				
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
	/*select all structure by id*/
	public List<Structure> selectAllStructureById(Connection con, int houseId){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Structure> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement("select * from structure where str_id = ?");
			pstmt.setInt(1, houseId);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Structure structure = createStructure(rs);
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

	/* select all structure by name and people */
	public List<Structure> selectAllStrByIdAndPeople(Connection con, int houseId, int peopleCnt){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Structure> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement("select * from structure where str_id = ? and str_people=? order by str_name asc");
			pstmt.setInt(1, houseId);
			pstmt.setInt(2, peopleCnt);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Structure structure = createStructure(rs);
				
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
	
	/* update structure */
	public void updateStructure(Connection con, Structure str){
		PreparedStatement pstmt = null;
		
		String sql = "update structure set str_id=?, str_name=?, str_people=?, str_price=?, str_option=?, str_image=? where str_no=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, str.getId());
			pstmt.setString(2, str.getName());
			pstmt.setInt(3, str.getPeople());
			pstmt.setInt(4, str.getPrice());
			pstmt.setString(5, str.getOption());
			pstmt.setString(6, str.getImage());
			pstmt.setInt(7, str.getNo());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmt);
		}
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
				structure = createStructure(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return structure;
	}
	
	//method
	private Structure createStructure(ResultSet rs) throws SQLException {
		int no = rs.getInt("str_no");
		int id = rs.getInt("str_id");
		String name = rs.getString("str_name");
		int people = rs.getInt("str_people");
		int price = rs.getInt("str_price");
		String option = rs.getString("str_option");
		String image = rs.getString("str_image");
		
		return new Structure(no, id, name, people, price, option, image);
	}
}
