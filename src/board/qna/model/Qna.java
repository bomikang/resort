package board.qna.model;

import java.util.Date;

import book.model.Book;
import member.model.Member;

public class Qna {
	private int no;
	private Member member;
	private String title;
	private Date regDate;
	private int article; //원 게시물 번호(관리자 일 때)
	private String content; //게시글 내용
	
	public Qna() {}
	
	public Qna(int no, Member member, String title, Date regDate, int article, String content) {
		this.no = no;
		this.member = member;
		this.title = title;
		this.regDate = regDate;
		this.article = article;
		this.content = content;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getArticle() {
		return article;
	}

	public void setArticle(int article) {
		this.article = article;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	/*method*/
	//Date 객체를 "yyyy-MM-dd" 형태의 String으로 반환
	public String getRegDateNoTimeForm(){
		return Book.dateFormat.format(regDate);
	}
}
