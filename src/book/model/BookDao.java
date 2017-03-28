package book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	 * 예약현황 조회에 취소되지 않은 내역을 시설 번호를 기준으로 월별로 조회하여 ArrayList로 돌려주는 Method - 사용
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
			//select * from resort.book as b left join resort.`structure` as s on b.bk_str=s.str_no where ((year(bk_startdate)=2017 and month(b.bk_startdate)=3)or(year(b.bk_enddate)=2017 and month(b.bk_enddate)=3))and b.bk_state!='예약취소' and b.bk_state!='예약종료' and b.bk_str=1
			//and s.str_id=1 order by bk_startdate;
			String sql = "select * from resort.book as b left join resort.`structure` as s on b.bk_str=s.str_no "
							+"where ((year(bk_startdate)=? and month(b.bk_startdate)=?)or(year(b.bk_enddate)=? and month(b.bk_enddate)=?)) "
							+"and b.bk_state!='예약취소' and b.bk_state!='예약종료' "
							+"and b.bk_str=? and s.str_id=? order by bk_startdate ";
			
			pstmt = conn.prepareStatement(sql);
			System.out.println(year);
			System.out.println(month);
			System.out.println(str);
			System.out.println(strId);
			System.out.println("selectThisMonthByStr : "+sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			pstmt.setInt(3, year);
			pstmt.setInt(4, month);
			pstmt.setInt(5, str);
			pstmt.setInt(6, strId);
			
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
				book.setName(rs.getString("bk_name"));
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
	 * 예약번호를 바탕으로 예약내역을 조회하여 Book객체를 반환해 주는 Method - 사용
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
				book.setName(rs.getString("bk_name"));
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
	 * 로그인한 내역? 하여튼 회원내역(회원번호)를 바탕으로 예약내역을 조회할 시 가져 올 Method - 사용
	 * */
	public List<Book> selectByMember(Connection conn, Member mem)throws SQLException{		
		PreparedStatement pstmt = null;
		List<Book> bList = new ArrayList<>();
		ResultSet rs = null;		
		try{
			String sql = "select * from resort.book where bk_mem = ? order by bk_no desc ";
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
				
				book.setName(rs.getString("bk_name"));
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
	 * 예약 시 수행할 Method - 사용
	 * */
	public void insertBook(Connection conn, Book book)throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			String sql = "insert into resort.`book`(bk_mem, bk_str, bk_regdate, bk_startdate, bk_enddate, bk_state, bk_tel, bk_no, bk_name)"
							+"values(?, ?, ?, ?, ?, '입금대기', ?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, book.getMem().getNo());
			pstmt.setInt(2, book.getStr().getNo());
			pstmt.setTimestamp(3, new Timestamp(book.getRegDate().getTime()));
			pstmt.setString(4, book.getStartDateForm());
			pstmt.setString(5, book.getEndDateForm());
			pstmt.setString(6, book.getTel());
			pstmt.setString(7, book.getNoForm());
			pstmt.setString(8, book.getMem().getName());
			
			pstmt.executeUpdate();			
		}finally {
			JdbcUtil.close(pstmt);
		}
	}//end of insertBook
	/**
	 * 입금대기/ 입금완료/ 예약취소로 구분 된 상태를 관리자로 하여금 수정할 수 있도록 하기 위한 update Methods - 사용
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
	 * 예약취소 위해 상태, 예약취소 날짜를 update하는 method - 사용
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
	 * 사용할 수 있는 경우 true를 반환(rs.next가 존재하지 않는 경우) - 사용
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
	/**
	 * 예약 후 3일 이내에 입금이 되지 않은 경우 예약 취소로 변경하는 메소드(관리자로 로그인 할 시 호출할 예정!)
	 * 호출할 handler에서 connection에 auto_commit false로 변경 후 사용요망
	 * */
	public void autoBookCancel(Connection conn)throws SQLException{
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.HOUR_OF_DAY)>=17){
			PreparedStatement pstmt = null;
			
			try{
				String sql = "update resort.book set bk_state='예약취소', bk_canceldate = now() where bk_regdate<date_sub(now(),interval 3 day) and bk_state = '입금대기'";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.executeUpdate();			
			}finally {
				JdbcUtil.close(pstmt);
			}
		}
	}// end of autoBookCancel
	
	/**
	 * 현재 날짜가 예약의 퇴실날짜를 경과한 경우 예약 종료로 만드는 구문 (관리자로 로그인 할 시 호출할 예정!)
	 * 호출할 handler에서 connection에 auto_commit false로 변경 후 사용요망
	 * */
	public void autoBookEnd(Connection conn)throws SQLException{
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.HOUR_OF_DAY)>=15){
			PreparedStatement pstmt = null;
			
			try{
				String sql = "update resort.book set bk_state='예약종료' where bk_enddate < now() and (bk_state = '입금완료' or bk_state='입금대기')";
				pstmt = conn.prepareStatement(sql);	
				
				pstmt.executeUpdate();			
			}finally {
				JdbcUtil.close(pstmt);
			}
		}
	}// end of autoBookEnd
	
	/**
	 * 로그인한 내역? 하여튼 회원내역(회원번호)를 바탕으로 예약내역 갯수를 반환할 메소드 (필요할 경우 대비) - 사용
	 * */
	public Integer selectCountByMember(Connection conn, Member mem)throws SQLException{		
		PreparedStatement pstmt = null;
		List<Book> bList = new ArrayList<>();
		ResultSet rs = null;		
		try{
			String sql = "select * from resort.book where bk_mem = ? and (bk_state !='예약취소' and bk_state != '예약종료')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem.getNo());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Book book = new Book();
				book.setNo(rs.getString("bk_no"));								//예약번호				
				bList.add(book);
			}	
			
			return bList.size();			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}//end of selectCountByMember
	/**
	 * 회원이 예약내역을 조회할 시 사용하는 메소드 (년도, 월, 시설, 예약상태를 바탕으로 조회 )
	 * */
	public List<Book> selectByMemberWithCon(Connection conn, int mem, int year, int month, int strId,String[] state, String condition, int index)throws SQLException{		
		PreparedStatement pstmt = null;
		List<Book> bList = new ArrayList<>();
		ResultSet rs = null;		
		try{
			//select * from resort.book as b left join resort.`structure` as s on b.bk_str=s.str_no where b.bk_mem=3 and s.str_id=1 and b.bk_state='예약취소';
			String sql = "";
			if(condition.equals("all")){//전체조회(회원)
				sql = "select * from resort.book where bk_mem = ? order by bk_no desc ";
				sql += " limit 10 offset ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, mem);
				pstmt.setInt(2, (index-1)*10);			
			}else{//선택조회 (전체)
				sql = "select * from resort.book as b left join resort.`structure` as s on b.bk_str = s.str_no "
						+"where ((year(b.bk_startdate)=? and month(b.bk_startdate)=?) "
						+"or (year(b.bk_enddate)=? and month(b.bk_enddate)=?)) and b.bk_mem= ? ";
				if(strId != 0){
					sql+=" and s.str_id=? ";
				}
				if(state.length>0){
					sql += " and (";
					for(int i=0;i<state.length;i++){
						if(i==0){
							sql += " b.bk_state='"+state[i]+"' ";
						}else{
							sql += "or b.bk_state='"+state[i]+"' ";
						}
					}
					sql += ")";
				}
				
				sql += " order by b.bk_regdate desc limit 10 offset ?";
				System.out.println("selectByMemberWithCon : "+sql);
				System.out.println("year : "+year);
				System.out.println("month : "+month);
				System.out.println("mem : "+mem);
				System.out.println("strId : "+strId);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, year);
				pstmt.setInt(2, month);
				pstmt.setInt(3, year);
				pstmt.setInt(4, month);
				pstmt.setInt(5, mem);
				if(strId != 0){
					pstmt.setInt(6, strId);
					pstmt.setInt(7, (index-1)*10);		
				}else{
					pstmt.setInt(6, (index-1)*10);		
				}
			}			
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
				book.setName(rs.getString("bk_name"));
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
	 * 예약내역 조건 중 년, 월을 Setting하기 위해 사용할 함수 - 사용
	 * */
	public Set<Integer> selectYearOfBook(Connection conn)throws SQLException{		
		PreparedStatement pstmt = null;
		Set<Integer> yearList = new HashSet<>();
		ResultSet rs = null;	
		
		try{
			String sql = "select year(bk_startdate), year(bk_enddate) from resort.book";
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Integer startYear = rs.getInt("year(bk_startdate)");
				Integer endYear = rs.getInt("year(bk_enddate)");
				
				if(startYear!= null && endYear != null){
					yearList.add(startYear);
					yearList.add(endYear);
				}
			}	
			
			return yearList;			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}//end of selectYearOfBook	
	/**
	 * 예약내역 조건 중 년, 월을 Setting하기 위해 사용할 함수 - 사용
	 * */
	public Set<Integer> selectMonthOfBook(Connection conn)throws SQLException{		
		PreparedStatement pstmt = null;
		Set<Integer> monthList = new HashSet<>();
		ResultSet rs = null;	
		
		try{
			String sql = "select month(bk_startdate), month(bk_enddate) from resort.book";
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Integer startMonth = rs.getInt("month(bk_startdate)");
				Integer endMonth = rs.getInt("month(bk_enddate)");
				
				if(startMonth!= null && endMonth != null){
					monthList.add(startMonth);
					monthList.add(endMonth);
				}
			}	
			
			return monthList;			
		}finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}//end of selectMonthOfBook
	/**
	 * 관리자가 조건에 따라 예약 내역을 조회할 시 필요한 메소드 - 사용
	 * */
	public List<Book> selectAllWithCondition(Connection conn, String start, String end, int strId,int sNo, String memName,String[] state, int index)throws SQLException{		
		PreparedStatement pstmt = null;
		List<Book> bList = new ArrayList<>();
		ResultSet rs = null;	
		
		try{
			//select * from resort.book as b left join resort.`structure` as s on b.bk_str = s.str_no where (year(b.bk_startdate)=2017 and month(b.bk_startdate)=3) and (year(b.bk_enddate)=2017 and month(b.bk_enddate)=3) and s.str_id=1 and b.bk_state='예약취소';
			String sql = "select * from resort.book as b left join resort.`structure` as s on b.bk_str=s.str_no "
						+"where ((b.bk_startdate>=? and b.bk_startdate<=?) "
						+"or (b.bk_enddate>=? and b.bk_enddate<=?)) ";
			if(strId != 0){
				sql+=" and s.str_id=? ";
				if(sNo != 0){
					sql+=" and s.str_no=? ";
				}
			}
			if(state.length>0){
				sql += "and (";	
				for(int i=0;i<state.length;i++){
					if(i==0){
						sql += " b.bk_state='"+state[i]+"' ";
					}else{
						sql += "or b.bk_state='"+state[i]+"' ";
					}
				}
				sql +=") ";
			}
			if(memName != null){
				sql += " and bk_name = ? ";
			}
			sql += " order by b.bk_startdate limit 10 offset ?";
			
			System.out.println("selectByMember SQL : "+sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			pstmt.setString(3, start);
			pstmt.setString(4, end);
			if(strId != 0){
				pstmt.setInt(5, strId);
				if(sNo != 0){
					pstmt.setInt(6, sNo);
					if(memName != null){
						pstmt.setString(7, memName);
						pstmt.setInt(8, (index-1)*10);
					}else{
						pstmt.setInt(7, (index-1)*10);
					}
				}else if(sNo==0 && memName != null){
					pstmt.setString(6, memName);
					pstmt.setInt(7, (index-1)*10);
				}else{
					pstmt.setInt(6, (index-1)*10);
				}
			}else if(strId == 0 && memName != null){
				pstmt.setString(5, memName);
				pstmt.setInt(6, (index-1)*10);
			}else{
				pstmt.setInt(5, (index-1)*10);
			}
			
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
				book.setName(rs.getString("bk_name"));
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
	}//end of selectAllWithCondition
	
	/**
	 * Test용
	 * 관리자 화면에서 모든 예약 내역을 관리하기 위해 DB에 저장된 모든 내역을 ArrayList형태로 반환해주는 Methods
	 * */
	public List<Book> selectAll(Connection conn, String bkName, int index)throws SQLException{
		List<Book> bList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			String sql = "select * from resort.book ";
			
			if(bkName != "0"){
				sql += "where bk_name=? ";
			}
			sql += "limit 10 offset ? ";
			pstmt = conn.prepareStatement(sql);
			
			if(bkName != "0"){
				pstmt.setString(1, bkName);
				pstmt.setInt(2, (index-1)*10);
			}else{
				pstmt.setInt(1, (index-1)*10);
			}
			
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
				book.setName(rs.getString("bk_name"));
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
		
	}//end of selectAll(with Paging)
	
	/*
	 * index 설정 위해 전체 갯수 가져오는 메소드 - 사용
	 * */
	public int getMaxIndex(Connection conn, String start, String end, int strId,int sNo, String memName,String[] state, String condition) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 1;
		try{
			String sql = "";
			if(condition.equals("all")){//전체 조회
				sql += "select count(bk_no) from resort.book ";
				
				if(memName != null){
					sql += "where bk_name=? ";
				}
				
				pstmt = conn.prepareStatement(sql);
				
				if(memName != null){
					pstmt.setString(1, memName);	
				}
				
			}else{//조건별 조회
				sql += "select count(bk_no) from resort.book as b left join resort.`structure` as s on b.bk_str=s.str_no "
						+"where ((b.bk_startdate>=? and b.bk_startdate<=?) "
						+"or (b.bk_enddate>=? and b.bk_enddate<=?)) ";
				if(strId != 0){
					sql+=" and s.str_id=? ";
					if(sNo != 0){
						sql+=" and s.str_no=? ";
					}
				}
				
				if(state.length>0){
					sql += "and (";	
					for(int i=0;i<state.length;i++){
						if(i==0){
							sql += " b.bk_state='"+state[i]+"' ";
						}else{
							sql += "or b.bk_state='"+state[i]+"' ";
						}
					}
					sql +=") ";
				}
				
				if(memName != null){
					sql += " and bk_name = ? ";
				}
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, start);
				pstmt.setString(2, end);
				pstmt.setString(3, start);
				pstmt.setString(4, end);
				if(strId != 0){
					pstmt.setInt(5, strId);
					if(sNo != 0){
						pstmt.setInt(6, sNo);
						if(memName != null){
							pstmt.setString(7, memName);
						}
					}else if(sNo==0 && memName != null){
						pstmt.setString(6, memName);
					}
				}else if(strId == 0 && memName != null){
					pstmt.setString(5, memName);
				}
			}
				
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int totalCnt = rs.getInt("count(bk_no)");
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
	/**
	 * index 설정 위해 전체 갯수 가져오는 메소드 - 사용
	 * */
	public int getMaxIndex(Connection conn, int mem, int year, int month, int strId,String[] state,String condition) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 1;
		try{
			String sql = "";
			if(condition.equals("all")){//전체조회(회원)
				sql = "select count(bk_no) from resort.book where bk_mem = ? order by bk_no desc ";				
				
				pstmt = conn.prepareStatement(sql);				
				pstmt.setInt(1, mem);	
			}else{//선택조회 (전체)
				sql = "select count(bk_no) from resort.book as b left join resort.`structure` as s on b.bk_str = s.str_no "
						+"where ((year(b.bk_startdate)=? and month(b.bk_startdate)=?) "
						+"or (year(b.bk_enddate)=? and month(b.bk_enddate)=?)) and b.bk_mem= ? ";
				if(strId != 0){
					sql+=" and s.str_id=? ";
				}
				if(state.length>0){
					sql += " and (";
					for(int i=0;i<state.length;i++){
						if(i==0){
							sql += " b.bk_state='"+state[i]+"' ";
						}else{
							sql += "or b.bk_state='"+state[i]+"' ";
						}
					}
					sql += ")";
				}
				
				sql += " order by b.bk_regdate desc ";
				System.out.println("selectByMemberWithCon : "+sql);
				System.out.println("year : "+year);
				System.out.println("month : "+month);
				System.out.println("mem : "+mem);
				System.out.println("strId : "+strId);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, year);
				pstmt.setInt(2, month);
				pstmt.setInt(3, year);
				pstmt.setInt(4, month);
				pstmt.setInt(5, mem);
				if(strId != 0){
					pstmt.setInt(6, strId);					
				}
			}				
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int totalCnt = rs.getInt("count(bk_no)");
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
