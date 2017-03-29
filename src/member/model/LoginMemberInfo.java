package member.model;

public class LoginMemberInfo { // 로그인 이후 세션에 데이터 남기는 개인정보 클래스
	private int my_no;
	private String my_id;
	private String my_name;
	private String my_mail;
	private Boolean isMng; // 관리자 유무판단
	private String my_tel;
	public LoginMemberInfo(int my_no, String my_id, String my_name, String my_mail, Boolean isMng,String my_tel) {
		super();
		this.my_no = my_no;
		this.my_id = my_id;
		this.my_name = my_name;
		this.my_mail = my_mail;
		this.isMng = isMng;
		this.my_tel = my_tel;
		
	}
	
	
	public String getMy_tel() {
		return my_tel;
	}


	public void setMy_tel(String my_tel) {
		this.my_tel = my_tel;
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


	public LoginMemberInfo() {}
}
