package auth.handler;

public class LoginMemberInfo {
	private int my_no;
	private String my_id;
	private String my_name;
	private String my_mail;
	private Boolean isMng;
	public LoginMemberInfo(int my_no, String my_id, String my_name, String my_mail, Boolean isMng) {
		super();
		this.my_no = my_no;
		this.my_id = my_id;
		this.my_name = my_name;
		this.my_mail = my_mail;
		this.isMng = isMng;
	}
	public int getMy_no() {
		return my_no;
	}
	public void setMy_no(int my_no) {
		this.my_no = my_no;
	}
	public String getMy_id() {
		return my_id;
	}
	public void setMy_id(String my_id) {
		this.my_id = my_id;
	}
	public String getMy_name() {
		return my_name;
	}
	public void setMy_name(String my_name) {
		this.my_name = my_name;
	}
	public String getMy_mail() {
		return my_mail;
	}
	public void setMy_mail(String my_mail) {
		this.my_mail = my_mail;
	}
	public Boolean getIsMng() {
		return isMng;
	}
	public void setIsMng(Boolean isMng) {
		this.isMng = isMng;
	}

}
