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
	
	/*<회원입장> 회원이 작성한 게시글의 리스트만 불러옴*/
	public List<Qna> selectAllQnaByMember(Connection con, Member member){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Qna> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement("select * from qna where mem_no = ? order by qna_no desc");
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
	
	/*<관리자입장> 관리자의 게시글을 제외한 회원의 모든 게시글 리스트를 가져옴*/
	public List<Qna> selectAllQnaExceptAdmin(Connection con){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Qna> list = new ArrayList<>();
		
		String sql = "select q.*, qna_detail "
					+ "from qna q, qna_detail qd where q.qna_article = 0 and q.qna_no = qd.qna_no order by q.qna_no desc";	
		
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
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return list;
	}//selectAllQnaWhenAdmin
	
	/*<관리자입장> 관리자가 답변을 달아준 게시글 리스트를 가져옴*/
	public List<Qna> completeReplyList(Connection con){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Qna> list = new ArrayList<>();
		
		String sql = "select uq.*, qna_detail from qna_detail uqd, qna uq inner join qna aq "
					+ "on uq.qna_no = aq.qna_article where uq.qna_no = uqd.qna_no order by uq.qna_no desc";		
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
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return list;
	}//completeReplyList
	
	/*<관리자입장> 관리자의 답변이 존재하지 않을 때
		관리자 게시물의 원게시물번호(qna_article)와 회원의 게시글번호가 같은 것은 제외하고
		원게시물번호가 0인 회원의 게시글만 불러옴(qna_article이 존재하면 답글이라는 뜻, 즉 관리자 라는 뜻)*/
	public List<Qna> incompleteReplyList(Connection con){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Qna> list = new ArrayList<>();
		
		String sql = "select q.*, qna_detail from qna q, qna_detail qd "
					+ "where q.qna_no not in(select qna_article from qna where qna_article <> 0) "
					+ "and q.qna_article = 0 and q.qna_no = qd.qna_no order by q.qna_no desc";
		
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
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return list;
	}
	
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
			int qnaNo = getLastQnaNo(con); //마지막 게시글 번호 가져오는 메소드 호출
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
	
	/*회원이 게시글을 삭제할 때 관리자 답변이 존재하면 관리자의 답변까지 삭제*/
	public void deleteQnaWhenUser(Connection con, Qna qna){
		PreparedStatement pstmtContent = null;
		PreparedStatement pstmtTitle = null;

		try {
			//내용부터 삭제
			pstmtContent = con.prepareStatement("delete from qna_detail where qna_no = ?");
			pstmtContent.setInt(1, qna.getNo());
			pstmtContent.executeUpdate();
			
			//제목 삭제
			pstmtTitle = con.prepareStatement("delete from qna where qna_no = ?");
			pstmtTitle.setInt(1, qna.getNo());
			pstmtTitle.executeUpdate();
			
			if ( qna.getReply() ) {
				//관리자 게시글 삭제 메소드호출
				deleteQnaWhenAdmin(con, qna);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmtTitle);
			JdbcUtil.close(pstmtContent);
		}
	}//deleteQna
	
	/*관리자 게시글 삭제*/
	public void deleteQnaWhenAdmin(Connection con, Qna qna){
		PreparedStatement pstmtContent = null;
		PreparedStatement pstmtTitle = null;

		try {
			//내용부터 삭제
			pstmtContent = con.prepareStatement("delete from qna_detail where qna_no = (select qna_no from qna where qna_article = ?)");
			pstmtContent.setInt(1, qna.getNo());
			pstmtContent.executeUpdate();
			
			//제목 삭제
			pstmtTitle = con.prepareStatement("delete from qna where qna_article = ?");
			pstmtTitle.setInt(1, qna.getNo());
			pstmtTitle.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(pstmtTitle);
			JdbcUtil.close(pstmtContent);
		}
	}
	
	/*게시글 번호로 제목과 내용을 불러오는 메소드*/
	public Qna getQnaByNo(Connection con, int qnaNo){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Qna qna = null;
		
		String sql = "select q.*, d.qna_detail from resort.qna as q left join resort.qna_detail as d on q.qna_no = d.qna_no";
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
	}//getQnaByNo
	
	/* 관리자 답변의 제목과 내용 가져오기 */
	public Qna getQnaFromAdmin(Connection con, int qnaNo){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Qna qna = null;
		
		String sql = "select q.*, qna_detail "
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
	}//getQnaFromAdmin
	
	
	/* method */
	private Qna createQna(ResultSet rs, Connection con) throws SQLException {
		int no = rs.getInt("qna_no");
		MemberDao memberDao = MemberDao.getInstance();
		Member member = memberDao.selectByNo(con, rs.getInt("mem_no")); 
		String title = rs.getString("qna_title");
		Date regDate = rs.getTimestamp("qna_regdate");
		int article = rs.getInt("qna_article");
		String content = ""; //필요 없을 때가 존재하기 때문에 각 메소드에서 setContent해줌
		boolean reply = checkExistReply(con, no) ? true : false; //메소드 호출하여 답변 여부 확인
		
		return new Qna(no, member, title, regDate, article, content, reply);
	}//createQna
	
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
	
	/*답변 여부를 통하여 Qna의 reply라는 변수에 심어줄 것임*/
	public boolean checkExistReply(Connection con, int qnaNo){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean reply = false;
		
		String sql = "select count(*) from qna uq, qna aq "
					+ "where uq.qna_no = aq.qna_article and aq.qna_article = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qnaNo);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getInt(1) == 1) {
					reply = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return reply;
	}//checkExistReply
}
