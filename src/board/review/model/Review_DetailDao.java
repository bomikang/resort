package board.review.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import jdbc.JdbcUtil;

public class Review_DetailDao {
	private static Review_DetailDao instance = new Review_DetailDao();
	
	public static Review_DetailDao getInstance(){
		return instance;
	}
	public void insert(Connection conn, Review_Deatail rev_d) throws SQLException{
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
	
}
