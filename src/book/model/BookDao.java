package book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
				book.setNo(rs.getString("bk_no"));							//예약번호
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
				
				
				book.setMem(mDao.selectByNo(conn, memNo));
				book.setStr(sDao.getStructureByNo(conn, strNo));			
				
				bList.add(book);
			}
			
			return bList;			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
	}//end of selectAll
	/**
	 * 예약현황 조회에 취소되지 않은 내역을 시설 번호를 기준으로 월별로 조회하여 ArrayList로 돌려주는 Method
	 * */
	public List<Book> selectThisMonthByStr(Connection conn, Date date, int str, int strId)throws SQLException{
		List<Book> bList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			
			String sql = "select * from resort.book"
							+" where((year(bk_startdate)=? and month(bk_startdate)=?)or(year(bk_enddate)=? and month(bk_enddate)=?))"
							+"and bk_state!='예약취소' and bk_state!='예약종료' and bk_str=? "
							+"and (select distinct str_id from resort.`structure` where str_no=?)=? "
							+"order by bk_startdate";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			pstmt.setInt(3, year);
			pstmt.setInt(4, month);
			pstmt.setInt(5, str);
			pstmt.setInt(6, str);
			pstmt.setInt(7, strId);
			
			rs = pstmt.executeQuery();
			
			MemberDao mDao = MemberDao.getInstance();
			StructureDao sDao = StructureDao.getInstance();
			
			while(rs.next()){
				Book book = new Book();
				book.setNo(rs.getString("bk_no"));							//예약번호
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
				
				
				book.setMem(mDao.selectByNo(conn, memNo));
				book.setStr(sDao.getStructureByNo(conn, strNo));		
				
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
	public Book selectByNo(Connection conn, String no)throws SQLException{		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			String sql = "select * from resort.book where bk_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			MemberDao mDao = MemberDao.getInstance();
			StructureDao sDao = StructureDao.getInstance();
			
			if(rs.next()){
				Book book = new Book();
				book.setNo(rs.getString("bk_no"));								//예약번호
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
				
				
				book.setMem(mDao.selectByNo(conn, memNo));
				book.setStr(sDao.getStructureByNo(conn, strNo));				
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
	public List<Book> selectByMember(Connection conn, Member mem)throws SQLException{		
		PreparedStatement pstmt = null;
		List<Book> bList = new ArrayList<>();
		ResultSet rs = null;		
		try{
			String sql = "select * from resort.book where bk_mem = ? and (bk_state !='예약취소' or bk_state != '예약종료') order by bk_startdate";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem.getNo());
			rs = pstmt.executeQuery();
			MemberDao mDao = MemberDao.getInstance();
			StructureDao sDao = StructureDao.getInstance();
			
			while(rs.next()){
				Book book = new Book();
				book.setNo(rs.getString("bk_no"));								//예약번호
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
				
				
				book.setMem(mDao.selectByNo(conn, memNo));
				book.setStr(sDao.getStructureByNo(conn, strNo));
				
				bList.add(book);
			}	
			
			return bList;			
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
			String sql = "insert into resort.`book`(bk_mem, bk_str, bk_regdate, bk_startdate, bk_enddate, bk_state, bk_tel, bk_no)"
							+"values(?, ?, ?, ?, ?, '입금대기', ?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, book.getMem().getNo());
			pstmt.setInt(2, book.getStr().getNo());
			pstmt.setTimestamp(3, new Timestamp(book.getRegDate().getTime()));
			pstmt.setString(4, book.getStartDateForm());
			pstmt.setString(5, book.getEndDateForm());
			pstmt.setString(6, book.getTel());
			pstmt.setString(7, book.getNoForm());
			
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
			String sql = "update resort.book set bk_state = ?, bk_canceldate = null where bk_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getState());
			pstmt.setString(2, book.getNo());
			
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
			pstmt.setString(2, book.getNo());
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(pstmt);
		}
	}//end of updateBookState
	/**
	 * 사용자가 선택한 시설에 원하는 기간동안 사용이 가능한지를 조회하는 메소드
	 * 사용할 수 있는 경우 true를 반환(rs.next가 존재하지 않는 경우)
	 * 메소드 확인 필요!!
	 * */
	public boolean checkBookDate(Connection conn, Book book) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try{
			String sql = "select * from resort.book where ((bk_startdate >= ? and bk_startdate <?) or (bk_enddate>? and bk_enddate<=?)) and bk_state != '예약취소' and bk_str = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getStartDateForm());
			pstmt.setString(2, book.getEndDateForm());
			pstmt.setString(3, book.getStartDateForm());
			pstmt.setString(4, book.getEndDateForm());
			pstmt.setInt(5, book.getStr().getNo());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				return false;
			}			
			return true;			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}// end of checkBookDate
}
