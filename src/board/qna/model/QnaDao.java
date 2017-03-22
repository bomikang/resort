package board.qna.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import member.model.Member;
import member.model.MemberDao;

public class QnaDao {
	private static final QnaDao instance = new QnaDao();

	public static QnaDao getInstance() {
		return instance;
	}
	
	/*회원이 작성한 게시글의 리스트만 불러오는 메소드*/
	public List<Qna> selectAllQnaByMember(Connection con, Member member){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Qna> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement("select * from qna where mem_no = ?");
			pstmt.setInt(1, member.getNo());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Qna qna = createQna(rs, con);
				list.add(qna);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return list;
	}//selectAllQnaByMember
	
	/*관리자의 게시글을 제외한 회원의 모든 게시글 리스트를 가져오는 메소드*/
	public List<Qna> selectAllQnaExceptAdmin(Connection con){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Qna> list = new ArrayList<>();
		
		String sql = "select q.qna_no, mem_no, qna_title, qna_regdate, qna_article, qna_detail "
					+ "from qna q, qna_detail qd where q.qna_article = 0 and q.qna_no = qd.qna_no";	
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Qna qna = createQna(rs, con);
				qna.setContent(rs.getString("qna_detail"));
				list.add(qna);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}//selectAllQnaWhenAdmin
	
	public void insertQna(Connection con, Qna qna){
		PreparedStatement pstmtTitle = null;
		PreparedStatement pstmtContent = null;
		
		try {
			//제목삽입(qna)
			pstmtTitle = con.prepareStatement("insert into qna(mem_no, qna_title, qna_regdate, qna_article) values(?, ?, ?, ?)");
			pstmtTitle.setInt(1, qna.getMember().getNo());
			pstmtTitle.setString(2, qna.getTitle());
			pstmtTitle.setTimestamp(3, new Timestamp(qna.getRegDate().getTime()));
			pstmtTitle.setInt(4, qna.getArticle());
			pstmtTitle.executeUpdate();
			
			//내용삽입(qna_detail)
			int qnaNo = getLastQnaNo(con);
			pstmtContent = con.prepareStatement("insert into qna_detail values(?, ?)");
			pstmtContent.setInt(1, qnaNo);
			pstmtContent.setString(2, qna.getContent());
			pstmtContent.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmtContent);
			JdbcUtil.close(pstmtTitle);
		}
	}//insertQna
	
	/*마지막 게시글 번호 가져오는 메소드 <= qna_detail에 insert 할 때 필요*/
	public int getLastQnaNo(Connection con){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int res = 0;
		
		try {
			pstmt = con.prepareStatement("select qna_no from qna order by qna_no desc limit 1");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				res = rs.getInt("qna_no");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return res;
	}//getLastQnaNo 
	
	
	public void updateQna(Connection con, Qna qna){
		PreparedStatement pstmtTitle = null;
		PreparedStatement pstmtContent = null;
		
		try {
			//제목수정(qna)
			pstmtTitle = con.prepareStatement("update qna set qna_title = ? where qna_no = ?");
			pstmtTitle.setString(1, qna.getTitle());
			pstmtTitle.setInt(2, qna.getNo());
			pstmtTitle.executeUpdate();
			
			//내용수정(qna_detail)
			pstmtContent = con.prepareStatement("update qna_detail set qna_detail = ? where qna_no = ?");
			pstmtContent.setString(1, qna.getContent());
			pstmtContent.setInt(2, qna.getNo());
			pstmtContent.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmtContent);
			JdbcUtil.close(pstmtTitle);
		}
	}//updateQna
	
	/*게시글 번호로 제목과 내용을 불러오는 메소드*/
	public Qna getQnaByNo(Connection con, int qnaNo){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Qna qna = null;
		
		String sql = "select q.qna_no, mem_no, qna_title, qna_regdate, qna_article, qna_detail "
				+ "from qna q, qna_detail qd where q.qna_no = ? and qd.qna_no = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qnaNo);
			pstmt.setInt(2, qnaNo);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				qna = createQna(rs, con);
				qna.setContent(rs.getString("qna_detail"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return qna;
	}//getQnaFromUser
	
	/*
	public Qna getQnaFromAdmin(Connection con, int qnaNo){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Qna qna = null;
		
		String sql = "select q.qna_no, mem_no, qna_title, qna_regdate, qna_article, qna_detail "
				+ "from qna q, qna_detail qd where q.qna_article = ? and q.qna_no = qd.qna_no";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qnaNo);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				qna = createQna(rs, con);
				qna.setContent(rs.getString("qna_detail"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return qna;
	}//getQnaFromAdmin 관리자가 회원의 게시글에 남긴 답글 불러오는 메소드
	*/
	

	
	/* method */
	private Qna createQna(ResultSet rs, Connection con) throws SQLException {
		int no = rs.getInt("qna_no");
		MemberDao memberDao = MemberDao.getInstance();
		Member member = memberDao.selectByNo(con, rs.getInt("mem_no")); 
		String title = rs.getString("qna_title");
		Date regDate = rs.getTimestamp("qna_regdate");
		int article = rs.getInt("qna_article");
		String content = ""; //필요 없을 때가 존재하기 때문에 각 메소드에서 setContent해줌
		
		return new Qna(no, member, title, regDate, article, content);
	}
}
