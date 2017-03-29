package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;

public class NoticeDetailDao {
	private static final NoticeDetailDao instance = new NoticeDetailDao();

	public static NoticeDetailDao getInstance() {
		return instance;
	}

	private NoticeDetailDao() {}
	/**
	 * 공지사항 detail화면에서 보여줄 내용
	 * */
	public NoticeDetail selectDetailByNo(Connection conn, int nNo) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select * from resort.notice_detail where nc_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nNo);
			rs = pstmt.executeQuery();
			if(rs.next()){
				int no = rs.getInt("nc_no");
				String detail = rs.getString("nc_detail");
				
				return new NoticeDetail(no, detail);
			}
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return null;
	}//end of selectDetailByNo
	
	/**
	 * 공지사항 게시물 내용 insert
	 * */
	public void insertNotice(Connection conn, NoticeDetail noticeDetail) throws SQLException{
		PreparedStatement pstmt = null;

		try{
			String sql = "insert into resort.notice_detail(nc_no, nc_detail) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeDetail.getNo());
			pstmt.setString(2, noticeDetail.getDetail());
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(pstmt);
		}
	}// end of insertNotice
	/**
	 * 공지사항 게시물 내용 update
	 * */
	public int updateNotice(Connection conn, NoticeDetail noticeDetail) throws SQLException{
		PreparedStatement pstmt = null;
		try{
			String sql = "update resort.notice_detail set nc_detail = ? where nc_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeDetail.getDetail());
			pstmt.setInt(2, noticeDetail.getNo());
			
			int res = pstmt.executeUpdate();
			System.out.println("res : "+res);
			return res;
		}finally {
			JdbcUtil.close(pstmt);
		}
	}// end of updateNotice
	/**
	 * 공지사항 게시물 내용 delete
	 * */
	public void deleteNotice(Connection conn, int nNo) throws SQLException{
		PreparedStatement pstmt = null;
		try{
			String sql = "delete from resort.notice_detail where nc_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nNo);
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(pstmt);
		}
	}// end of updateNotice
	
	/**
	 * 공지사항 게시물 select(내용 검색시)
	 * */
	public List<Notice> selectNoticeByDetail(Connection conn, String search, int index) throws SQLException{
		List<Notice> nList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select n.nc_no, m.mem_name, m.mem_ismng, n.nc_title, n.nc_regdate, n.nc_readcnt, n.nc_state "
						+"from resort.notice as n left join resort.`member` as m on n.nc_mem=m.mem_no "
						+"left join resort.notice_detail as d on n.nc_no = d.nc_no "
						+"where where nc_detail like ?  order by n.nc_no desc limit 10 offset ?";
			pstmt = conn.prepareStatement(sql);
			if(!search.contains("%")){
				search = "%"+search+"%";
			}else{
				/*검색하는 단어 중 %가 포함됐을 경우 \%로 만들어줄 방법 */
			}
			pstmt.setString(1, search);
			pstmt.setInt(2, (index-1)*10);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int no = rs.getInt("nc_no");
				String name = rs.getString("mem_name");
				boolean isMng = rs.getBoolean("mem_ismng");
				LoginMemberInfo writer = new LoginMemberInfo();
				writer.setMy_name(name);
				writer.setIsMng(isMng);
				String title = rs.getString("nc_title");
				Date regDate = rs.getTimestamp("nc_regdate");
				int readCnt = rs.getInt("nc_readcnt");
				boolean state = rs.getBoolean("nc_state");
				
				if(isMng==true){
					nList.add(new Notice(no, writer, title, regDate, readCnt, state));
				}				
			}			
			return nList;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}// end of selectNoticeByTitle
	
	/**
	 * Notice Detail 정보 삭제(여러개)
	 * */
	public int deleteNoticeDetails(Connection conn, String[] nums)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			if(nums.length==0){
				return 0;
			}
			
			String sql = "delete from resort.notice_detail ";
			
			for(int i=0;i<nums.length;i++){
				if(i==0){
					sql += " where nc_no="+nums[i];
				}else{
					sql += " or nc_no="+nums[i];
				}
			}
			
			pstmt = conn.prepareStatement(sql);
			
			int res = pstmt.executeUpdate();
			return res;
		}finally {
			JdbcUtil.close(pstmt);
		}		
	}//end of deleteNoticeDetails
}
