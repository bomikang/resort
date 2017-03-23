package board.model;

import java.util.Date;

import book.model.Book;
import member.model.LoginMemberInfo;

public class Faq {
	/* FIELDS - 조회수 column이 table에 존재하지만 쓸 일 없을거같아 일단 DB수정 없이 진행. But,변수 사용안함 */
	private int no;
	private LoginMemberInfo writer;
	private String title;
	private String detail;
	private Date regDate;
	
	/* GET/SET */
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public LoginMemberInfo getWriter() {
		return writer;
	}	
	public void setWriter(LoginMemberInfo writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	/* CONSTURUCTOR */
	public Faq() {}
	public Faq(LoginMemberInfo writer, String title, String detail, Date regDate) {
		/*no : auto_increment */
		this.writer = writer;
		this.title = title;
		this.detail = detail;
		this.regDate = regDate;
	}
	public Faq(int no, LoginMemberInfo writer, String title, String detail, Date regDate) {
		/*list생성용*/
		this.no = no;
		this.writer = writer;
		this.title = title;
		this.detail = detail;
		this.regDate = regDate;
	}
	/* 등록 날짜 format 함수 */
	public String getRegDateForm(){
		return Book.dateFormat.format(this.regDate);
	}
	public String getRegDateTimeForm(){
		return Book.dateTimeFormat.format(this.regDate);
	}	
}
