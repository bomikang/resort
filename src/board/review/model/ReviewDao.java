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
											rs.getInt("rev_mem"),	// 회원번호
										   rs.getString("rev_title"),
										   rs.getString("rev_name"),	// 작성자
										   rs.getDate("rev_regdate"),
										   rs.getInt("rev_readcnt"),
										   rs.getInt("rev_replycnt"));
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
	// 조회수 쿼리 .. 게시판  디테일 핸들러 만들때 사용 
	public int rev_ReadCnt(Connection conn,int no) throws SQLException{
		PreparedStatement pstmt = null;	
		try {
			String sql = "update review set rev_readcnt = rev_readcnt+1 where rev_no= ? "; 

			pstmt= conn.prepareStatement(sql);
			
			pstmt.setInt(1,no);
			return pstmt.executeUpdate();			
		} finally {
			
			JdbcUtil.close(pstmt);
		
		}
	}
	
	public Review selectByNo(Connection conn,int no)throws SQLException{ // 게시물 번호 찾기
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
	
	public int delete(Connection conn, int no) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "delete from review where rev_no =?";
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
	
	
	public List<Review> selectOrderBy(Connection conn, int startRow,int size)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(
					"select * from review order by rev_no desc limit ?,?"
					);
			pstmt.setInt(1,startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			List<Review> result = new ArrayList<>();
			while(rs.next()){
				Review review = new Review(rs.getInt("rev_no"), 	// 게시물번호
						rs.getInt("rev_mem"),	// 회원번호
					   rs.getString("rev_title"),
					   rs.getString("rev_name"),	// 작성자
					   rs.getDate("rev_regdate"),
					   rs.getInt("rev_readcnt"),
					   rs.getInt("rev_replycnt"));
						result.add(review);	
			}
			return result;	
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			
		}
	}
	public List<Review> searchTitleOrderBy(Connection conn,String title, int startRow,int size)throws SQLException{ // 검색 제목 정렬
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(
"select rev_no,rev_mem,rev_title,rev_name,rev_regdate,rev_readcnt,rev_replycnt from review where rev_title like ? order by rev_no desc limit ?,?");
			pstmt.setString(1, title);
			pstmt.setInt(2,startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();
			List<Review> result = new ArrayList<>();
			while(rs.next()){
				Review review = new Review(rs.getInt("rev_no"), 	// 게시물번호
						rs.getInt("rev_mem"),	// 회원번호
					   rs.getString("rev_title"),
					   rs.getString("rev_name"),	// 작성자
					   rs.getDate("rev_regdate"),
					   rs.getInt("rev_readcnt"),
					   rs.getInt("rev_replycnt"));
						result.add(review);	
			}
			return result;	
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);	
		}
	}
	public List<Review> searchNameOrderBy(Connection conn,String name, int startRow,int size)throws SQLException{ // 검색 이름 정렬
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(
"select rev_no,rev_mem,rev_title,rev_name,rev_regdate,rev_readcnt,rev_replycnt from review where rev_name like ? order by rev_no desc limit ?,?");
			pstmt.setString(1, name);
			pstmt.setInt(2,startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();
			List<Review> result = new ArrayList<>();
			while(rs.next()){
				Review review = new Review(rs.getInt("rev_no"), 	// 게시물번호
						rs.getInt("rev_mem"),	// 회원번호
					   rs.getString("rev_title"),
					   rs.getString("rev_name"),	// 작성자
					   rs.getDate("rev_regdate"),
					   rs.getInt("rev_readcnt"),
					   rs.getInt("rev_replycnt"));
						result.add(review);	
			}
			return result;	
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);	
		}
	}
	
	public List<Review> searchNumberOrderBy(Connection conn,int no, int startRow,int size)throws SQLException{ // 검색 제목 정렬
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(
"select rev_no,rev_mem,rev_title,rev_name,rev_regdate,rev_readcnt,rev_replycnt from review where rev_no like ? order by rev_no desc limit ?,?");
			pstmt.setInt(1, no);
			pstmt.setInt(2,startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();
			List<Review> result = new ArrayList<>();
			while(rs.next()){
				Review review = new Review(rs.getInt("rev_no"), 	// 게시물번호
						rs.getInt("rev_mem"),	// 회원번호
					   rs.getString("rev_title"),
					   rs.getString("rev_name"),	// 작성자
					   rs.getDate("rev_regdate"),
					   rs.getInt("rev_readcnt"),
					   rs.getInt("rev_replycnt"));
						result.add(review);	
			}
			return result;	
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);	
		}
	}
	
	public int searchCnt_Title(Connection conn, String title)throws SQLException{ // 검색 페이징 목록갯수 구하기 타이틀
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql ="select count(*) from review where rev_title like ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1,title+"%");
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
	
	public int searchCnt_name(Connection conn, String name)throws SQLException{ // 검색 페이징 목록갯수 구하기 이름으로
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql ="select count(*) from review where rev_name like ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1,name+"%");
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
	public int searchCnt_number(Connection conn, int no)throws SQLException{ // 검색 페이징 목록갯수 구하기 게시물 번호로
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql ="select count(*) from review where rev_no like ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1,no);
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
	
	
	public ListPageCount getArticlePage(int pageNum){ // 게시판 페이징
		int size = 10;
		try(Connection conn = ConnectionProvider.getConnection()){
			ReviewDao dao = ReviewDao.getInstance();
			int total = dao.selectCount(conn);
			List<Review> content = dao.selectOrderBy(conn, (pageNum-1)*size, size);
			return new ListPageCount(total, pageNum, size, content); 
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	
	}
	
	public int update(Connection conn, Review review) throws SQLException{
		PreparedStatement pstmt = null;	
		try {
			String sql = "update Review set rev_mem=?, rev_title=?,rev_name=?,rev_regdate=?,rev_readcnt=?,rev_replycnt=? where rev_no=?"; 

			pstmt= conn.prepareStatement(sql);
		
			pstmt.setInt(1, review.getRev_mem());
			pstmt.setString(2, review.getRev_title());
			pstmt.setString(3, review.getRev_name());
			pstmt.setTimestamp(4,new Timestamp(review.getRev_regdate().getTime()));
			pstmt.setInt(5, review.getRev_readcnt());
			pstmt.setInt(6, review.getRev_replycnt());
			pstmt.setInt(7, review.getRev_no());
			return pstmt.executeUpdate();			
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}


