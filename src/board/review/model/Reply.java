package board.review.model;

import java.util.Date;

public class Reply {
	private int rep_no; 		//댓글번호
	private Review rep_review; // 게시물번호 -> 리뷰테이블 외래키 지정
	private int rep_mem;	   // 회원번호 -> session DATA 가져오기//
	private String rep_name;   // 작성자 -> 동일//
	private String rep_detail; // 답글(내용)
	private Date rep_regdate; // 등록날짜
	
	public Reply(int rep_no, Review rep_review, int rep_mem, String rep_name, String rep_detail, Date rep_regdate) {
		super();
		this.rep_no = rep_no;
		this.rep_review = rep_review;
		this.rep_mem = rep_mem;
		this.rep_name = rep_name;
		this.rep_detail = rep_detail;
		this.rep_regdate = rep_regdate;
	}
	public int getRep_no() {
		return rep_no;
	}
	public void setRep_no(int rep_no) {
		this.rep_no = rep_no;
	}
	public Review getRep_review() {
		return rep_review;
	}
	public void setRep_review(Review rep_review) {
		this.rep_review = rep_review;
	}
	public int getRep_mem() {
		return rep_mem;
	}
	public void setRep_mem(int rep_mem) {
		this.rep_mem = rep_mem;
	}
	public String getRep_name() {
		return rep_name;
	}
	public void setRep_name(String rep_name) {
		this.rep_name = rep_name;
	}
	public String getRep_detail() {
		return rep_detail;
	}
	public void setRep_detail(String rep_detail) {
		this.rep_detail = rep_detail;
	}
	public Date getRep_regdate() {
		return rep_regdate;
	}
	public void setRep_regdate(Date rep_regdate) {
		this.rep_regdate = rep_regdate;
	}
	
	
}
