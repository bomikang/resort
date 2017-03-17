package member.model;

import java.util.Date;

public class Member {
	private int no;
	private String id;
	private String password;
	private String name;
	private String mail;
	private String tel;
	private Date regDate;
	private Date outDate;
	private Boolean isMng;

	public Member(int no, String id, String password, String name, String mail, String tel, Date regDate, Date outDate,
			Boolean isMng) {
		super();
		this.no = no;
		this.id = id;
		this.password = password;
		this.name = name;
		this.mail = mail;
		this.tel = tel;
		this.regDate = regDate;
		this.outDate = outDate;
		this.isMng = isMng;
	}

	public Member(String id) {
		super();
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public Boolean getIsMng() {
		return isMng;
	}

	public void setIsMng(Boolean isMng) {
		this.isMng = isMng;
	}
	
	public boolean matchPassword(String pwd){ // 패스워드 비교
		return password.equals(pwd);
	}
}
