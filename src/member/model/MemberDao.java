package member.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdbc.JdbcUtil;

public class MemberDao {
	private static MemberDao dao = new MemberDao();
	
	public static MemberDao getInstance(){
		return dao;
	}
	
	
	
	
	public void insert(Connection conn, Member mem) throws SQLException{
		PreparedStatement pstmt = null;
		try{
			String sql ="insert into member"
					+ "(mem_no,mem_id,mem_pwd,mem_name,mem_mail,mem_tel,mem_regdate,mem_outdate,mem_ismng)"
					+ "values(?,?,?,?,?,?,?,?,?)";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem.getNo());
			pstmt.setString(2, mem.getId());
			pstmt.setString(3,mem.getPassword());
			pstmt.setString(4,mem.getName());
			pstmt.setString(5,mem.getMail());
			pstmt.setString(6,mem.getTel());
			pstmt.setTimestamp(7,new Timestamp(mem.getRegDate().getTime()));
			pstmt.setTimestamp(8,null);
			pstmt.setBoolean(9, false);
			pstmt.executeUpdate();
		}finally{
			JdbcUtil.close(pstmt);
		}
	}
	public List<Member> listNo(Connection conn)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(
					"select mem_id from member;"
					);
			rs = pstmt.executeQuery();
			if(rs.next()){
				List<Member> memberlist = new ArrayList<>();
				do{
					
					Member mb = new Member(rs.getString("mem_id"));
					memberlist.add(mb);
				}while(rs.next());
				return memberlist;
			}else{
				return Collections.emptyList();
			}
			
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			
		}
	}
	public Member selectById(Connection conn, int no) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String sql = "select * from member where mem_no =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			Member member= null;
			//mem_no,mem_id,mem_pwd,mem_name,mem_mail,mem_tel,mem_regdate,mem_outdate,mem_ismng
			if(rs.next()){
				member = new Member(
						rs.getInt("mem_no"),
						rs.getString("mem_id"),
						rs.getString("mem_pwd"),
						rs.getString("mem_name"),
						rs.getString("mem_mail"),
						rs.getString("mem_tel"),
						rs.getDate("mem_regdate"),
						rs.getDate("mem_outdate"),
						rs.getBoolean("mem_ismng"));
			}
			return member;
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	public List<Member> listAll(Connection conn)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(
					"select * from member"
					);
			rs = pstmt.executeQuery();
			if(rs.next()){
				List<Member> memberlist = new ArrayList<>();
				do{
					//mem_no,mem_id,mem_pwd,mem_name,mem_mail,mem_tel,mem_regdate,mem_outdate,mem_ismng
					Member mb = new Member(
							rs.getInt("mem_no"),
							rs.getString("mem_id"),
							rs.getString("mem_pwd"),
							rs.getString("mem_name"),
							rs.getString("mem_mail"),
							rs.getString("mem_tel"),
							rs.getDate("mem_regdate"),
							rs.getDate("mem_outdate"),
							rs.getBoolean("mem_ismng"));
					memberlist.add(mb);
				}while(rs.next());
				return memberlist;
			}else{
				return Collections.emptyList();
			}
			
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			
		}
	}
	/*public int updatePwd(Connection conn, Member member) throws SQLException{
		PreparedStatement pstmt = null;	
		try {
			String sql = "UPDATE member set password = ? where memberid = ?"; 

			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getId());
			return pstmt.executeUpdate();			
		} finally {
			
			JdbcUtil.close(pstmt);
		
		}
	}*/

	

	
}
