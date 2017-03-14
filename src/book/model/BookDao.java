package book.model;

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
import structure.model.StructureDao;

public class BookDao {
	private static final BookDao instance = new BookDao();

	public static BookDao getInstance() {
		return instance;
	}

	private BookDao() {}
	/**
	 * 관리자 화면에서 모든 예약 내역을 관리하기 위해 DB에 저장된 모든 내역을 ArrayList형태로 반환해주는 Methods
	 * */
	public List<Book> selectAll(Connection conn)throws SQLException{
		List<Book> bList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			String sql = "select * from resort.book";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			MemberDao mDao = MemberDao.getInstance();
			StructureDao sDao = StructureDao.getInstance();
			while(rs.next()){
				Book book = new Book();
				book.setNo(rs.getInt("bk_no"));								//예약번호
				book.setRegDate(rs.getTimestamp("bk_regdate"));				//예약날짜
				book.setStartDate(rs.getTimestamp("bk_startdate"));			//숙박시작날짜
				book.setEndDate(rs.getTimestamp("bk_enddate"));				//숙박끝날짜
				
				if(rs.getTimestamp("bk_canceldate")!=null){
					book.setCancelDate(rs.getTimestamp("bk_canceldate"));	//취소 했을시(취소날짜)
				}
				
				book.setState(rs.getString("bk_state"));					//예약의 진행상태
				book.setTel(rs.getString("bk_tel"));						//예약자 연락처
				
				int memNo = rs.getInt("bk_mem");
				int strNo = rs.getInt("bk_str");
				
				
				book.setMem(mDao.selectById(conn, memNo));
//				book.setStr(sDao.);
				
				
				bList.add(book);
			}
			
			return bList;			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
	}//end of selectAll
	/**
	 * 예약현황 조회에 취소되지 않은 내역을 월별로 조회하여 ArrayList로 돌려주는 Method
	 * */
	public List<Book> selectThisMonth(Connection conn, Date date)throws SQLException{
		List<Book> bList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			int year = date.getYear();
			int month = date.getMonth()+1;
			String sql = "select * from resort.book where((year(bk_startdate)=? and month(bk_startdate)=?)or(year(bk_enddate)=? and month(bk_enddate)=?))and bk_state!='예약취소'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			pstmt.setInt(3, year);
			pstmt.setInt(4, month);
			rs = pstmt.executeQuery();
			MemberDao mDao = MemberDao.getInstance();
			StructureDao sDao = StructureDao.getInstance();
			while(rs.next()){
				Book book = new Book();
				book.setNo(rs.getInt("bk_no"));								//예약번호
				book.setRegDate(rs.getTimestamp("bk_regdate"));				//예약날짜
				book.setStartDate(rs.getTimestamp("bk_startdate"));			//숙박시작날짜
				book.setEndDate(rs.getTimestamp("bk_enddate"));				//숙박끝날짜
				
				if(rs.getTimestamp("bk_canceldate")!=null){
					book.setCancelDate(rs.getTimestamp("bk_canceldate"));	//취소 했을시(취소날짜)
				}
				
				book.setState(rs.getString("bk_state"));					//예약의 진행상태
				book.setTel(rs.getString("bk_tel"));						//예약자 연락처
				
				int memNo = rs.getInt("bk_mem");
				int strNo = rs.getInt("bk_str");
				
				
				book.setMem(mDao.selectById(conn, memNo));
//				book.setStr(sDao.);		
				
				bList.add(book);
			}
			
			return bList;			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
	}//end of selectThisMonth
	/**
	 * 예약번호를 바탕으로 예약내역을 조회하여 Book객체를 반환해 주는 Method
	 * */
	public Book selectByNo(Connection conn, int no)throws SQLException{		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			String sql = "select * from resort.book where bk_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			MemberDao mDao = MemberDao.getInstance();
			StructureDao sDao = StructureDao.getInstance();
			
			if(rs.next()){
				Book book = new Book();
				book.setNo(rs.getInt("bk_no"));								//예약번호
				book.setRegDate(rs.getTimestamp("bk_regdate"));				//예약날짜
				book.setStartDate(rs.getTimestamp("bk_startdate"));			//숙박시작날짜
				book.setEndDate(rs.getTimestamp("bk_enddate"));				//숙박끝날짜
				
				if(rs.getTimestamp("bk_canceldate")!=null){
					book.setCancelDate(rs.getTimestamp("bk_canceldate"));	//취소 했을시(취소날짜)
				}
				
				book.setState(rs.getString("bk_state"));					//예약의 진행상태
				book.setTel(rs.getString("bk_tel"));						//예약자 연락처
				
				int memNo = rs.getInt("bk_mem");
				int strNo = rs.getInt("bk_str");
				
				
				book.setMem(mDao.selectById(conn, memNo));
//				book.setStr(sDao.);
				
				
				return book;
			}
			
			return null;			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}//end of selectByNo
	/**
	 * 로그인한 내역? 하여튼 회원내역(회원번호)를 바탕으로 예약내역을 조회할 시 가져 올 Method
	 * */
	public Book selectByMember(Connection conn, Member mem)throws SQLException{		
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try{
			String sql = "select * from resort.book where bk_mem = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem.getNo());
			rs = pstmt.executeQuery();
			MemberDao mDao = MemberDao.getInstance();
			StructureDao sDao = StructureDao.getInstance();
			
			if(rs.next()){
				Book book = new Book();
				book.setNo(rs.getInt("bk_no"));								//예약번호
				book.setRegDate(rs.getTimestamp("bk_regdate"));				//예약날짜
				book.setStartDate(rs.getTimestamp("bk_startdate"));			//숙박시작날짜
				book.setEndDate(rs.getTimestamp("bk_enddate"));				//숙박끝날짜
				
				if(rs.getTimestamp("bk_canceldate")!=null){
					book.setCancelDate(rs.getTimestamp("bk_canceldate"));	//취소 했을시(취소날짜)
				}
				
				book.setState(rs.getString("bk_state"));					//예약의 진행상태
				book.setTel(rs.getString("bk_tel"));						//예약자 연락처
				
				int memNo = rs.getInt("bk_mem");
				int strNo = rs.getInt("bk_str");
				
				
				book.setMem(mDao.selectById(conn, memNo));
//				book.setStr(sDao.);	
				return book;
			}			
			return null;			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}//end of selectByMember
	/**
	 * 예약 시 수행할 Method
	 * */
	public void insertBook(Connection conn, Book book)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			String sql = "insert into resort.`book`(bk_mem, bk_str, bk_regdate, bk_startdate, bk_enddate, bk_state, bk_tel)"
							+"values(?, ?, ?, ?, ?, '입금대기', ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, book.getMem().getNo());
			pstmt.setInt(2, book.getStr().getNo());
			pstmt.setTimestamp(3, new Timestamp(book.getRegDate().getTime()));
			pstmt.setString(4, book.getStartDateForm());
			pstmt.setString(5, book.getEndDateForm());
			pstmt.setString(6, book.getTel());
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(pstmt);
		}
	}//end of insertBook
	/**
	 * 입금대기/ 입금완료/ 예약취소로 구분 된 상태를 관리자로 하여금 수정할 수 있도록 하기 위한 update Methods
	 * */
	public void updateBookState(Connection conn, Book book)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			String sql = "update resort.book set bk_state = ? where bk_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getState());
			pstmt.setInt(2, book.getNo());
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(pstmt);
		}
	}//end of updateBookState
	
	/**
	 * 예약취소를 구분하기 위해 상태, 예약취소 날짜를 update하는 method
	 * */
	public void updateCancelDate(Connection conn, Book book)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			String sql = "update resort.book set bk_state = '예약취소', bk_canceldate = ? where bk_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, new Timestamp(book.getCancelDate().getTime()));
			pstmt.setInt(2, book.getNo());
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(pstmt);
		}
	}//end of updateBookState
}
