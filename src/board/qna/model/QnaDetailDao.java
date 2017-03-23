package board.qna.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.JdbcUtil;

public class QnaDetailDao {
	private static final QnaDetailDao instance = new QnaDetailDao();

	public static QnaDetailDao getInstance() {
		return instance;
	}
	
	public void insertQnaDetail(Connection con, Qna qna){
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement("insert into qna_detail values(?, ?)");
			pstmt.setInt(1, qna.getNo());
			pstmt.setString(2, qna.getContent());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmt);
		}
	}//insertQnaDetail
}
