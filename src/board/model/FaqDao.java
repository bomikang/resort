package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;

public class FaqDao {
	private static final FaqDao instance = new FaqDao();

	private FaqDao() {}

	public static FaqDao getInstance() {
		return instance;
	}
	/**
	 * faq 테이블 내 모든 정보를 가져오는 메소드
	 * */
	public List<Faq> selectAllFaq(Connection conn)throws SQLException{
		List<Faq> fList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select f.faq_no, f.mem_no, m.mem_id, m.mem_name, m.mem_ismng ,m.mem_mail, m.mem_tel,faq_title, faq_detail, faq_regdate from resort.faq as f left join resort.`member` as m on f.mem_no = m.mem_no";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int no = rs.getInt("faq_no");
				/*member*/
				int my_no = rs.getInt("mem_no");
				String my_id = rs.getString("mem_id");
				String my_name = rs.getString("mem_name");
				String my_mail = rs.getString("mem_mail");
				boolean isMng = rs.getBoolean("mem_ismng");
				String my_tel = rs.getString("mem_tel");
				LoginMemberInfo writer = new LoginMemberInfo(my_no, my_id, my_name, my_mail, isMng, my_tel);
				
				String title = rs.getString("faq_title");
				String detail = rs.getString("faq_detail");
				Date regDate = rs.getTimestamp("faq_regdate");
				if(isMng==true){
					Faq faq = new Faq(no, writer, title, detail, regDate);
					fList.add(faq);
				}
			}
			return fList;
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}		
	}//end of selectAllFaq
	
	/**
	 * faq 테이블에 정보를 입력하는 메소드
	 * */
	public void insertIntoFaq(Connection conn, Faq faq)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			String sql = "insert into resort.faq(mem_no, faq_title, faq_detail, faq_regdate) values(?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, faq.getWriter().getMy_no());
			pstmt.setString(2, faq.getTitle());
			pstmt.setString(3, faq.getDetail());
			pstmt.setTimestamp(4, new Timestamp(faq.getRegDate().getTime()));
			
			pstmt.executeUpdate();
			
		}finally {
			JdbcUtil.close(pstmt);
		}		
	}//end of insertIntoFaq
	
	/**
	 * faq 정보 수정
	 * */
	public void updateFaq(Connection conn, Faq faq)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			String sql = "update resort.faq set faq_title=?, faq_detail=? where faq_no=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, faq.getTitle());
			pstmt.setString(2, faq.getDetail());
			pstmt.setInt(3, faq.getNo());
			
			pstmt.executeUpdate();
			
		}finally {
			JdbcUtil.close(pstmt);
		}		
	}//end of updateFaq
	
	/**
	 * faq 정보 삭제
	 * */
	public void deleteFaq(Connection conn, String[] nums)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			if(nums.length==0){
				return;
			}
			
			String sql = "delete from resort.faq ";
			
			for(int i=0;i<nums.length;i++){
				if(i==0){
					sql += " where faq_no="+nums[i];
				}else{
					sql += " or faq_no="+nums[i];
				}
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.executeUpdate();
			
		}finally {
			JdbcUtil.close(pstmt);
		}		
	}//end of deleteFaq
	/**
	 * faq 테이블 내 선택된 정보를 가져오는 메소드
	 * */
	public Faq selectFaqByNo(Connection conn, int fNo)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select f.faq_no, f.mem_no, m.mem_id, m.mem_name, m.mem_ismng ,m.mem_mail, m.mem_tel,faq_title, faq_detail, faq_regdate "
						+"from resort.faq as f left join resort.`member` as m on f.mem_no = m.mem_no where faq_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int no = rs.getInt("faq_no");
				/*member*/
				int my_no = rs.getInt("mem_no");
				String my_id = rs.getString("mem_id");
				String my_name = rs.getString("mem_name");
				String my_mail = rs.getString("mem_mail");
				boolean isMng = rs.getBoolean("mem_ismng");
				String my_tel = rs.getString("mem_tel");
				LoginMemberInfo writer = new LoginMemberInfo(my_no, my_id, my_name, my_mail, isMng, my_tel);
				
				String title = rs.getString("faq_title");
				String detail = rs.getString("faq_detail");
				Date regDate = rs.getTimestamp("faq_regdate");
				if(isMng==true){
					return new Faq(no, writer, title, detail, regDate);
				}
			}
			return null;
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}		
	}//end of selectFaqByNo
	/**
	 * faq 정보 삭제
	 * */
	public void deleteFaq(Connection conn, int nums)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			String sql = "delete from resort.faq where faq_no=?";			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nums);
			pstmt.executeUpdate();
			
		}finally {
			JdbcUtil.close(pstmt);
		}		
	}//end of deleteFaq
}
