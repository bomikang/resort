package board.review.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import jdbc.JdbcUtil;

public class Review_DetailDao {
	private static Review_DetailDao instance = new Review_DetailDao();
	
	public static Review_DetailDao getInstance(){
		return instance;
	}
	public void insert(Connection conn, Review_Detail rev_d) throws SQLException{
		PreparedStatement pstmt = null;
		try{
			String sql ="insert into review_detail(rev_no,rev_detail) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rev_d.getRev_no());
			pstmt.setString(2,rev_d.getRev_detail());
			pstmt.executeUpdate();
		}finally{
			JdbcUtil.close(pstmt);
		}
	}
	public Review_Detail selectByNo(Connection conn,int no)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select * from review_detail where rev_no = ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1,no);
			rs = pstmt.executeQuery();
			Review_Detail rev = null;
			if(rs.next()){
				rev = new Review_Detail(rs.getInt("rev_no"), 
								rs.getString("rev_detail"));
			}
			return rev;
			
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			
		}
	}
	public int delete(Connection conn, int no) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "delete from review_detail where rev_no =?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
			
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		
		}
	}
}
