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

import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.model.Member;

public class ReplyDao {
	private static ReplyDao instance = new ReplyDao();
	
	public static ReplyDao getInstance(){
		return instance;
	}
	public List<Reply> rep_list(Connection conn,int no)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select * from reply where rep_review = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();	
			
			if(rs.next()){
				List<Reply> rep = new ArrayList<>();
				 do{
					Reply reply = new Reply(rs.getInt("rep_no"), 	
								rs.getInt("rep_review"),	
							   rs.getInt("rep_mem"),
							   rs.getString("rep_name"),	
							   rs.getString("rep_detail"),
							   rs.getDate("rep_regdate"));
					rep.add(reply); 		
				 }while(rs.next());
				return rep;
				}else{
					return Collections.emptyList();
				}
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);	
		}
	}
	
	public void insert(Connection conn, Reply rep) throws SQLException{
		PreparedStatement pstmt = null;
		try{
			String sql ="insert into reply (rep_no,rep_review,rep_mem,rep_name,rep_detail,rep_regdate)values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rep.getRep_no());
			pstmt.setInt(2,rep.getRep_review());
			pstmt.setInt(3,rep.getRep_mem());
			pstmt.setString(4,rep.getRep_name());
			pstmt.setString(5,rep.getRep_detail());
			pstmt.setTimestamp(6,new Timestamp(rep.getRep_regdate().getTime()));
			pstmt.executeUpdate();
		}finally{
			JdbcUtil.close(pstmt);
		}
	}
	
	
	
	/*public Review selectByNo(Connection conn,int no)throws SQLException{ 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select * from review where rev_no = ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1,no);
			rs = pstmt.executeQuery();
			Review rev = null;
			if(rs.next()){			
				rev = new Review(rs.getInt("rev_no"), 
								rs.getInt("rev_mem"),
						rs.getString("rev_title"),
						rs.getString("rev_name"),
						rs.getDate("rev_regdate"), 
						rs.getInt("rev_readcnt"),
						rs.getInt("rev_replycnt")	
						);
			}
			return rev;
			
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			
		}
	}
	*/
	public int delete(Connection conn, int no) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "delete from reply where rep_no =?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
			
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		
		}
	}
	
	public int selectCount(Connection conn)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql ="select count(*) from review";
			pstmt= conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
			return 0;
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}


