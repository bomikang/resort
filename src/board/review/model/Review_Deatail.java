package board.review.model;

public class Review_Deatail {
	private int rev_no;
	private String rev_detail;
	public Review_Deatail(int rev_no, String rev_detail) {
		super();
		this.rev_no = rev_no;
		this.rev_detail = rev_detail;
	}
	public int getRev_no() {
		return rev_no;
	}
	public void setRev_no(int rev_no) {
		this.rev_no = rev_no;
	}
	public String getRev_detail() {
		return rev_detail;
	}
	public void setRev_detail(String rev_detail) {
		this.rev_detail = rev_detail;
	}
	
	
}
