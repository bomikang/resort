package board.review.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jdbc.JdbcUtil;

public class ReviewDao {
	private static ReviewDao instance = new ReviewDao();
	
	public static ReviewDao getInstance(){
		return instance;
	}
	public List<Review> listAll(Connection conn)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			HttpServletRequest req = null;
			
			pstmt = conn.prepareStatement(
					"select * from review"
					);
			rs = pstmt.executeQuery();
			if(rs.next()){
				List<Review> review_list = new ArrayList<>();
				do{
				Review review = new Review(rs.getInt("rev_no"), 	// 게시물번호
											rs.getInt(null),	// 회원번호
										   rs.getString("rev_title"),
										   rs.getString(null),	// 작성자
										   rs.getDate("rev_regdate"),
										   rs.getInt("rev_readcnt"));
					review_list.add(review);
				
				}while(rs.next());
				return review_list;
			}else{
				return Collections.emptyList();
			}	
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			
		}
	}
	public void insert(Connection conn, Review rev) throws SQLException{
		PreparedStatement pstmt = null;
		try{
			String sql ="insert into review(rev_no,rev_mem,rev_title,rev_name,rev_regdate,rev_readcnt)" 
						+"values(?,?,?,?,?,?)";

			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rev.getRev_no());
			pstmt.setInt(2,rev.getRev_mem());
			pstmt.setString(3,rev.getRev_title());
			pstmt.setString(4,rev.getRev_name());
			pstmt.setTimestamp(5,new Timestamp(rev.getRev_regdate().getTime()));
			pstmt.setInt(6,rev.getRev_readcnt());
			pstmt.executeUpdate();
		}finally{
			JdbcUtil.close(pstmt);
		}
	}
}
