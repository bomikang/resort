package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import member.model.LoginMemberInfo;

public class NoticeDao {
	private static final NoticeDao instance = new NoticeDao();

	public static NoticeDao getInstance() {
		return instance;
	}

	private NoticeDao() {}
	/**
	 * 공지사항 게시물 List select
	 * */
	public List<Notice> selectAllNotice(Connection conn, int index) throws SQLException{
		List<Notice> nList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select n.nc_no, m.mem_name, m.mem_ismng, n.nc_title, n.nc_regdate, n.nc_readcnt, n.nc_state "
						+"from resort.notice as n left join resort.`member` as m on n.nc_mem=m.mem_no order by n.nc_no desc limit 10 offset ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (index-1)*10);
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
	}// end of selectAllNotice
	
	/**
	 * 공지사항 게시물 select
	 * */
	public Notice selectNoticeByNo(Connection conn, int nNo) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select n.nc_no, m.mem_name, m.mem_ismng, n.nc_title, n.nc_regdate, n.nc_readcnt, n.nc_state "
						+"from resort.notice as n left join resort.`member` as m on n.nc_mem=m.mem_no where n.nc_no=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nNo);
			rs = pstmt.executeQuery();
			if(rs.next()){
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
					return new Notice(no, writer, title, regDate, readCnt, state);
				}
			}
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return null;
	}// end of selectNoticeByNo
	
	/**
	 * 공지사항 게시물 select(제목 검색시)
	 * */
	public List<Notice> selectNoticeByTitle(Connection conn, String search, int index) throws SQLException{
		List<Notice> nList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select n.nc_no, m.mem_name, m.mem_ismng, n.nc_title, n.nc_regdate, n.nc_readcnt, n.nc_state "
						+"from resort.notice as n left join resort.`member` as m on n.nc_mem=m.mem_no where where nc_title like ?  order by n.nc_no desc limit 10 offset ?";
			pstmt = conn.prepareStatement(sql);
			if(!search.contains("%")){
				search = "%"+search+"%";
			}else{
				
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
	 * 공지사항 중 항상 상단에 뜨도록 설정할 realNotice(nc_state=true)를 가지고 오는 메소드
	 * */
	public List<Notice> selectRealNotice(Connection conn) throws SQLException{
		List<Notice> nList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select n.nc_no, m.mem_name, m.mem_ismng, n.nc_title, n.nc_regdate, n.nc_readcnt, n.nc_state "
						+"from resort.notice as n left join resort.`member` as m on n.nc_mem=m.mem_no where n.nc_state=true";
			pstmt = conn.prepareStatement(sql);
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
	}// end of selectRealNotice
	
	/**
	 * 공지사항 게시물 insert
	 * */
	public int insertNotice(Connection conn, Notice notice) throws SQLException{
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "insert into resort.notice(nc_mem, nc_title, nc_regdate, nc_readcnt, nc_state) values(?,?,?,0,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice.getWriter().getMy_no());
			pstmt.setString(2, notice.getTitle());
			pstmt.setTimestamp(3, new Timestamp(notice.getRegDate().getTime()));
			pstmt.setBoolean(4, notice.isState());
			int res = pstmt.executeUpdate();
			if(res>0){
				stmt = conn.createStatement();
				String sql2 = "select last_insert_id() from resort.notice";
				rs = stmt.executeQuery(sql2);
				
				if(rs.next()){
					int newNo = rs.getInt(1);
					return newNo;
				}
				
			}
			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(stmt);
		}
		return -1;
	}// end of insertNotice
	
	/**
	 * 공지사항 게시물 Update
	 * */
	public void updateNotice(Connection conn, Notice notice) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "update resort.notice set nc_title=?, nc_state=? where n.nc_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, notice.getTitle());
			pstmt.setBoolean(2, notice.isState());
			pstmt.setInt(3, notice.getNo());
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}// end of updateNotice
	
	/**
	 * 공지사항 게시물 Delete
	 * */
	public void deleteNotice(Connection conn, int nNo) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "delete from resort.notice where n.nc_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nNo);			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}// end of deleteNotice
	
	/**
	 * 조회수 증가시키는 method
	 * */
	public void updateReadCntNotice(Connection conn, int nNo) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "update resort.notice set nc_readcnt = nc_readcnt+1 where nc_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nNo);
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}// end of updateReadCntNotice
	/**
	 * index 설정 위해 전체 갯수 가져오는 메소드 
	 * */
	public int getMaxIndex(Connection conn) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 1;
		try{
			String sql = "select count(nc_no) from resort.notice as n left join resort.`member` as m on n.nc_mem=m.mem_no "
						+"where m.mem_ismng=true";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int totalCnt = rs.getInt("count(nc_no)");
				if(totalCnt==0){
					result = 1;
				}else{
					if(totalCnt%10 != 0){			
						result = (totalCnt/10)+1;
					}else if(totalCnt%10 == 0){
						result = (totalCnt/10);
					}
				}
			}
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return result;
	}// end of getMaxIndex
}
