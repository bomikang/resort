package board.qna.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;
import member.model.Member;

public class QnaDao {
	private static final QnaDao instance = new QnaDao();

	public static QnaDao getInstance() {
		return instance;
	}
	
	public void insertQna(Connection con, Qna qna){
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement("insert into qna(mem_no, qna_title, qna_regdate, qna_article) values(?, ?, ?, ?)");
			pstmt.setInt(1, qna.getMember().getNo());
			pstmt.setString(2, qna.getTitle());
			pstmt.setTimestamp(3, new Timestamp(qna.getRegDate().getTime()));
			pstmt.setInt(4, qna.getArticle());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmt);
		}
	}//insertQna
}
