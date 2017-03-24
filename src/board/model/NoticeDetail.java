package board.model;

public class NoticeDetail {
	/* FIELDS */
	private int no;
	private String detail;
	/* CONSTRUCTOR */
	public NoticeDetail() {}

	public NoticeDetail(int no, String detail) {
		super();
		this.no = no;
		this.detail = detail;
	}
	/* Getter/Setter */
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}	
}
