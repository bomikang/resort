package board.model;

import java.util.Date;

import book.model.Book;
import member.model.LoginMemberInfo;

public class Notice {
	/* FILEDS */
	private int no;
	private LoginMemberInfo writer;
	private String title;
	private Date regDate;
	private int readCnt;
	private boolean state;
	
	/* CONSTRUCTOR */
	public Notice() {}
	/* insert/ update 용 */
	public Notice(LoginMemberInfo writer, String title, Date regDate, int readCnt, boolean state) {
		super();
		this.writer = writer;
		this.title = title;
		this.regDate = regDate;
		this.readCnt = readCnt;
		this.state = state;
	}

	// select 용(List)
	public Notice(int no, LoginMemberInfo writer, String title, Date regDate, int readCnt, boolean state) {
		super();
		this.no = no;
		this.writer = writer;
		this.title = title;
		this.regDate = regDate;
		this.readCnt = readCnt;
		this.state = state;
	}
	
	/* GETTER */
	public int getNo() {
		return no;
	}
	public LoginMemberInfo getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public Date getRegDate() {
		return regDate;
	}
	public int getReadCnt() {
		return readCnt;
	}
	public boolean isState() {
		return state;
	}
	/* SETTER */
	public void setNo(int no) {
		this.no = no;
	}
	public void setWriter(LoginMemberInfo writer) {
		this.writer = writer;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	/**
	 * Methods - 등록 날짜를 날짜, 날짜+시간 형태로 돌려주는 메소드
	 * */
	public String getRegDateTimeForm(){
		return Book.dateTimeFormat.format(this.regDate);
	}
	public String getRegDateForm(){
		return Book.dateFormat.format(this.regDate);
	}
	
}
