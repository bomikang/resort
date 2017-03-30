package board.review.model;

import java.util.Date;

public class Review {
	private int rev_no;			// 게시물 번호
	private int rev_mem;		//회원번호 -> session DATA가져오기
	private String rev_title;	//제목
	private String rev_name;	//작성자	-> session DATA가져오기
	private Date rev_regdate; // 등록날짜
	private int rev_readcnt; // 조회수 
	private int rev_replycnt; // 댓글수

	public Review(int rev_no, int rev_mem, String rev_title, String rev_name, Date rev_regdate, int rev_readcnt,
			int rev_replycnt) {
		super();
		this.rev_no = rev_no;
		this.rev_mem = rev_mem;
		this.rev_title = rev_title;
		this.rev_name = rev_name;
		this.rev_regdate = rev_regdate;
		this.rev_readcnt = rev_readcnt;
		this.rev_replycnt = rev_replycnt;
	}
	public int getRev_replycnt() {
		return rev_replycnt;
	}
	public void setRev_replycnt(int rev_replycnt) {
		this.rev_replycnt = rev_replycnt;
	}
	public int getRev_no() {
		return rev_no;
	}
	public void setRev_no(int rev_no) {
		this.rev_no = rev_no;
	}
	public String getRev_title() {
		return rev_title;
	}
	public void setRev_title(String rev_title) {
		this.rev_title = rev_title;
	}
	public Date getRev_regdate() {
		return rev_regdate;
	}
	public void setRev_regdate(Date rev_regdate) {
		this.rev_regdate = rev_regdate;
	}
	public int getRev_readcnt() {
		return rev_readcnt;
	}
	public void setRev_readcnt(int rev_readcnt) {
		this.rev_readcnt = rev_readcnt;
	}
	public int getRev_mem() {
		return rev_mem;
	}
	public void setRev_mem(int rev_mem) {
		this.rev_mem = rev_mem;
	}
	public String getRev_name() {
		return rev_name;
	}
	public void setRev_name(String rev_name) {
		this.rev_name = rev_name;
	}
	public boolean reply_zero(){
		return rev_replycnt==0;
	}
		
}
